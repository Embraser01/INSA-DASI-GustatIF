package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Livreur;
import util.JpaUtil;

public class LivreurDAO {
    
    public void create(Livreur livreur) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(livreur);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public void merge(Livreur livreur) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.merge(livreur);
        }
        catch(Exception e) {
            throw e;
        }        
    }
    
    public Livreur findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Livreur livreur = null;
        try {
            livreur = em.find(Livreur.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return livreur;
    }
    
    public List<Livreur> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Livreur> livreurs = null;
        try {
            Query q = em.createQuery("SELECT l FROM Livreur l");
            livreurs = (List<Livreur>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return livreurs;
    }
    
    public List<Livreur> trouverLivreurDispoParPoids(double poids)
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Livreur> livreurs = null;
        
        Query q = em.createQuery("SELECT l FROM Livreur l "
                + "WHERE l.poidsMax >= :poids "
                + "AND l.etat=1");
        q.setParameter("poids", poids);
        
        livreurs = (List<Livreur>) q.getResultList();
        
        return livreurs;
    }
}
