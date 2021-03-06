package actions;

import exception.*;
import metier.modele.Client;
import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;

public abstract class Action {

    public static final String SESSION_CLIENT_FIELD = "client";
    public static final String RESULTS_FIELD = "results";

    protected ServiceMetier serviceMetier;

    public Action(ServiceMetier serviceMetier) {
        this.serviceMetier = serviceMetier;
    }

    public abstract void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, NotLoggedException, SignUpException, NullAvailableProductException, ClientNullException, ConnectionFailException, IncompatibleTypeException, MissingInformationException, InfoClientUpdateException, ValiderCommandeException, ParseException;


    protected boolean isClient(HttpServletRequest req, HttpServletResponse res)
            throws ServletException {

        HttpSession session = req.getSession(true);

        Client user = (Client) session.getAttribute(SESSION_CLIENT_FIELD);

        return user != null;
    }

}
