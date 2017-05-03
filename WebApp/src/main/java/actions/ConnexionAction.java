package actions;

import com.google.gson.Gson;
import exception.ConnectionFailException;
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
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, ConnectionFailException {
        HttpSession session = req.getSession(true);

        String mail = req.getParameter("mail");

        String ids = req.getParameter("password");
        Long  id = Long.valueOf(ids);

        Client user = this.serviceMetier.connexion(mail,id);
        if (user == null) {
            throw new ConnectionFailException();
        }
        req.setAttribute(RESULTS_FIELD,user);
    }
}
