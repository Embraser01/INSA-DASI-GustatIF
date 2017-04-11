/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import metier.modele.Client;

import java.util.Date;
import java.util.List;
import metier.modele.Commande;
import metier.modele.Livreur;
import metier.modele.Produit;
import metier.modele.ProduitCommande;
import metier.modele.Restaurant;
import metier.service.ServiceMetier;
import util.JpaUtil;
import util.Saisie;


/**
 *
 * @author thomas
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        // TODO code application logic here
        JpaUtil.init();
        
        JpaUtil.creerEntityManager();  // à laisser ici // 
        
        mainGustatif();

        JpaUtil.destroy();
    }
    
    public static void mainGustatif()
    {
        ServiceMetier sm = new ServiceMetier();
        System.out.println(
        " _______           _______ _________ _______ _________ _ _________ _______ \n" +
        "(  ____ \\|\\     /|(  ____ \\\\__   __/(  ___  )\\__   __/( )\\__   __/(  ____ \\\n" +
        "| (    \\/| )   ( || (    \\/   ) (   | (   ) |   ) (   |/    ) (   | (    \\/\n" +
        "| |      | |   | || (_____    | |   | (___) |   | |         | |   | (__    \n" +
        "| | ____ | |   | |(_____  )   | |   |  ___  |   | |         | |   |  __)   \n" +
        "| | \\_  )| |   | |      ) |   | |   | (   ) |   | |         | |   | (      \n" +
        "| (___) || (___) |/\\____) |   | |   | )   ( |   | |      ___) (___| )      \n" +
        "(_______)(_______)\\_______)   )_(   |/     \\|   )_(      \\_______/|/     ");
        
        System.out.println("\nQuel rôle jouez vous?\n");
        
        boolean continuer =true ;
        
        
        System.out.println("1- Client");
        System.out.println("2- Gestionnaire/livreur");
        System.out.println("3- Création des livreurs");
        
        int choixAction = Saisie.lireInteger("Votre choix : ");
        
        if(choixAction==1)
        {
            System.out.println("Que voulez-vous faire ?\n");
            System.out.println("1- Inscription");
            System.out.println("2- Connexion");

            choixAction = Saisie.lireInteger("Votre choix : ");

            if(choixAction == 1)
            {
                System.out.println("Inscription\n");
                String nom = Saisie.lireChaine("Votre nom : ");
                String prenom = Saisie.lireChaine("Votre prenom : ");
                String adresse = Saisie.lireChaine("Votre adresse : ");
                String mail = Saisie.lireChaine("Votre mail : ");

                Client c = new Client(nom, prenom, mail, adresse);

                sm.inscription(c);
                
            }else if(choixAction ==2)
            {
                System.out.println("Connexion\n");
                String mail = Saisie.lireChaine("Votre mail : ");
                long mdp = (long)Saisie.lireLong("Votre mdp : ");

                Client c= null;
                c = sm.connexion(mail, mdp);

                if(c!=null)
                {

                    System.out.println("Connexion réussie\n");
                    System.out.println("Bonjour " + c.getPrenom() + ",");

                    System.out.println("\nQue voulez-vous faire ?");

                    while(continuer)
                    {
                        continuer =false;
                        System.out.println("\t1-Modifier vos informations");
                        System.out.println("\t2-Passer une commande");
                        System.out.println("\t3-Voir vos commandes");
                        System.out.println("\t4-Afficher les informations sur gustat'IF");

                        choixAction = Saisie.lireInteger("Votre choix : ");

                        if(choixAction ==1)
                        {
                            String nom = Saisie.lireChaine("Votre nv nom : ");
                            String prenom = Saisie.lireChaine("Votre nv prenom : ");
                            mail = Saisie.lireChaine("Votre nv mail : ");
                            
                            if(nom.compareTo("")!=0) {c.setNom(nom);}
                            if(prenom.compareTo("") !=0 ) {c.setPrenom(prenom);}
                            if(mail.compareTo("") !=0){c.setMail(mail);}

                            c = sm.majInfoClient(c);

                            System.out.println("\nVos informations : ");
                            System.out.println("Votre nom est : " + c.getNom());
                            System.out.println("Votre prenom est : " + c.getPrenom());
                            System.out.println("Votre mail est : " + c.getMail());

                        }else if(choixAction == 2)
                        {
                            Commande cde = new Commande(c);

                            List<Restaurant> listeR = sm.restaurantsPartenaires();

                            System.out.println("\nRestaurants : \n");
                            for(int i=0; i<listeR.size(); i++)
                            {
                                System.out.println(i + "-" + listeR.get(i).getDenomination());
                                System.out.println("\t" + listeR.get(i).getDescription() + "\n");
                            }

                            choixAction = Saisie.lireInteger("\nVotre choix de restaurant : ");

                            System.out.println("\nProduits disponible\n");
                            List<Produit> listeP = sm.produitsDisponibles(listeR.get(choixAction));

                            for(int i=0; i<listeP.size(); i++)
                            {
                                System.out.println(i + "-" + listeP.get(i).getDenomination());
                                System.out.println("\t" + listeP.get(i).getDescription());
                                System.out.println("\tPrix : " + listeP.get(i).getPrix());
                            }

                            do
                            {
                                choixAction = Saisie.lireInteger("\nVotre choix de produit : ");
                                cde.ajouterProduit(listeP.get(choixAction));
                                System.out.println("\nEntrez NON pour continuer selection produit");
                            }while(Saisie.lireChaine("Avez vous fini ?").compareTo("NON")==0);

                            System.out.println("\n-------------------------------------------------");
                            System.out.println("Récapitulatif panier");

                            List<ProduitCommande> lpc = cde.getProduitsCommandes();
                            for(int i=0; i<lpc.size();i++)
                            {
                                System.out.println("\t" + lpc.get(i).getProduit().getDenomination() + " qte : "
                                + lpc.get(i).getQuantite());
                            }

                            System.out.println("\tPoids commande : "+ cde.calculateWeight());
                            System.out.println("\tPrix commande : " + cde.calculerPrixCommande());
                            System.out.println("-------------------------------------------------");
                            System.out.println("\nTemporisation avant de valider la commande");
                            Saisie.pause();

                            boolean succes = sm.validerCommande(cde);

                            if(succes)
                            {
                                System.out.println("Commande validée");
                            }
                            else
                            {
                                System.out.println("Aucun livreur dispo pour effectuer la commande");
                            }

                        }else if(choixAction==3)
                        {
                            List<Commande> lc = c.getListeCommandes();
                            System.out.println("\nID Commande  |  Date Enregistrement          |     Date Livraison            |    ID Livreur\n");
                            for(int i=0; i<lc.size();i++)
                            {
                                System.out.print("    "  +lc.get(i).getId() + "     | " + lc.get(i).getDateEnregistrementCommande() + " | "
                                        + lc.get(i).getDateLivraisonCommande());
                                if(lc.get(i).getDateLivraisonCommande() != null)
                                {
                                        System.out.println(" |        " + lc.get(i).getLivreur().getId());
                                }
                                else
                                {
                                    System.out.println("                          |        " 
                                            + lc.get(i).getLivreur().getId());
                                }
                            }
                        }else if (choixAction ==4)
                        {
                            System.out.println("Info Gustat'IF");
                            System.out.println("Année de création 2017");
                            System.out.println("Réalisé par Baptiste Maucourt et Thomas Point");  
                        }  


                        if(Saisie.lireInteger("\nQue voulez-vous faire ? "
                                + "\n1-Continuer\n2-Quiter\nVotre choix : ")==1)
                        {
                            continuer=true;
                        }
                    }
                    
                }else
                {
                  
                    do
                    {
                        System.out.println("Identifiant ou mot de passe incorrect");
                        mail = Saisie.lireChaine("Votre mail : ");
                        mdp = (long)Saisie.lireLong("Votre mdp : ");
                        c=sm.connexion(mail, mdp);
                    }while(c==null);
                    
                    System.out.println("Connexion réussie\n");
                    System.out.println("Bonjour " + c.getPrenom() + ",");

                    System.out.println("\nQue voulez-vous faire ?");

                    while(continuer)
                    {
                        continuer =false;
                        System.out.println("\t1-Modifier vos informations");
                        System.out.println("\t2-Passer une commande");
                        System.out.println("\t3-Voir vos commandes");
                        System.out.println("\t4-Afficher les informations sur gustat'IF");

                        choixAction = Saisie.lireInteger("Votre choix : ");

                        if(choixAction ==1)
                        {
                            String nom = Saisie.lireChaine("Votre nv nom : ");
                            String prenom = Saisie.lireChaine("Votre nv prenom : ");
                            mail = Saisie.lireChaine("Votre nv mail : ");
                            
                            if(nom.compareTo("")!=0) {c.setNom(nom);}
                            if(prenom.compareTo("") !=0 ) {c.setPrenom(prenom);}
                            if(mail.compareTo("") !=0){c.setMail(mail);}

                            c = sm.majInfoClient(c);

                            System.out.println("\nVos informations : ");
                            System.out.println("Votre nom est : " + c.getNom());
                            System.out.println("Votre prenom est : " + c.getPrenom());
                            System.out.println("Votre mail est : " + c.getMail());

                        }else if(choixAction == 2)
                        {
                            Commande cde = new Commande(c);

                            List<Restaurant> listeR = sm.restaurantsPartenaires();

                            System.out.println("\nRestaurants : \n");
                            for(int i=0; i<listeR.size(); i++)
                            {
                                System.out.println(i + "-" + listeR.get(i).getDenomination());
                                System.out.println("\t" + listeR.get(i).getDescription() + "\n");
                            }

                            choixAction = Saisie.lireInteger("\nVotre choix de restaurant : ");

                            System.out.println("\nProduits disponible\n");
                            List<Produit> listeP = sm.produitsDisponibles(listeR.get(choixAction));

                            for(int i=0; i<listeP.size(); i++)
                            {
                                System.out.println(i + "-" + listeP.get(i).getDenomination());
                                System.out.println("\t" + listeP.get(i).getDescription());
                                System.out.println("\tPrix : " + listeP.get(i).getPrix());
                            }

                            do
                            {
                                choixAction = Saisie.lireInteger("\nVotre choix de produit : ");
                                cde.ajouterProduit(listeP.get(choixAction));
                                System.out.println("\nEntrez NON pour continuer selection produit");
                            }while(Saisie.lireChaine("Avez vous fini ?").compareTo("NON")==0);

                            System.out.println("\n-------------------------------------------------");
                            System.out.println("Récapitulatif panier");

                            List<ProduitCommande> lpc = cde.getProduitsCommandes();
                            for(int i=0; i<lpc.size();i++)
                            {
                                System.out.println("\t" + lpc.get(i).getProduit().getDenomination() + " qte : "
                                + lpc.get(i).getQuantite());
                            }

                            System.out.println("\tPoids commande : "+ cde.calculateWeight());
                            System.out.println("\tPrix commande : " + cde.calculerPrixCommande());
                            System.out.println("-------------------------------------------------");
                            System.out.println("\nTemporisation avant de valider la commande");
                            Saisie.pause();

                            boolean succes = sm.validerCommande(cde);

                            if(succes)
                            {
                                System.out.println("Commande validée");
                            }
                            else
                            {
                                System.out.println("Aucun livreur dispo pour effectuer la commande");
                            }

                        }else if(choixAction==3)
                        {
                            List<Commande> lc = c.getListeCommandes();
                            System.out.println("\nID Commande  |  Date Enregistrement          |     Date Livraison            |    ID Livreur\n");
                            for(int i=0; i<lc.size();i++)
                            {
                                System.out.print("    "  +lc.get(i).getId() + "     | " + lc.get(i).getDateEnregistrementCommande() + " | "
                                        + lc.get(i).getDateLivraisonCommande());
                                if(lc.get(i).getDateLivraisonCommande() != null)
                                {
                                        System.out.println(" |        " + lc.get(i).getLivreur().getId());
                                }
                                else
                                {
                                    System.out.println("                          |        " 
                                            + lc.get(i).getLivreur().getId());
                                }
                            }
                        }else if (choixAction ==4)
                        {
                            System.out.println("Info Gustat'IF");
                            System.out.println("Année de création 2017");
                            System.out.println("Réalisé par Baptiste Maucourt et Thomas Point");  
                        }  


                        if(Saisie.lireInteger("\nQue voulez-vous faire ? "
                                + "\n1-Continuer\n2-Quiter\nVotre choix : ")==1)
                        {
                            continuer=true;
                        }
                    }
                    
                }
            }
            else
            {
                do
                {
                    System.out.println("Aucun action possible pour ce choix");
                    choixAction = Saisie.lireInteger("Votre nouveau choix : ");
                }while(choixAction<1 || choixAction >2 );
            }
        }else if(choixAction==2)
        {
            while(continuer){    
                
                System.out.println("\n1-Cloturer livraison");
                System.out.println("2-Voir adresse Restaurant, Livreurs, Clients (20 premiers)");
                System.out.println("3-Voir les livraisons terminées");

                choixAction = Saisie.lireInteger("Votre choix : ");
                
                if(choixAction==1)
                {
                    System.out.println("\nRecherche de la livraison à cloturer ");
                    System.out.println("(Appuyer sur entrer directement si vous ne voulez pas "
                            + "considerer le champs)");
                    System.out.println("(Entrez un nombre négatif pour ne pas considérer le "
                            + "numero de commande)");
                    String nom =null;
                    String prenom = null;
                    Long numCommande = null;
                    Date date = null;

                    nom = Saisie.lireChaine("Le nom de commande : ");
                    if(nom.compareTo("") ==0){ nom = null;}

                    prenom = Saisie.lireChaine("Le prenom de commande : ");
                    if(prenom.compareTo("") ==0){ prenom = null;}

                    numCommande = Saisie.lireLong("Le numero de commande : ");
                    if(numCommande <0) {numCommande=null;}

                    System.out.println("OUI pour considerer la date, ou enter si non");
                    String choix = Saisie.lireChaine("Voulez vous considerer la date ?");
                    if(choix.compareTo("OUI")==0)
                    {
                        int jour = Saisie.lireInteger("Jour : ");
                        int mois = Saisie.lireInteger("Mois : ");
                        date = new Date();
                        date.setDate(jour); date.setMonth(mois);
                    }

                    List<Commande> lc = sm.rechercherLivraison(nom, prenom, numCommande, date, true);
                    if(lc != null)
                    {
                        System.out.println("\nListe des commandes à clôturer : \n");
                        for(int i=0; i<lc.size();i++)
                        {
                            System.out.println(i+"-" + lc.get(i).getId() + " - " + 
                              lc.get(i).getClient().getNom()+ " - "+ lc.get(i).getClient().getPrenom()
                            + " - " + lc.get(i).getClient().getMail() + " - " + lc.get(i).getClient().getAdresse());
                        }

                        choixAction= Saisie.lireInteger("\nVotre choix : ");

                        sm.cloturerLivraison(lc.get(choixAction)); 
                    }else
                    {
                        System.out.println("Toutes les commandes ont déjà été cloturées ! ");
                    }
                }else if(choixAction==2)
                {
                    System.out.println("\n--------------------------------------------\n");
                    System.out.println("Adresses Restaurant :\n");
                    List<Restaurant> liste = sm.restaurantsPartenaires();
                    for(Restaurant el : liste)
                    {
                        System.out.println(el.getDenomination() + " - Lat : "
                                + el.getLatitude() + " - Long : "
                                        + el.getLongitude());
                    }
                    System.out.println("\n------------------------------------------\n");
                    System.out.println("Adresses Livreurs :\n");
                    List<Livreur> listeL = sm.livreursPartenaires();
                    for(Livreur l : listeL)
                    {
                        System.out.println(l.getId() + " - Lat : "
                                + l.getLatitude() + " - Long : "
                                        + l.getLongitude());
                    }
                    System.out.println("\n------------------------------------------\n");
                    System.out.println("Adresses Clients :\n");

                    List<Client> listeC = sm.clientsGustatif();
                    for(int i=0; i<20;i++)
                    {

                        System.out.println(listeC.get(i).getPrenom() + " - Lat : "
                                + listeC.get(i).getLatitude() + " - Long : "
                                        + listeC.get(i).getLongitude());
                    }
                }else if(choixAction==3)
                {
                    System.out.println("\nListe des livraisons terminées : \n");
                    List<Commande> listeC = null;
                    
                    listeC = sm.rechercherLivraison(null, null, null, null, false);
                    
                    if(listeC==null)
                    {
                        System.out.println("Aucune livraison terminée ! ");
                    }
                    else
                    {
                        System.out.println("ID Commande  |  Date Enregistrement          |     Date Livraison            |    ID Livreur\n");
                        for(Commande c  : listeC)
                        {
                            
                            System.out.println("    "  +c.getId() + "     | " + c.getDateEnregistrementCommande() + " | " + c.getDateLivraisonCommande()
                            + " |        " + c.getLivreur().getId());
                        }
                    }
                }
                
                System.out.println("\nVoulez vous continuer ?");
                System.out.println("1- oui");
                System.out.println("2- non");
                int chx = Saisie.lireInteger("Votre choix : ");
                if(chx == 1) 
                {   
                    continuer=true;
                }else{ 
                    continuer =false;
                }
            }
        }else if(choixAction==3)
        {
            sm.creationLivreur();
        }
        else
        {
            while(choixAction<1 || choixAction >2 )
            {
                System.out.println("Aucun action possible pour ce choix");
                choixAction = Saisie.lireInteger("Votre nouveau choix : ");
            }
        }
    }
    
}
    

