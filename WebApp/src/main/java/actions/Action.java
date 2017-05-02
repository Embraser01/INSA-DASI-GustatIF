package actions;

import exception.NotLoggedException;
import exception.SignUpException;
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
            throws ServletException, IOException, NotLoggedException, SignUpException;


    protected boolean isClient(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(true);

        Client user = (Client) session.getAttribute(SESSION_CLIENT_FIELD);

        return user != null;
    }
}
