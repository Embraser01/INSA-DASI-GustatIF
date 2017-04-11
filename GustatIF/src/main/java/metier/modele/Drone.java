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
public class Drone extends Livreur{
    
    //les vitesses sont en m/s
    private Double vitesse;
    
    //private Double latitude;
    //private Double longitude;
    
    /**
     *
     */
    public Drone (){
    }
    
    /**
     * Constructeur de la classe Drone
     * @param etat 1 si le drone est dispo, 0 sinon
     * @param poidsMax le poids Max que le drone peut porter 
     * @param adresse l'adresse initiale du drone
     * @param vitesse la vitesse moyenne de vol du drone
     */
    public Drone(boolean etat, Double poidsMax, String adresse, Double vitesse)
    {
        super(etat, poidsMax,adresse);
        this.vitesse=vitesse;
        this.latitude=null;
        this.longitude=null;
    }

    /**
     * Permet d'obtenir la vitesse de vol du drone 
     * @return
     */
    public Double getVitesse() {
        return vitesse;
    }


    @Override
    public int compareTo(Livreur o) {
        long compareTemps = ((Livreur)o).getTempsLivraison();
        
        // ordre croissant
        return (int)this.getTempsLivraison()-(int)compareTemps;
    }
    

}
