/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Client;
import metier.modele.Commande;
import metier.modele.Drone;
import metier.modele.Humain;
import metier.modele.ProduitCommande;

/**
 *
 * @author thomas
 */
public class ServiceTechnique {
    
    final static GeoApiContext GOOGLE_MAP 
            = new GeoApiContext().setApiKey("AIzaSyAziHj8iH4_UZ1tL53eFOgvlc_aGskKHzU");
    
    /**
     * Envoi d'un mail lors de l'inscription d'un client pour le notifier de la 
     * réussite ou de l'échec de son inscription
     * 
     * @param client le client à qui envoyer le mail 
     * @param succesInscription true si l'inscription est un succes, false sinon
     */
    protected static void mailInscription(Client client, boolean succesInscription)
    {
        if(succesInscription)
        {
            System.out.println("Bonjour, " + client.getPrenom() + " " + client.getNom());
            System.out.println("Votre inscription est un succès");
            System.out.println("Votre identifiant est : " + client.getMail());
            System.out.println("Votre mot de passe est : " + client.getId());
        }else 
        {
            System.out.println("Votre inscription a échoué. Veuillez réessayer"
                    + " plus tard.");
        }    
    }
    
    /**
     * Envoi un mail au livreur pour le notifier de la commande qu'il doit 
     * effectuer 
     * 
     * @param c la commande à effectuer 
     */
    protected static void mailInformationLivreur(Commande c)
    {
        if(c.getLivreur() instanceof Humain)
        {
            System.out.println("Bonjour, " + ((Humain) c.getLivreur()).getPrenom());
        }
        
        System.out.println("Livraison numéro " + c.getId() + " à réaliser.");
        
        System.out.println("Détail de la livraison : ");
        System.out.println("\t-Date/Heure : " 
                + c.getDateEnregistrementCommande().toString());
        
        if(c.getLivreur() instanceof Humain)
        {
            System.out.println("\t-Livreur : " + ((Humain) c.getLivreur()).getPrenom() + 
                    " " + ((Humain) c.getLivreur()).getNom()
            + " (id : " + ((Humain) c.getLivreur()).getId()+ ")");
        }
        else
        {
            System.out.println("\t-Livreur : Drone " + ((Drone) c.getLivreur()).getId());
        }

        System.out.println("\t-Restaurant : " + c.getRestaurant().getDenomination());
        System.out.println("\t-Client : ");
        System.out.println("\t\t"+c.getClient().getPrenom() + " " + c.getClient().getNom());
        System.out.println("\t\t" + c.getClient().getAdresse());

        System.out.println("\t-Commande : ");
        List<ProduitCommande> lpc = c.getProduitsCommandes();
        for(int i =0; i<lpc.size();i++)
        {
            System.out.println("\t\t* " + lpc.get(i).getQuantite()
            + " " +lpc.get(i).getProduit().getDenomination() +" : " 
            + lpc.get(i).getQuantite() + " x " 
            + lpc.get(i).getProduit().getPrix() + "€");
        }

        System.out.println("\tTOTAL : " + c.calculerPrixCommande() +" €");

          
    }
    
    /**
     * Permet d'obtenir un objet de type LatLng (composé d'une latitude et d'une 
     * longitude) à partir d'une adresse.
     * 
     * @param adresse l'adresse dont on veut obtenir la latitude et longitude
     * @return latitude et longitude de l'adresse donnée en paramètre 
     */
    protected static LatLng getLatLngFromAdress(String adresse) {
        try {
            GeocodingResult[] results =
                GeocodingApi.geocode(GOOGLE_MAP,adresse).await();
            return results[0].geometry.location;
        } catch (Exception ex) {
            return null;
        }
    }
    
    /**
     * Permet de calculer la durée d'un trajet effectué par un livreur Humain en
     * considérant un moyen de transport en en respectant le code de la route.
     * @param depart un tableau qui contient les adresses de départ 
     * @param arrivee un tableau qui contient les adresses d'arrivée 
     * @param mode le mode de transport 
     * @return la durée en secondes pour effectuer le trajet
     */
    protected static long calculDureeHumain(String [] depart, String [] arrivee, TravelMode mode)
    {
        long res=-1; 
        
        try { 
            DistanceMatrixApiRequest itineraire = new DistanceMatrixApiRequest(GOOGLE_MAP);
            DistanceMatrix results =  DistanceMatrixApi.getDistanceMatrix(GOOGLE_MAP,
                    depart,arrivee).units(Unit.METRIC).mode(mode)
                    .await();
            
            res = results.rows[0].elements[0].duration.inSeconds
                    + results.rows[1].elements[1].duration.inSeconds;
        } catch (Exception ex) {
            System.out.println("calculDureeLivraison - aucun duree trouvee "
                    + "pour cette requête");
            Logger.getLogger(ServiceTechnique.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }
    
    /**
     * Permet de calculer la distance à vol d'oiseau entre 2 adresses .
     * 
     * @param d  le drone qui effectue le trajet 
     * @param latDepart latitude de départ 
     * @param lngDepart longitude de départ  
     * @param latArrivee latitude  d'arrivée 
     * @param lngArrivee longitude d'arrivée 
     * 
     * @return la duree pour effectuer le trajet 
     */
    //protected static long calculDureeDrone(Drone d, String depart, String arrivee)
    protected static long calculDureeDrone(Drone d, double latDepart, double lngDepart,
                                            double latArrivee, double lngArrivee)
    {
        long distance = 0 ; 
        distance  = (long)haversine(latDepart, lngDepart, latArrivee, lngArrivee);
        long temps = (long) (distance/(d.getVitesse()));
        return temps;
    }
    
    /**
     * Méthode qui permet de calculer la distance "à vol d'oiseaux" entre deux
     * adresses.
     * Elle implémente la formule Haversine, utilisée notamment en navigation.
     * 
     * Implémentation trouvée sur le site : 
     * https://rosettacode.org/wiki/Haversine_formula#Java
     * 
     * @param lat1 la latitude du point de départ 
     * @param lng1 la longitude du point de départ
     * @param lat2 la latitude du point d'arrivée
     * @param lng2 la longitude du point d'arrivée
     * @return la distance en m entre les 2 points
     */
    public static double haversine(double lat1, double lng1,
                                      double lat2, double lng2) {
        double R = 6372800; // rayon de la Terre en m
        
        //LatLng point1  = getLatLngFromAdress(adr1);
        //LatLng point2  = getLatLngFromAdress(adr2);
        
        //System.out.println(lat1 + " | " + lng1);
        //System.out.println(lat2 + " | " + lng2);
        
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);
        double lat1Radian = Math.toRadians(lat1);
        double lat2Radian = Math.toRadians(lat2);
 
        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1Radian) * Math.cos(lat2Radian);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }
   
}
