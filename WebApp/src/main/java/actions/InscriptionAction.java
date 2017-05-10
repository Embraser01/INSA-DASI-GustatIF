package actions;

import exception.IncompatibleTypeException;
import exception.MissingInformationException;
import exception.SignUpException;
import metier.modele.Client;
import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class InscriptionAction extends Action {

    public InscriptionAction(ServiceMetier serviceMetier) {
        super(serviceMetier);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, SignUpException, IncompatibleTypeException, MissingInformationException {

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String address = req.getParameter("address");


        if (name == null || surname == null || email == null || address == null)
            throw new MissingInformationException();

        Client user = new Client(name, surname, email, address);
        if (!serviceMetier.inscription(user)) throw new SignUpException();

        req.setAttribute(RESULTS_FIELD, user);
    }
}
