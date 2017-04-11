package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author thomas
 */
@Entity
public class Restaurant implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String denomination;
    private String description;
    private String adresse;
    private Double longitude;
    private Double latitude;

    @OneToMany
    private List<Produit> produits;
    
    /**
     * Constructeur par défaut de la classe Restaurant 
     */
    protected Restaurant() {
        this.produits = new ArrayList<>();
    }

    /**
     * Constructeur de la classe restaurant 
     * @param denomination le nom du restaurant 
     * @param description la description du restaurant 
     * @param adresse l'adresse du restaurant 
     */
    public Restaurant(String denomination, String description, String adresse) {
        this.denomination = denomination;
        this.description = description;
        this.adresse = adresse;
        this.longitude = null;
        this.latitude = null;
        this.produits = new ArrayList<>();
    }

    /**
     * Permet d'obtenir l'id du restaurant 
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * Permetd'obtenir le nom du restaurant 
     * @return
     */
    public String getDenomination() {
        return denomination;
    }

    /**
     * Permet d'obtenir la description du restaurant 
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Permet d'obtenir l'adresse du restaurant 
     * @return
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Permet d'obtenir la longitude du restaurant 
     * @return
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Permet d'obtenir la latitude du restaurant 
     * @return
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Permet d'obtenir une liste des produits d'un restaurant 
     * @return
     */
    public List<Produit> getProduits() {
        return produits;
    }

    /**
     * Permet de changer le nom d'un restaurant 
     * @param denomination
     */
    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    /**
     * Permet de changer la description d'un restaurant 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Permet de changer l'adresse d'un restaurant 
     * @param adresse
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Permet de changer la latitude et la longitude d'un restaurant 
     * @param latitude
     * @param longitude
     */
    public void setLatitudeLongitude(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Permet d'ajouter un produit au restaurant 
     * @param produit
     */
    public void addProduit(Produit produit) {
        this.produits.add(produit);
    }

    @Override
    public String toString() {
        return "Restaurant{" + "id=" + id + ", denomination=" + denomination + ", description=" + description + ", adresse=" + adresse + ", longitude=" + longitude + ", latitude=" + latitude + ", produits=" + produits + '}';
    }

}
