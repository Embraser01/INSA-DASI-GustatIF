import actions.Action;
import com.google.gson.Gson;
import metier.modele.Client;
import metier.modele.Livreur;
import metier.modele.Produit;
import metier.modele.Restaurant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("unchecked")
public class JsonView {


    public static void inscription(HttpServletRequest req, HttpServletResponse res) {

        // To use results :

        req.getAttribute(Action.RESULTS_FIELD);
    }

    public static void connexion(HttpServletRequest req, HttpServletResponse res) {

    }

    public static void majInfoClient(HttpServletRequest req, HttpServletResponse res) {

    }

    public static void restaurantsPartenaires(HttpServletRequest req, HttpServletResponse res) throws IOException {
        List<Restaurant> restaurants = (List<Restaurant>) req.getAttribute(Action.RESULTS_FIELD);

        res.getWriter().print(new Gson().toJson(restaurants));
    }

    public static void produitsDisponible(HttpServletRequest req, HttpServletResponse res) throws IOException {
        List<Produit> produits = (List<Produit>) req.getAttribute(Action.RESULTS_FIELD);

        res.getWriter().print(new Gson().toJson(produits));
    }

    public static void clientsGustatif(HttpServletRequest req, HttpServletResponse res) throws IOException {
        List<Client> clients = (List)req.getAttribute(Action.RESULTS_FIELD);
        res.getWriter().print(new Gson().toJson(clients));
    }

    public static void livreursPartenaires(HttpServletRequest req, HttpServletResponse res) throws IOException {
        List<Livreur> livreurs = (List<Livreur>) req.getAttribute(Action.RESULTS_FIELD);

        res.getWriter().print(new Gson().toJson(livreurs));
    }

    public static void validerCommande(HttpServletRequest req, HttpServletResponse res) {

    }

    public static void cloturerLivraison(HttpServletRequest req, HttpServletResponse res) {
        System.out.println("commande cloture");
    }

    public static void rechercherLivraison(HttpServletRequest req, HttpServletResponse res) {

    }

    public static void notFound(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.sendError(404);
    }

    public static void forbidden(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.sendError(403);
    }

    public static void notFound(HttpServletRequest req, HttpServletResponse res, String msg) throws IOException {
        res.sendError(404, msg);
    }

    public static void forbidden(HttpServletRequest req, HttpServletResponse res, String msg) throws IOException {
        res.sendError(403, msg);
    }

    public static void badRequest(HttpServletRequest req, HttpServletResponse res, String msg) throws IOException {
        res.sendError(400, msg);
    }
}
