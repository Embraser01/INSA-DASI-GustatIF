package actions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exception.MissingInformationException;
import exception.NotLoggedException;
import exception.ValiderCommandeException;
import metier.modele.Client;
import metier.modele.Commande;
import metier.modele.Produit;
import metier.modele.Restaurant;
import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class ValiderCommandeAction extends Action {

    public ValiderCommandeAction(ServiceMetier serviceMetier) {
        super(serviceMetier);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, NotLoggedException, MissingInformationException, ValiderCommandeException {

        if (!isClient(req, res)) throw new NotLoggedException();


        try {
            JsonObject obj = new JsonParser().parse(req.getParameter("commande")).getAsJsonObject();

            Commande commande = new Commande((Client) req.getSession().getAttribute(SESSION_CLIENT_FIELD));
            Restaurant restaurant = new Restaurant();

            // Setting Restaurant
            restaurant.setId(obj.get("restaurant").getAsLong());
            commande.setRestaurant(restaurant);


            // Setting each product

            List<Produit> produitsDisponibles = this.serviceMetier.produitsDisponibles(restaurant);

            JsonArray produits = obj.getAsJsonArray("produits");
            Produit curr = null;
            int qte = 0;
            long id = 0;

            for (JsonElement p :
                    produits) {

                id = p.getAsJsonObject().get("id").getAsLong();
                qte = p.getAsJsonObject().get("qte").getAsInt();

                for (Produit pr : produitsDisponibles) {
                    if (id == pr.getId()) {
                        curr = pr;
                        break;
                    }
                }
                for (int i = 0; i < qte; i++) {
                    commande.ajouterProduit(curr);
                }
            }

            if (!this.serviceMetier.validerCommande(commande)) {
                throw new ValiderCommandeException();
            }
        } catch (ValiderCommandeException e) {
            throw e;
        } catch (Exception e) {
            throw new MissingInformationException();
        }
    }
}
