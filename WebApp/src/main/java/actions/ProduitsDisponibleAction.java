package actions;


import exception.IncompatibleTypeException;
import exception.MissingInformationException;
import exception.NullAvailableProductException;
import metier.modele.Produit;
import metier.modele.Restaurant;
import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class ProduitsDisponibleAction extends Action {

    public ProduitsDisponibleAction(ServiceMetier serviceMetier) {
        super(serviceMetier);
    }

    @Override

    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, NullAvailableProductException, MissingInformationException, IncompatibleTypeException {

        String ids = req.getParameter("id");

        if (ids == null) throw new MissingInformationException();

        try {
            Long id = Long.valueOf(ids);

            Restaurant restaurant = new Restaurant();
            restaurant.setId(id);
            List<Produit> produits = serviceMetier.produitsDisponibles(restaurant);
            if (produits == null) {
                throw new NullAvailableProductException();
            }

            req.setAttribute(RESULTS_FIELD, produits);

        } catch (Exception e) {
            throw new IncompatibleTypeException();
        }
    }
}
