import actions.Action;
import com.google.gson.Gson;
import metier.modele.Produit;
import metier.modele.Restaurant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
        List<Restaurant> restaurants = (List)req.getAttribute(Action.RESULTS_FIELD);
        res.getWriter().print(new Gson().toJson(restaurants));
    }

    public static void produitsDisponible(HttpServletRequest req, HttpServletResponse res) throws IOException {
        List<Produit> produits = (List)req.getAttribute(Action.RESULTS_FIELD);
        res.getWriter().print(new Gson().toJson(produits));
    }

    public static void clientsGustatif(HttpServletRequest req, HttpServletResponse res) {

    }

    public static void livreursPartenaires(HttpServletRequest req, HttpServletResponse res) {

    }

    public static void validerCommande(HttpServletRequest req, HttpServletResponse res) {

    }

    public static void cloturerLivraison(HttpServletRequest req, HttpServletResponse res) {

    }

    public static void rechercherLivraison(HttpServletRequest req, HttpServletResponse res) {

    }
}
