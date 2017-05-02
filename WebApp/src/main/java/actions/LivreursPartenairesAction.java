package actions;

import metier.modele.Livreur;
import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class LivreursPartenairesAction extends Action {

    public LivreursPartenairesAction(ServiceMetier serviceMetier) {
        super(serviceMetier);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException {

        List<Livreur> livreurs = this.serviceMetier.livreursPartenaires();

        req.setAttribute(RESULTS_FIELD, livreurs);
    }
}
