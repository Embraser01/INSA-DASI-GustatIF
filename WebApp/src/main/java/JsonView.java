import actions.Action;
import com.google.gson.Gson;
import metier.modele.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("unchecked")
public class JsonView {


    public static void inscription(HttpServletRequest req, HttpServletResponse res) throws IOException {

        // To use results :
        // TODO Set header for json
        Client user = (Client)req.getAttribute(Action.RESULTS_FIELD);
        res.getWriter().print(new Gson().toJson(user));
    }

    public static void connexion(HttpServletRequest req, HttpServletResponse res) throws IOException{
        Client user = (Client) req.getAttribute(Action.RESULTS_FIELD);
        res.getWriter().print(new Gson().toJson(user));
    }

    public static void majInfoClient(HttpServletRequest req, HttpServletResponse res) throws IOException{
        Client user = (Client)req.getAttribute(Action.RESULTS_FIELD);
        res.getWriter().print(new Gson().toJson(user));
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

    public static void rechercherLivraison(HttpServletRequest req, HttpServletResponse res) throws IOException{
        List<Commande> commandes = (List<Commande>)req.getAttribute(Action.RESULTS_FIELD);
        res.getWriter().print(new Gson().toJson(commandes));
    }

    public static void notFound(HttpServletRequest req, HttpServletResponse res, String msg) throws IOException {
        res.setStatus(404);
        res.getWriter().print("{\"msg\": \"Not Found : "+ msg + "\"}");
    }

    public static void forbidden(HttpServletRequest req, HttpServletResponse res, String msg) throws IOException {
        res.setStatus(403);
        res.getWriter().print("{\"msg\": \"Forbidden : "+ msg + "\"}");
    }

    public static void badRequest(HttpServletRequest req, HttpServletResponse res, String msg) throws IOException {
        res.setStatus(400);
        res.getWriter().print("{\"msg\": \"Bad Request : "+ msg + "\"}");
    }

    public static void ok(HttpServletRequest req, HttpServletResponse res, String msg) throws IOException {
        res.setStatus(200);
        res.getWriter().print("{\"msg\": \"Ok : "+ msg + "\"}");
    }

    public static void serverError(HttpServletRequest req, HttpServletResponse res, String msg) throws IOException {
        res.setStatus(500);
        res.getWriter().print("{\"msg\": \"Server Error : "+ msg + "\"}");
    }
}
