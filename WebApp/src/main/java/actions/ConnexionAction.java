package actions;

import metier.modele.Produit;
import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class ConnexionAction extends Action {

    public ConnexionAction(ServiceMetier serviceMetier) {
        super(serviceMetier);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException {

        List<Produit> produits = this.serviceMetier.produitsDisponibles(null);

    }
}
