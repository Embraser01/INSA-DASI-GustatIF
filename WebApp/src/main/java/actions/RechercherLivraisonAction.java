package actions;

import metier.modele.Commande;
import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


public class RechercherLivraisonAction extends Action {

    public RechercherLivraisonAction(ServiceMetier serviceMetier) {
        super(serviceMetier);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, ParseException {

        List<Commande> commandes = null;
        try {
            commandes = this.serviceMetier.rechercherLivraison(
                    req.getParameter("lastName"),
                    req.getParameter("firstName"),
                    Long.valueOf(req.getParameter("commandeId")),
                    new SimpleDateFormat("DD/MM/YYYY").parse(req.getParameter("date")),
                    Boolean.parseBoolean(req.getParameter("all"))
            );
        } catch (ParseException e) {
            // TODO SEND ERROR MSG (rethrow exception)
            throw e;
        }

        req.setAttribute(RESULTS_FIELD, commandes);
    }
}
