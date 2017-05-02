import actions.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JsonView {


    public static void inscription(HttpServletRequest req, HttpServletResponse res) {

        // To use results :

        req.getAttribute(Action.RESULTS_FIELD);
    }

    public static void connexion(HttpServletRequest req, HttpServletResponse res) {

    }

    public static void majInfoClient(HttpServletRequest req, HttpServletResponse res) {

    }

    public static void restaurantsPartenaires(HttpServletRequest req, HttpServletResponse res) {

    }

    public static void produitsDisponible(HttpServletRequest req, HttpServletResponse res) {

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
