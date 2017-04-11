/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author thomas
 */

//todo penser à faire la liaison avec produit

@Entity 
public class Commande implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    //private Date dateEnregistrementCommande;
    private Date dateEnregistrementCommande;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLivraisonCommande;
    
    @ManyToOne
    private Livreur livreur;
    
    @ManyToOne
    private Client client;
    
    @ManyToOne
    private Restaurant restaurant;
    
    //@OneToMany(mappedBy="primaryKey.commande")
    @OneToMany(cascade = CascadeType.ALL,mappedBy="commande", orphanRemoval=true)
    private List<ProduitCommande> produitsCommandes
            = new ArrayList<ProduitCommande>();
    
    /**
     * Constructeur par défault
     */
    public Commande(){   
        livreur = null;
        client =null;
        restaurant= null;
        dateEnregistrementCommande= null;
        dateLivraisonCommande = null;
    }
    
    /**
     * Constructeur d'une commande à partir d'un client
     * @param c le client qui passe la commande
     */
    public Commande(Client c )
    {
        this.client =c;
    }


    /* Setter */

    /**
     * Permet de changer la date d'enregistrement de la commande 
     * @param dateEnregistrementCommande nouvelle date 
     */
    public void setDateEnregistrementCommande(Date dateEnregistrementCommande) {
        this.dateEnregistrementCommande = dateEnregistrementCommande;
    }
    
    /**
     * Permet d'enregistrer la date de livraison de la commande 
     * @param dateLivraisonCommande la date de livraison
     */
    public void setDateLivraisonCommande(Date dateLivraisonCommande) {
        this.dateLivraisonCommande = dateLivraisonCommande;
    }

    /**
     * Permet d'affecter un livreur à une commande 
     * @param livreur le livreur qui effectue la livraison de la commande
     */
    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }

    /**
     * Permet d'affecter un restaurant à une commande 
     * @param restaurant 
     */
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    
    /* Getter */

    /**
     * Permet d'obtenir l'ID de la commande 
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * Permet d'obtenir la date d'enregistrement de la commande 
     * @return la date d'enregistrement de la commande 
     */
    public Date getDateEnregistrementCommande() {
        return dateEnregistrementCommande;
    }

    /**
     * Permet d'obtenir la date de livraison de la commande 
     * @return la date de livraison, null si elle n'a pas encore été livrée 
     */
    public Date getDateLivraisonCommande() {
        return dateLivraisonCommande;
    }

    /**
     * Permet d'obtenir le livreur qui effectuera la commande 
     * @return le livreur ou null si aucun livreur n'est attribué 
     */
    public Livreur getLivreur() {
        return livreur;
    }

    /**
     * Permet d'obtenir le client qui passe la commande 
     * @return le client 
     */
    public Client getClient() {
        return client;
    }  

    /**
     * Permet d'obtenir le restaurant dans lequel est passé la commande 
     * @return 
     */
    public Restaurant getRestaurant() {
        return restaurant;
    }
    
    
    
    /**
     * Permet d'obtenir la liste d'objets ProduitCommande,composé d'un produit et 
     * de sa quantité, qui composent la commande.
     * @return 
     */
    public List<ProduitCommande> getProduitsCommandes() {
        return produitsCommandes;
    }
    
    /**
     * Methode qui permet d'ajouter un produit à la commande. 
     * Si le produit existe dans la liste de produits commandé on augmente de 1 
     * la quantité de ce produit, si non on l'ajoute et sa quantité vaut 1.
     * 
     * @param p le produit à ajouter 
     */
    public void ajouterProduit(Produit p)
    {
        List<ProduitCommande> listeProduitsCommande =  this.getProduitsCommandes();
        boolean present = false;
            
        for(ProduitCommande pc : listeProduitsCommande)
        {
            // on verifie si un des produits est égal à celui donné en paramètre
            // on ajoute 1 à la quantité s'il existe 
            // on cree un nv ProduitCommande sinon
            if(pc.getProduit().equals(p))
            {
                pc.setQuantite(pc.getQuantite()+1);
                present=true;
                break;
            }
        }

        if(!present)
        {
            ProduitCommande pc = new ProduitCommande(this, p);
            listeProduitsCommande.add(pc);
        }
    }
    
    /**
     * Methode qui permet de retirer un produit à la commande. 
     * Si le produit est présent dans la liste de produits commandés on retire 1
     * à la quantité. Si la quantité vaut 0 après retrait on l'enlève de la 
     * liste.
     * 
     * @param p le produit à retirer 
     */
    public void retirerProduit(Produit p)
    {
        List<ProduitCommande> listeProduitsCommande =  this.getProduitsCommandes();
        boolean present = false;
        
        for(ProduitCommande pc : listeProduitsCommande)
        {
            // on verifie si un des produits est égal à celui donné en paramètre
            // on ajoute 1 à la quantité s'il existe 
            // on cree un nv ProduitCommande sinon
            if(pc.getProduit().equals(p))
            {
                int qte = pc.getQuantite()-1;
                if(qte!=0)
                {
                    pc.setQuantite(qte);
                    break;
                }
                else 
                {
                    listeProduitsCommande.remove(pc);
                    break;
                }
            }
        }        
    }
    
    /**
     *  Méthode qui permet de calculer le poids total d'une commande.
     * 
     * @return le poids total de la commande 
     */
    public double calculateWeight()
    {
        double res=0;
        
        for(int i=0; i<this.produitsCommandes.size();i++)
        {
            res +=
            this.produitsCommandes.get(i).getQuantite()*this.produitsCommandes.get(i).getProduit().getPoids();
        }
        
        /*System.out.println("\t-----------------------------------------------");
        System.out.println("\tservice technique calcul poids");
        System.out.println("\tpoids total commande : " + res);
        System.out.println("\t-----------------------------------------------");*/
        return res;
    }
    
    /**
     * Service qui permet de calculer le prix total de la commande.
     *  
     * @return le prix total à payer 
     */
    public double calculerPrixCommande()
    {
        double res=0;
        
        for(int i=0; i<this.produitsCommandes.size();i++)
        {
            res +=
            produitsCommandes.get(i).getQuantite()*produitsCommandes.get(i).getProduit().getPrix();
        }
        
        return res;
    }
   
    
}
