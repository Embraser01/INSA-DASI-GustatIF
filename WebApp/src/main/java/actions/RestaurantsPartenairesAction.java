package actions;

import com.google.gson.Gson;
import metier.modele.Restaurant;
import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RestaurantsPartenairesAction extends Action {

    public RestaurantsPartenairesAction(ServiceMetier serviceMetier) {
        super(serviceMetier);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        List<Restaurant> restaurants = this.serviceMetier.restaurantsPartenaires();


        req.setAttribute(RESULTS_FIELD,restaurants);
    }
}
