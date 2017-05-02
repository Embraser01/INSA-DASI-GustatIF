package actions;

import com.google.gson.Gson;
import metier.modele.Produit;
import metier.modele.Restaurant;
import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class ProduitsDisponibleAction extends Action {

    public ProduitsDisponibleAction(ServiceMetier serviceMetier) {
        super(serviceMetier);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        Restaurant restaurant = (Restaurant) req.getAttribute("restaurant");

        List<Produit> produits = this.serviceMetier.produitsDisponibles(restaurant);

        if (produits == null) {
            res.sendError(400,"No products were found. Check requested restaurant");
        }

        req.setAttribute(RESULTS_FIELD,produits);
    }
}
