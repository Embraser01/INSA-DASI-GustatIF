/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Produit;
import metier.modele.ProduitCommande;
import util.JpaUtil;

/**
 *
 * @author thomas
 */
public class ProduitCommandeDAO {

    public void create(ProduitCommande pc) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(pc);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public ProduitCommande findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        ProduitCommande pc = null;
        try {
            pc = em.find(ProduitCommande.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return pc;
    }
    
    
    @SuppressWarnings("unchecked")
    public List<Produit> findProductsOfOrder(Long orderId) throws Exception{
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Produit> produits = new ArrayList<>();
        ProduitDAO pdao = new ProduitDAO();
        
        try {
            //on travaille sur le modèle objet donc dans le where il faut 
            //mettre les attributs de nos entités et pas les nom de colonne
            //des relations
            Query q = em.createQuery("SELECT pc "
                    + "FROM ProduitCommande pc "
                    + "WHERE pc.commande.id = :cde_id");
            q.setParameter("cde_id", orderId);
            //on récupère la liste des ProduitCommande dont l'id de commande
            //est passé en paramètre
            List<ProduitCommande> lpc = (List<ProduitCommande>) q.getResultList();
            
            //On récupère tous le produits qui font partis de la commande
            Produit p = new Produit();
            for(int i=0; i<lpc.size(); i++)
            {
                p=pdao.findById(lpc.get(i).getProduit().getId());
                produits.add(p);
            }
        }
        catch(Exception e) {
            throw e;
        }
        return produits;
    }
    
}
