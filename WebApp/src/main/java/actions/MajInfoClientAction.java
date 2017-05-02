package actions;

import exception.NotLoggedException;
import metier.modele.Client;
import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MajInfoClientAction extends Action {

    public MajInfoClientAction(ServiceMetier serviceMetier) {
        super(serviceMetier);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException, NotLoggedException {

        if (!isClient(req, res)) throw new NotLoggedException();

        Client client = (Client) req.getSession().getAttribute(SESSION_CLIENT_FIELD);

        // FIXME Escape HTML

        client.setNom(req.getParameter("lastName"));
        client.setPrenom(req.getParameter("firstName"));
        client.setMail(req.getParameter("mail"));

        this.serviceMetier.majInfoClient(client);
    }
}
