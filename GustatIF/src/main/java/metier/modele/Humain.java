/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import javax.persistence.Entity;

/**
 *
 * @author thomas
 */
@Entity
public class Humain extends Livreur{
    
    String nom;
    String prenom;
    
    /**
     * Constructeur par défault 
     */
    public Humain(){
    }
    
    /**
     * Constructeur de la classe Humain
     * @param etat true si le livreur est dispo, false sinon
     * @param poidsMax charge maximale que le livreur peut transporter 
     * @param adresse adresse initiale du livreur 
     * @param nom le nom du livreur 
     * @param prenom le prénom du livreur 
     */
    public Humain(boolean etat, Double poidsMax, String adresse, String nom, 
                    String prenom)
    {
        super(etat, poidsMax, adresse);
        this.nom=nom;
        this.prenom=prenom;
    }
    
    /**
     * Permet d'obtenir le nom du livreur 
     * @return
     */
    public String getNom(){
        return this.nom;
    }
    
    /**
     * Permet d'obtenir le prenom du livreur 
     * @return
     */
    public String getPrenom(){
        return this.prenom;
    }
    
    /**
     * Permet de changer le nom du livreur 
     * @param nom
     */
    public void setNom(String nom){
        this.nom=nom;
    }
    
    /**
     * Permet de changer le prenom du livreur 
     * @param prenom
     */
    public void setPrenom(String prenom){
        this.prenom=prenom;
    }

    @Override
    public int compareTo(Livreur o) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        long compareTemps = ((Livreur)o).getTempsLivraison();
        
        // ordre croissant
        return (int)this.getTempsLivraison()-(int)compareTemps;
        
    }
    
}
