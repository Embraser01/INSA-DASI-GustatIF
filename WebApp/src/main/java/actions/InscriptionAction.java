package actions;

import com.google.gson.Gson;
import exception.SignUpException;
import metier.modele.Client;
import metier.modele.Produit;
import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


public class InscriptionAction extends Action {

    public InscriptionAction(ServiceMetier serviceMetier) {
        super(serviceMetier);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, SignUpException {

        String name = (String) req.getAttribute("name");

        String surname = (String) req.getAttribute("surname");
        String email = (String) req.getAttribute("email");
        String address = (String) req.getAttribute("address");


        Client user = new Client(name, surname, email, address);
        if (!serviceMetier.inscription(user)) throw new SignUpException();

        HttpSession session = req.getSession(true);
        session.setAttribute(SESSION_CLIENT_FIELD, user);

        //TODO redirect user ?
        req.setAttribute(RESULTS_FIELD,user);

    }
}
