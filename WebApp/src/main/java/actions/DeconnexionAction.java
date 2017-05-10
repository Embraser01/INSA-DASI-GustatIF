package actions;

import exception.*;
import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class DeconnexionAction extends Action {

    public DeconnexionAction(ServiceMetier serviceMetier) {
        super(serviceMetier);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, NotLoggedException, SignUpException, NullAvailableProductException, ClientNullException, ConnectionFailException {
        HttpSession session = req.getSession(true);

        session.setAttribute(SESSION_CLIENT_FIELD, null);
    }
}
