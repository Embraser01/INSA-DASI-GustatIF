package actions;

import exception.NotLoggedException;
import metier.modele.Client;
import metier.modele.Commande;
import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CloturerLivraisonAction extends Action {

    public CloturerLivraisonAction(ServiceMetier serviceMetier) {
        super(serviceMetier);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, NotLoggedException {
        if (!isClient(req, res)) throw new NotLoggedException();
        Commande commande = new Commande((Client) req.getSession().getAttribute(SESSION_CLIENT_FIELD));
        this.serviceMetier.cloturerLivraison(commande);
    }
}
