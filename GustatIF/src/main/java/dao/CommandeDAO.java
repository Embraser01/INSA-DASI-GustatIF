/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Commande;
import metier.modele.ProduitCommande;
import util.JpaUtil;

/**
 *
 * @author thomas
 */
public class CommandeDAO {

    public void create(Commande commande) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        //trouver livreur et attribuer le livreur
        try {
            em.persist(commande);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public void merge(Commande commande) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.merge(commande);
        }
        catch(Exception e) {
            throw e;
        }        
    }
    
    public void remove (Commande commande) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        //trouver livreur et attribuer le livreur
        try {
            em.remove(commande);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public Commande findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Commande commande = null;
        try {
            commande = em.find(Commande.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return commande;
    }
    
    public List<Commande> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Commande> commandes = null;
        try {
            Query q = em.createQuery("SELECT c FROM Commande c");
            commandes = (List<Commande>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return commandes;
    } 
    
    /*public List<Commande> findByCustomerFamilyName(String familyName)
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Commande> commandes = null;
        
        Query q = em.createQuery("SELECT c FROM Commande c "
                + "WHERE c.client.nom = :famName");
        q.setParameter("famName", familyName);
        
        commandes = (List<Commande>)q.getResultList();
        
        return commandes;
    }*/
    
    public List<ProduitCommande> trouverProduitCommande(Commande c) throws Exception
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<ProduitCommande> lpc = null;
        
        try {
            Query q = em.createQuery("SELECT pc "
                    + "FROM Commande c LEFT JOIN c.produitsCommandes pc"
                    + " WHERE c.id=:idCde");
            q.setParameter("idCde", c.getId());
                
            lpc = (List<ProduitCommande>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }    
        
        return lpc;
    }
}
