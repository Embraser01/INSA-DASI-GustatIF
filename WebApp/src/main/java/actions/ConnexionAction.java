package actions;

import com.google.gson.Gson;
import metier.modele.Client;
import metier.modele.Produit;
import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


public class ConnexionAction extends Action {

    public ConnexionAction(ServiceMetier serviceMetier) {
        super(serviceMetier);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException {
        HttpSession session = req.getSession(true);
        Client user = (Client)req.getAttribute(SESSION_CLIENT_FIELD);
        String mail = req.getParameter("mail");
        Long id = user.getId();
        user = this.serviceMetier.connexion(mail,id);
        req.setAttribute(RESULTS_FIELD,user);
    }
}
