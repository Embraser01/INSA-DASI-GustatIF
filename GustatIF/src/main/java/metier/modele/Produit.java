package metier.modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author thomas
 */
@Entity
public class Produit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String denomination;
    private String description;
    private Double poids;

    private Double prix;
    
    //@OneToMany(mappedBy="produit")
    
    /*@OneToMany
    private List<ProduitCommande> produitsCommandes
            = new ArrayList<ProduitCommande>();*/

    /**
     * Constructeur par défault 
     */
    public Produit() {
    }

    /**
     * Constructeiur de la classe produit 
     * @param denomination le nom du produit 
     * @param description la description du produit 
     * @param poids le poids du produit 
     * @param prix le prix du produit 
     */
    public Produit(String denomination, String description, Double poids, Double prix) {
        this.denomination = denomination;
        this.description = description;
        this.poids = poids;
        this.prix = prix;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produit other = (Produit) obj;
        if (!Objects.equals(this.denomination, other.denomination)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.denomination);
        return hash;
    }
    
    /**
     *Permet d'obtenir l'id du produit 
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * Permet d'obtenir le nom du produit 
     * @return
     */
    public String getDenomination() {
        return denomination;
    }

    /**
     *Permet d'obtenir la description du produit 
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Permet d'btenir le poids du produit 
     * @return
     */
    public Double getPoids() {
        return poids;
    }

    /**
     * Permet d'obtenir le prix du produit 
     * @return
     */
    public Double getPrix() {
        return prix;
    }

    /**
     * Permet de changer le nom d'un produit 
     * @param denomination
     */
    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    /**
     * Permet de changer la description d'un produit 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Permet de changer le poids d'un produit 
     * @param poids
     */
    public void setPoids(Double poids) {
        this.poids = poids;
    }

    /**
     * Permet de changer le prix d'un produit 
     * @param prix
     */
    public void setPrix(Double prix) {
        this.prix = prix;
    }
    

    /*public List<ProduitCommande> getProduitsCommandes() {
        return produitsCommandes;
    }*/


    /*public void setProduitsCommandes(List<ProduitCommande> produitsCommandes) {
        this.produitsCommandes = produitsCommandes;
    }*/

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", denomination=" + denomination + ", description=" + description + ", poids=" + poids + ", prix=" + prix + '}';
    }

    public void setId(long id) {
        this.id = id;
    }
}
