package actions;

import com.google.gson.Gson;
import exception.NotLoggedException;
import metier.modele.Client;
import metier.modele.Commande;
import metier.modele.Produit;
import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.NotActiveException;
import java.util.List;


public class ValiderCommandeAction extends Action {

    public ValiderCommandeAction(ServiceMetier serviceMetier) {
        super(serviceMetier);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException, NotLoggedException {

        if (!isClient(req, res)) throw new NotLoggedException();


        Commande commande = new Commande((Client) req.getSession().getAttribute(SESSION_CLIENT_FIELD));

        // TODO READ FROM JSON
    }
}
