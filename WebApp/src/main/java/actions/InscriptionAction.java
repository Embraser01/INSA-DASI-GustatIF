package actions;

import exception.IncompatibleTypeException;
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
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, SignUpException, IncompatibleTypeException {

        String name = parameterString("name",req);
        String surname = parameterString("surname",req);
        String email = parameterString("email",req);
        String address = parameterString("address",req);


        Client user = new Client(name, surname, email, address);
        if (!serviceMetier.inscription(user)) throw new SignUpException();

        //TODO redirect user ?
        req.setAttribute(RESULTS_FIELD,user);

    }
}
