import actions.*;
import exception.NotLoggedException;
import exception.SignUpException;
import metier.service.ServiceMetier;
import util.JpaUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * "C:\Program Files\Java\jdk1.8.0_77\db\bin\startNetworkServer" -noSecurityManager
 */
public class ActionServlet extends HttpServlet {

    private ServiceMetier serviceMetier;

    public ActionServlet() {
        this.serviceMetier = new ServiceMetier();
    }


    @Override
    public void destroy() {
        JpaUtil.destroy();
    }

    @Override
    public void init() throws ServletException {
        JpaUtil.init();
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");

        String todo = req.getParameter("action");

        if (todo == null) {
            res.sendError(404);
            return;
        }

        Action action;

        switch (todo) {
            case "inscription":
                action = new InscriptionAction(serviceMetier);
                break;
            case "connexion":
                action = new ConnexionAction(serviceMetier);
                break;
            case "majInfoClient":
                action = new MajInfoClientAction(serviceMetier);
                break;
            case "restaurantsPartenaires":
                action = new RestaurantsPartenairesAction(serviceMetier);
                break;
            case "produitsDisponible":
                action = new ProduitsDisponibleAction(serviceMetier);
                break;
            case "clientsGustatif":
                action = new ClientsGustatifAction(serviceMetier);
                break;
            case "livreursPartenaires":
                action = new LivreursPartenairesAction(serviceMetier);
                break;
            case "validerCommande":
                action = new ValiderCommandeAction(serviceMetier);
                break;
            case "cloturerLivraison":
                action = new CloturerLivraisonAction(serviceMetier);
                break;
            case "rechercherLivraison":
                action = new RechercherLivraisonAction(serviceMetier);
                break;
            default:
                res.sendError(404);
                return;
        }

        try {
            action.execute(req, res);
        } catch (NotLoggedException | SignUpException e) {
            // TODO SEND RESPONSE
        } catch (SignUpException e) {
            //TODO SEND RESPONSE
        }
    }


    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse);
    }
}
