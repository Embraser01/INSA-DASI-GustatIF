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

        String name = attributeString("name",req);
        String surname = attributeString("surname",req);
        String email = attributeString("email",req);
        String address = attributeString("address",req);


        Client user = new Client(name, surname, email, address);
        if (!serviceMetier.inscription(user)) throw new SignUpException();

        req.setAttribute(RESULTS_FIELD,user);
    }
}
