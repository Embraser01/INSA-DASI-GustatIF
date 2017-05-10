package actions;

import exception.InfoClientUpdateException;
import exception.MissingInformationException;
import exception.NotLoggedException;
import metier.modele.Client;
import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MajInfoClientAction extends Action {

    public MajInfoClientAction(ServiceMetier serviceMetier) {
        super(serviceMetier);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, NotLoggedException, MissingInformationException, InfoClientUpdateException {

        if (!isClient(req, res)) throw new NotLoggedException();

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String address = req.getParameter("address");


        if (name == null || surname == null || email == null || address == null)
            throw new MissingInformationException();

        Client user = new Client(name, surname, email, address);
        user = serviceMetier.majInfoClient(user);

        if (user == null) throw new InfoClientUpdateException();

        req.setAttribute(RESULTS_FIELD, user);
        req.getSession().setAttribute(SESSION_CLIENT_FIELD, user);
    }
}
