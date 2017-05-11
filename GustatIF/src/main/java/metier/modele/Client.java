package metier.modele;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thomas
 */
@Entity
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nom;
    private String prenom;
    
    @Column(unique=true)
    private String mail;
    
    private String adresse;
    private Double longitude;
    private Double latitude;
    
    @OneToMany(mappedBy="client")
    private List<Commande> listeCommandes;

    /**
     *
     */
    protected Client() {
        this.listeCommandes = new ArrayList<>();
    }
    
    /**
     * Contructeur de la classe Client 
     * @param nom le nom du client 
     * @param prenom le prénom du client 
     * @param mail le mail du client 
     * @param adresse l'adresse du client 
     */
    public Client(String nom, String prenom, String mail, String adresse) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.adresse = adresse;
        this.longitude = null;
        this.latitude = null;
        
        this.listeCommandes = new ArrayList<>();
    }
    
    /**
     * Permet d'obtenir l'id du client
     * @return l'id
     */
    public Long getId() {
        return id;
    }

    /**
     * Permet d'obtenir le nom du client
     * @return le nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Permet d'obtenir le prenom du client
     * @return le prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Permet d'obtenir le mail du client
     * @return le mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * Permet d'obtenir l'adresse du client
     * @return l'adresse du client
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Permet d'obtenir la latitude du client
     * @return
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Permet d'obtenir la longitude du client
     * @return
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Permet de changer le nom du client
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Permet de changer le prenom du client
     * @param prenom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Permet de changer le mail du client
     * @param mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Permet de changer l'adresse du client
     * @param adresse
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Permet de changer la latitude et longitud du client
     * @param latitude
     * @param longitude
     */
    public void setLatitudeLongitude(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Permet d'obtenir la liste des commandes du client 
     * @return une liste de commandes 
     */
    public List<Commande> getListeCommandes() {
        return listeCommandes;
    }


    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", adresse=" + adresse + ", longitude=" + longitude + ", latitude=" + latitude + '}';
    }

    public void setListeCommandes(List<Commande> listeCommandes) {
        this.listeCommandes = listeCommandes;
    }
}
