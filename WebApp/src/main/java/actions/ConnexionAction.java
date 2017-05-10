package actions;

import exception.ConnectionFailException;
import exception.IncompatibleTypeException;
import exception.MissingInformationException;
import metier.modele.Client;
import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ConnexionAction extends Action {

    public ConnexionAction(ServiceMetier serviceMetier) {
        super(serviceMetier);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, ConnectionFailException, IncompatibleTypeException, MissingInformationException {
        HttpSession session = req.getSession(true);

        String mail = req.getParameter("email");
        String ids = req.getParameter("password");

        if (mail == null || ids == null) throw new MissingInformationException();

        try {
            Long id = Long.valueOf(ids);

            Client user = this.serviceMetier.connexion(mail, id);
            if (user == null) {
                throw new ConnectionFailException();
            }
            session.setAttribute(SESSION_CLIENT_FIELD, user);
            req.setAttribute(RESULTS_FIELD, user);
        } catch (Exception e) {
            throw new IncompatibleTypeException();
        }


    }
}
