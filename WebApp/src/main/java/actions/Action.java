package actions;

import exception.*;
import metier.modele.Client;
import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public abstract class Action {

    public static final String SESSION_CLIENT_FIELD = "client";
    public static final String RESULTS_FIELD = "results";

    protected ServiceMetier serviceMetier;

    public Action(ServiceMetier serviceMetier) {
        this.serviceMetier = serviceMetier;
    }

    public abstract void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, NotLoggedException, SignUpException, NullAvailableProductException, ClientNullException, ConnectionFailException, IncompatibleTypeException;


    protected boolean isClient(HttpServletRequest req, HttpServletResponse res)
            throws ServletException {

        HttpSession session = req.getSession(true);

        Client user = (Client) session.getAttribute(SESSION_CLIENT_FIELD);

        return user != null;
    }
    protected String attributeString(String attributeName, HttpServletRequest req) throws IncompatibleTypeException {
        Object o = req.getParameter(attributeName);
        if(o instanceof String){
            return (String)o;
        }
        throw new IncompatibleTypeException(attributeName+" is not a String");

    }

}
