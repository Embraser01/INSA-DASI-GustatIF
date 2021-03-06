package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Produit;
import util.JpaUtil;

public class ProduitDAO {
    
    public void create(Produit produit) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(produit);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public Produit findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Produit produit = null;
        try {
            produit = em.find(Produit.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return produit;
    }
    
    public List<Produit> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Produit> produits = null;
        try {
            Query q = em.createQuery("SELECT p FROM Produit p");
            produits = (List<Produit>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return produits;
    }
}
