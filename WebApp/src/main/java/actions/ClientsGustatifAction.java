package actions;

import exception.ClientNullException;
import metier.modele.Client;
import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class ClientsGustatifAction extends Action {

    public ClientsGustatifAction(ServiceMetier serviceMetier) {
        super(serviceMetier);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, ClientNullException {
        List<Client> clients = this.serviceMetier.clientsGustatif();

        if (clients == null) {
            throw new ClientNullException();
        }
        req.setAttribute(RESULTS_FIELD, clients);

    }
}
