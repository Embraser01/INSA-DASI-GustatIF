/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import dao.ClientDAO;
import dao.CommandeDAO;
import dao.LivreurDAO;
import dao.RestaurantDAO;
import metier.modele.*;
import util.JpaUtil;

import javax.persistence.OptimisticLockException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static metier.service.ServiceTechnique.calculDureeDrone;

/**
 * @author thomas
 */
public class ServiceMetier {

    /**
     * Service qui permet d'ajouter un client à la base de donnée.
     * Un mail sera envoyé au client pour lui spécifier si l'inscription s'est
     * bien déroulé ou a échoué.
     *
     * @param client le client à inscrire
     * @return true si l'inscription s'est bien déroulé, false sinon
     */
    public boolean inscription(Client client) {
        JpaUtil.creerEntityManager();

        JpaUtil.ouvrirTransaction();
        boolean succes = false;
        ClientDAO cldao = new ClientDAO();
        try {
            //on va recuperer LatLng
            LatLng latlng = ServiceTechnique.getLatLngFromAdress(client.getAdresse());

            //renseigne latitude et longitude du client
            client.setLatitudeLongitude(latlng.lat, latlng.lng);

            cldao.create(client);

            JpaUtil.validerTransaction();
            ServiceTechnique.mailInscription(client, true);
            succes = true;
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            ServiceTechnique.mailInscription(client, false);
            //Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }

        JpaUtil.fermerEntityManager();
        return succes;
    }


    /**
     * Service qui recherche un client dans la base de donnée à partir d'un mot
     * de passe et d'un mail. Le mot de passe est l'id du client stocké dans la
     * base de donnée.
     *
     * @param mail le mail du client
     * @param id   le mot de passe du client
     * @return le client trouvé ou null si aucune entrée de la base ne correspond
     */
    public Client connexion(String mail, long id) {
        JpaUtil.creerEntityManager();

        Client client = null;
        ClientDAO cldao = new ClientDAO();

        try {
            client = cldao.findById(id);

            if (mail.equals(client.getMail()) == false) {
                client = null;
            }
        } catch (Exception ex) {
        }


        JpaUtil.fermerEntityManager();
        return client;
    }

    /**
     * Service qui permet de mettre à jour les informtions d'un client dans la
     * base de données.
     *
     * @param cl le client à modifier
     * @return le client modifié
     */
    public Client majInfoClient(Client cl) {
        JpaUtil.creerEntityManager();

        ClientDAO cldao = new ClientDAO();
        Client clModif = null;

        JpaUtil.ouvrirTransaction();
        try {
            cl = cldao.merge(cl);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println("\nAucune modif effectuée\n");
        }

        try {
            clModif = cldao.findById(cl.getId());
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }


        JpaUtil.fermerEntityManager();
        return clModif;
    }

    /**
     * Service qui se charge de créer le panier qui permettra de stocker les 
     * produits que le client choisira.
     *
     * @param cl le client qui possède le panier 
     * @return le panier du client vide 
     */
    /*public Commande creerPanier(Client cl)
    {
        return new Commande(cl);
    }*/


    /**
     * Service qui permet de valider la commande passée par un client.
     * Il recherche un livreur disponible en fonction du poids de la commande
     * et essaye d'attribuer le livreur le plus rapide.
     * Si aucun livreur n'est disponible le client est notifié par mail que
     * sa commande est annulée.
     *
     * @param c la commande à valider
     * @return true si la commande a été attribué, false sinon
     */
    public boolean validerCommande(Commande c) {
        JpaUtil.creerEntityManager();

        boolean commandeValidee = false;
        CommandeDAO cdao = new CommandeDAO();
        // on récupère le restaurant vers où va se rendre le livreur 
        RestaurantDAO rdao = new RestaurantDAO();
        Restaurant r = null;

        try {
            r = rdao.trouverRestaurantDuProduit(c.getProduitsCommandes()
                    .get(0).getProduit());
        } catch (Exception ex) {
            //Logger.getLogger(ServiceTechnique.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Aucun restaurant trouvé");
        }

        if (r != null) {

            LivreurDAO ldao = new LivreurDAO();
            List<Livreur> livreurs = null;

            // on recupère une liste de livreurs suceptibles de pouvoir prendre
            // en charge la livraison
            livreurs = ldao.trouverLivreurDispoParPoids(c.calculateWeight());

            if (livreurs.size() > 0) {
                // Calcul de la durée de livraison estimée pour chaque livreur
                // livreur -> restau -> client
                for (int i = 0; i < livreurs.size(); i++) {

                    // Pour différencier un drone d'un Humain
                    if (livreurs.get(i) instanceof Drone) {
                        //Calcul durée pour Drone 
                        long dureeDroneVersRestaurant =
                                calculDureeDrone((Drone) livreurs.get(i), livreurs.get(i).getLatitude(),
                                        livreurs.get(i).getLongitude(), r.getLatitude(), r.getLongitude());

                        long dureeRestaurantVersClient =
                                calculDureeDrone((Drone) livreurs.get(i), r.getLatitude(), r.getLongitude(),
                                        c.getClient().getLatitude(), c.getClient().getLongitude());

                        long dureeLivraison = dureeDroneVersRestaurant + dureeRestaurantVersClient;

                        livreurs.get(i).setTempsLivraison(dureeLivraison);
                    } else if (livreurs.get(i) instanceof Humain) {
                        String[] adrDepart = {livreurs.get(i).getAdresse(), r.getAdresse()};
                        String[] adrArrivee = {r.getAdresse(), c.getClient().getAdresse()};

                        long dureeLivraison =
                                ServiceTechnique.calculDureeHumain(adrDepart, adrArrivee, TravelMode.BICYCLING);
                        livreurs.get(i).setTempsLivraison(dureeLivraison);
                    }
                }

                // on trie : ordre croissant du temps estimé
                Collections.sort(livreurs);

                for (int i = 0; i < livreurs.size(); i++) {
                    System.out.println(livreurs.get(i).getId() + " - " +
                            livreurs.get(i).getTempsLivraison());
                }

                int cpt = 0;
                while (!commandeValidee && cpt < livreurs.size()) {
                    JpaUtil.ouvrirTransaction();
                    try {
                        // on spécifie le livreur qui va faire la commande et il devient 
                        // indisponible (le 1er la liste car ordre croissant)
                        c.setLivreur(livreurs.get(cpt));
                        livreurs.get(cpt).setEtat(false);

                        c.setRestaurant(r);
                        c.setDateEnregistrementCommande(new Date());

                        //test
                        c.getClient().getListeCommandes().add(c);

                        cdao.create(c);
                        ldao.merge(livreurs.get(cpt));

                        JpaUtil.validerTransaction();

                        commandeValidee = true;
                        ServiceTechnique.mailInformationLivreur(c);
                        //break;

                    } catch (Exception ex) {
                        System.out.println("Problème de concurence d'accès sur un livreur");
                        if (ex.getCause() instanceof OptimisticLockException) {
                            cpt++;
                        }
                    }
                }
            }
        }


        JpaUtil.fermerEntityManager();
        return commandeValidee;
    }

    /**
     * Service qui permet d'obtenir la liste des restaurants partenaires du
     * programme GustatIF
     *
     * @return une liste de restaurants ou null s'il n'y a pas de restaurants
     */
    public List<Restaurant> restaurantsPartenaires() {
        JpaUtil.creerEntityManager();

        RestaurantDAO rdao = new RestaurantDAO();
        List<Restaurant> listeRestaurants = null;

        try {
            listeRestaurants = rdao.findAll();
        } catch (Exception ex) {
            listeRestaurants = null;
        }


        JpaUtil.fermerEntityManager();
        return listeRestaurants;
    }

    /**
     * Service qui permet d'obtenir les listes des livreurs partenaires du
     * du programme GustatIF
     *
     * @return une liste de livreur ou null si pas de livreur
     */
    public List<Livreur> livreursPartenaires() {
        JpaUtil.creerEntityManager();

        LivreurDAO ldao = new LivreurDAO();
        List<Livreur> listeL = null;

        try {
            listeL = ldao.findAll();
        } catch (Exception ex) {
        }


        JpaUtil.fermerEntityManager();
        return listeL;
    }

    /**
     * Service qui permet d'obtenir une liste de tous les clients inscrits au
     * programme GustatIF
     *
     * @return une liste de tous les clients ou null si pas de clients
     */
    public List<Client> clientsGustatif() {
        JpaUtil.creerEntityManager();

        ClientDAO cldao = new ClientDAO();
        List<Client> listeC = null;

        try {
            listeC = cldao.findAll();
        } catch (Exception ex) {
        }


        JpaUtil.fermerEntityManager();
        return listeC;
    }

    /**
     * Service qui permet d'obtenir la liste des produits disponibles dans un
     * restaurant.
     *
     * @param r le restaurant dont on veut connaitre la liste de produits
     * @return une liste des produits disponibles ou null s'il n'y a aucun produit
     */
    public List<Produit> produitsDisponibles(Restaurant r) {
        JpaUtil.creerEntityManager();

        RestaurantDAO rdao = new RestaurantDAO();
        List<Produit> listeProduits = null;

        try {
            listeProduits = rdao.trouverProduitRestaurant(r.getId());
        } catch (Exception ex) {
            listeProduits = null;
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }


        JpaUtil.fermerEntityManager();
        return listeProduits;
    }

    /**
     * Service qui permet de cloturer une livraison donnée en paramètre.
     * La date de cloture sera la date d'appel à cette méthode.
     *
     * @param c la commande à cloturer
     */
    public void cloturerLivraison(Commande c) {
        JpaUtil.creerEntityManager();

        CommandeDAO cdao = new CommandeDAO();
        c.setDateLivraisonCommande(new Date());

        LivreurDAO ldao = new LivreurDAO();

        JpaUtil.ouvrirTransaction();
        try {
            cdao.merge(c);
            c.getLivreur().setEtat(true);
            ldao.merge(c.getLivreur());
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
        }

        JpaUtil.fermerEntityManager();
    }

    /**
     * Service qui permet de faire une recherche de commandes en fonction du nom
     * du client, de son prénom de son numéro de commande et de la date
     * d'enregistrement de la commande.
     * Il renvoi une liste de commande en accord avec les paramètre de recherche
     * fournis en paramètres.
     *
     * @param nom            le nom du client ou null si on ne veut pas considérer ce
     *                       paramètre pour la recherche
     * @param prenom         le prénom du client ou null si on ne veut pas considérer ce
     *                       paramètre pour la recherche
     * @param numCommande    le numéro de commande ou null si on ne veut pas considérer
     *                       ce paramètre pour la recherche
     * @param date           date à partir de laquelle on va effectuer la recherche. Les
     *                       commandes retournées seront celles qui ont une date d'enregistrement
     *                       supérieure à la date donnée en paramètre.
     * @param dateLivNonRens boolean qui permet de spécifier si la recherche
     *                       est dans un but de cloture de livraison ou non. Il vautdra true si on veut
     *                       cloturer une livraison ou false sinon
     * @return une liste de commande qui correspond à tous les critères de
     * recherche donnés en paramètres ou null si aucune commande ne correspond.
     */
    public List<Commande> rechercherLivraison(String nom, String prenom,
                                              Long numCommande, Date date, boolean dateLivNonRens) {
        JpaUtil.creerEntityManager();

        List<Commande> listeCommande = null;

        // On recupère toutes les commandes 
        listeCommande = this.rechercherLivraison(dateLivNonRens);

        if (listeCommande != null) {
            for (int i = 0; i < listeCommande.size(); i++) {
                boolean removed = false;

                if (!removed && nom != null
                        && listeCommande.get(i).getClient().getNom().compareTo(nom) != 0) {
                    removed = true;
                    listeCommande.remove(i);
                    i -= 1;
                }
                if (!removed && prenom != null
                        && listeCommande.get(i).getClient().getPrenom().compareTo(prenom) != 0) {
                    removed = true;
                    listeCommande.remove(i);
                    i -= 1;
                }
                if (!removed && numCommande != null
                        && listeCommande.get(i).getId() != (long) numCommande) {
                    removed = true;
                    listeCommande.remove(i);
                    i -= 1;
                }
                //permet d'enlever les commandes dont la date d'enregistrement est 
                //après la date donnée en paramètre
                if (!removed && date != null
                        && !date.before(listeCommande.get(i).getDateEnregistrementCommande())) {
                    removed = true;
                    listeCommande.remove(i);
                    i -= 1;
                }

            }
        }

        if (listeCommande != null && listeCommande.isEmpty()) {
            listeCommande = null;
        }


        JpaUtil.fermerEntityManager();
        return listeCommande;
    }


    /**
     * Service qui permet de faire une recherche de commande dans un but de
     * cloture ou non.
     *
     * @param dateLivNonRens attribut qui spécifie si on veut obtenir les commandes
     *                       avec des dates de livraison non renseignées ( l'attribut vaudra vrai dans
     *                       ce cas et faux sinon)
     * @return la liste de toutes les commandes qui correspondent à la recherche
     */
    private List<Commande> rechercherLivraison(boolean dateLivNonRens) {
        JpaUtil.creerEntityManager();

        CommandeDAO cdao = new CommandeDAO();
        List<Commande> listeCommande = null;

        try {
            listeCommande = cdao.findAll();
            if (dateLivNonRens) {
                for (int i = 0; i < listeCommande.size(); i++) {
                    if (listeCommande.get(i).getDateLivraisonCommande() != null) {
                        listeCommande.remove(i);
                        i -= 1;
                    }
                }
            } else {
                for (int i = 0; i < listeCommande.size(); i++) {
                    if (listeCommande.get(i).getDateLivraisonCommande() == null) {
                        listeCommande.remove(i);
                        i -= 1;
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Problème lors de la recherche de commande");
            //Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }


        JpaUtil.fermerEntityManager();
        return listeCommande;
    }

    /**
     * Service de création des livreurs qui pourront livrer les commandes
     */
    public void creationLivreur() {

        Humain h = new Humain(true, 4000.0, "16 rue grenette, 69002 Lyon", "Maucourt",
                "Baptiste");
        Humain h1 = new Humain(true, 10000.0, "2 impasse de Broglie, 84700 Sorgues", "Laruc",
                "Michel");
        Humain h2 = new Humain(true, 3000.0, "20 Avenue albert einstein, 69100 Villeurbanne", "Bidule",
                "Thomas");
        Humain h3 = new Humain(true, 2500.0, "Place Ampère, Lyon", "Point",
                "Thomas");
        Humain h4 = new Humain(true, 1000.0, "Rue des Archers, Lyon", "Lamarre",
                "Philipe");
        Humain h5 = new Humain(true, 990.0, "Rue Jean-Baldassini, Lyon", "Maranzana",
                "Mathieu");
        Humain h6 = new Humain(true, 2300.0, "Rue Barrème, Lyon", "Wick",
                "John");
        Humain h7 = new Humain(true, 500.0, "Rue Bataille, Lyon", "Pottier",
                "Luc");
        Humain h8 = new Humain(true, 420.0, "Rue Bourgelat, Lyon", "Trump",
                "Donald");
        Humain h9 = new Humain(true, 1736.0, "Rue Bichat, Lyon", "Fillon",
                "Francine");
        Humain h10 = new Humain(true, 1220.0, "Avenue de Birmingham, Lyon", "Macron",
                "Brigitte");
        Humain h11 = new Humain(true, 2500.0, "Rue Bony, Lyon", "Potter",
                "Harry");
        Humain h12 = new Humain(true, 1110.0, "Rue Bugeaud, Lyon", "Norris",
                "Chuck");
        Humain h13 = new Humain(true, 230.0, "Rue Marc-Bloch, Lyon", "Point",
                "Vivian");
        Humain h14 = new Humain(true, 690.0, "Rue Sœur-Bouvier, Lyon", "Point",
                "Thomas");
        Humain h15 = new Humain(true, 410.0, "Rue de Bourgogne, Lyon", "Putin",
                "Vladimir");
        Humain h16 = new Humain(true, 860.0, "Rue du Commandant-Charcot, Lyon", "Roussel",
                "Chloe");
        Humain h17 = new Humain(true, 780.0, "Rue Chevreul, Lyon", "Chalbos",
                "Sara");
        Humain h18 = new Humain(true, 1556.0, "Quai Jules-Courmont, Lyon", "Sanchez",
                "Bastien");
        Humain h19 = new Humain(true, 700.0, "Rue Constantine, Lyon", "Gay",
                "Thomas");
        Humain h20 = new Humain(true, 960.0, "Rue de Cuire, Lyon", "Ros",
                "Benjamin");
        Humain h21 = new Humain(true, 753.0, "Rue des Cuirassiers, Lyon", "Hamelin",
                "Hubert");
        Humain h22 = new Humain(true, 951.0, "Quai des Célestins, Lyon", "Fontaine",
                "Romain");
        Humain h23 = new Humain(true, 456.3, "Rue du Colonel-Chambonnet, Lyon", "Youchevko",
                "Igor");
        Humain h24 = new Humain(true, 596.1, "Rue des Capucins, Lyon", "Lemaitre",
                "Christophe");
        Humain h25 = new Humain(true, 10003.0, "Quai du Commerce, Lyon", "Bolt",
                "Husain");
        Humain h26 = new Humain(true, 2560.4, "Rue Joliot-Curie, Lyon", "DiCaprio",
                "Leonardo");
        Humain h27 = new Humain(true, 1000.0, "Rue de Saint-Cyr, Lyon", "Cruz",
                "Penelope");
        Humain h28 = new Humain(true, 1000.0, "Rue Paul-Duvivier, Lyon", "Simeone",
                "Ricardo");
        Humain h29 = new Humain(true, 1000.0, "Rue Desaix, Lyon", "Tretaux",
                "Hugues");
        Humain h30 = new Humain(true, 1000.0, "Rue Dubois, Lyon", "Grant",
                "Justin");
        Humain h31 = new Humain(true, 1000.0, "Boulevard de la Duchère, Lyon", "Dupont",
                "Georges");
        Humain h32 = new Humain(true, 1556.0, "Rue du Dauphiné, Lyon", "Patalo",
                "Lilian");
        Humain h33 = new Humain(true, 1556.0, "Rue Edmond-Locard, Lyon", "Pinochet",
                "marc");
        Humain h34 = new Humain(true, 1556.0, "Rue du Commandant-Faurax, Lyon", "Tatulli",
                "Sabinot");
        Humain h35 = new Humain(true, 1556.0, "Quai Fulchiron, Lyon", "Matrichain",
                "Olivier");
        Humain h36 = new Humain(true, 2300.0, "Place Jules-Ferry, Lyon", "Point",
                "Sandrine");
        Humain h37 = new Humain(true, 2300.0, "Rue du Cardinal-Gerlier, Lyon", "Garcia",
                "Lea");
        Humain h38 = new Humain(true, 2300.0, "Avenue de Grande-Bretagne, Lyon", "Parciflot",
                "thom");
        Humain h39 = new Humain(true, 2300.0, "Rue Sainte-Hélène, Lyon", "Torpino",
                "Ricardo");
        Humain h40 = new Humain(true, 2300.0, "Rue Sainte Geneviève, Lyon", "Poituvier",
                "ulysse");

        Drone d0 = new Drone(true, 100000.0, "Paris", 20.0);
        Drone d1 = new Drone(true, 500000.0, "Lyon", 50.0);
        Drone d2 = new Drone(true, 25000.0, "Sorgues", 5.0);
        Drone d3 = new Drone(true, 15000.0, "Nice", 13.0);
        Drone d4 = new Drone(true, 15000.0, "Rue Jaboulay, Lyon", 10.0);
        Drone d5 = new Drone(true, 15000.0, "Place Saint-Irénée, Lyon", 16.0);
        Drone d6 = new Drone(true, 15000.0, "Rue d'Ivry, Lyon", 4.0);
        Drone d7 = new Drone(true, 15000.0, "Rue Saint-Jean, Lyon", 5.6);
        Drone d8 = new Drone(true, 15000.0, "Pont Alphonse-Juin, Lyon", 8.0);
        Drone d9 = new Drone(true, 15000.0, "Avenue Jean-Jaurès, Lyon", 3.0);


        Livreur[] tabLivreurs = {h, h1, h2, h3, h4, h5, h6, h7, h8, h9, h10, h11, h12, h13,
                h14, h15, h16, h17, h18, h19, h20, h21, h22, h23, h24, h25, h26, h27, h28, h29, h30,
                h31, h32, h33, h34, h35, h36, h37, h38, h39, h40,
                d0, d1, d2, d3, d4, d5, d6, d7, d8, d9};

        this.creerLivreur(tabLivreurs);
    }

    /**
     * Service de création de livreurs à partir de livreurs rangés dans un tableau
     *
     * @param livreurs un tableau de livreurs
     */
    private void creerLivreur(Livreur[] livreurs) {
        JpaUtil.creerEntityManager();

        JpaUtil.ouvrirTransaction();

        LivreurDAO ldao = new LivreurDAO();
        try {

            for (Livreur l : livreurs) {
                l.setLatitude(ServiceTechnique.getLatLngFromAdress(l.getAdresse()).lat);
                l.setLongitude(ServiceTechnique.getLatLngFromAdress(l.getAdresse()).lng);
                ldao.create(l);
            }
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }

        JpaUtil.fermerEntityManager();
    }

}
        
