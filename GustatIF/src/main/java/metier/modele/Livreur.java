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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 *
 * @author thomas
 */

//TODO Rajouter les clauses manyToOne etc pour lier les tables entre elles 

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Livreur implements Serializable, Comparable<Livreur>{
    
    /**
     * L'id du livreur 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    
    @Version
    private long version;
    
    /**
     *L'état du livreur (true s'il est dispo et false sinon)
     */
    protected boolean etat;

    /**
     * Le poids max que le livreur peut transporter
     */
    protected Double poidsMax;
    
    /**
     *L'adresse initiale du livreur 
     */
    protected String adresse;
    
    @Transient
    long tempsLivraison;
    
    protected Double latitude ;
    
    protected Double longitude;
    
    /**
     * Constructeur par default
     */
    public Livreur(){
    }
    
    /**
     * Constructeur de la classe livreur 
     * @param etat disposnibilité du livreur (true si libre, false sinon)
     * @param poidsMax charge maximale qu'il peut porter 
     * @param adresse adresse initiale du livreur 
     */
    public Livreur(boolean etat, Double poidsMax, String adresse) {
        this.etat=etat;
        this.poidsMax=poidsMax;
        this.adresse=adresse;
        this.tempsLivraison=-10;
    }
    
    /**
     * Permet d'obtenir l'id du livreur 
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * Permet d'obtenir la temps de livraison estimé 
     * @return
     */
    public long getTempsLivraison() {
        return tempsLivraison;
    }
    
    /**
     * Permet de connaitre l'état d'un livreur 
     * @return
     */
    public boolean getEtat(){
        return etat;
    }
    
    /**
     * Permet d'obtenir la charge max que peut porter un lvreur 
     * @return
     */
    public Double getPoidsMax(){
        return poidsMax;
    }
    
    /**
     * Permet d'obtenir l'adresse initiale d'un livreur 
     * @return
     */
    public String getAdresse(){
        return adresse;
    }
    
    /**
     * Permet d'obtenir la latitude du drone 
     * @return 
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Permet d'obtenir la longitude  du drone 
     * @return
     */
    public Double getLongitude() {
        return longitude;
    }
    
    /**
     * Permet de changer l'état d'un livreur 
     * @param etat
     */
    public void setEtat(boolean etat) {
        this.etat=etat;
    }
     
    /**
     *Permet de changer l'adresse initiale d'un livreur 
     * @param adresse
     */
    public void setAdresse(String adresse){
        this.adresse=adresse;
    }
    
    /**
     * Permet de changer le poid max que peut porter un livreur 
     * @param poidsMax
     */
    public void setPoidsMax(Double poidsMax) {
        this.poidsMax=poidsMax;
    }
    
    /**
     * Permet d'affecter une valeur au temps de livraison estimé pour ce livreur 
     * @param tempsLivraison
     */
    public void setTempsLivraison(long tempsLivraison)
    {
        this.tempsLivraison=tempsLivraison;
    }
    
    /**
     * Permet de régler la latitude du drone 
     * @param latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Permet de régler la longitude du drone
     * @param longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
}
