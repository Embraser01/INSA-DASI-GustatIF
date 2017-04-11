package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Produit;
import metier.modele.Restaurant;
import util.JpaUtil;

public class RestaurantDAO {
    
    public void create(Restaurant restaurant) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(restaurant);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public Restaurant findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Restaurant restaurant = null;
        try {
            restaurant = em.find(Restaurant.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return restaurant;
    }
    
    public List<Restaurant> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Restaurant> restaurants = null;
        try {
            Query q = em.createQuery("SELECT r FROM Restaurant r");
            restaurants = (List<Restaurant>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return restaurants;
    }
    
    public List<Produit> trouverProduitRestaurant(long idRestau) throws Exception
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Produit> produits = null;
        
        try {
            Query q = em.createQuery("SELECT p "
                    + "FROM Restaurant r LEFT JOIN r.produits p"
                    + " WHERE r.id=:idRestau");
            q.setParameter("idRestau", idRestau);
                
            produits = (List<Produit>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return produits;
    }
    
    public Restaurant trouverRestaurantDuProduit(Produit p )throws Exception
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Restaurant> restaurants = null;
        Restaurant restaurant = null;
        
        try {    
            Query q = em.createQuery("SELECT r "
                + "FROM Produit p JOIN  Restaurant r"
                + " WHERE  p.id =:idPdt");
            q.setParameter("idPdt", p.getId());

            restaurants = (List<Restaurant>) q.getResultList();
            restaurant=  restaurants.get(0);
        }
        catch(Exception e) {
            throw e;
        }
        return restaurant;
        
    }
    
}
