/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author thomas
 */

@Entity
public class ProduitCommande implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private Commande commande;
    
    @ManyToOne
    private Produit produit;
    
    private int quantite;
    
    /**
     * Constructeur par défault
     */
    public ProduitCommande() {
    }
    
    /**
     * Constructeur de la classe ProduitCommande 
     * 
     * @param c la commande à laquelle apartiennent les produits
     * @param p le produit qu'on veut ajouter 
     */
    public ProduitCommande(Commande c, Produit p){
        this.produit=p;
        this.commande=c;
        quantite=1;
    }

    /**
     * Permet d'obtenir la commande
     * @return
     */
    public Commande getCommande(){
        return commande;
    }
    
    /**
     * Permet d'obtenir le produit 
     * @return
     */
    public Produit getProduit(){
        return produit;
    }
    
    /**
     * Permet d'obtenir la quantité du produit 
     * @return
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * Permet de changer la quantité d'un produit 
     * @param quantite la nouvelle quantité 
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    
}
