import actions.*;
import exception.*;
import metier.service.ServiceMetier;
import util.JpaUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * "C:\Program Files\Java\jdk1.8.0_77\db\bin\startNetworkServer" -noSecurityManager
 * E:\jdk\db\bin\startNetworkServer
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
            JsonView.notFound(req, res, "");
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
            case "deconnexion":
                action = new DeconnexionAction(serviceMetier);
                break;
            default:
                JsonView.notFound(req, res, "Unknown action !");
                return;
        }

        boolean executed = false;

        try {
            action.execute(req, res);
            executed = true;
        } catch (NotLoggedException e) {
            JsonView.forbidden(req, res, "You must be logged to do this !");
        } catch (SignUpException e) {
            JsonView.badRequest(req, res, "SignUp failed");
        } catch (ClientNullException e) {
            JsonView.notFound(req, res, "No such client");
        } catch (ConnectionFailException e) {
            JsonView.badRequest(req, res, "Connection failed");
        } catch (NullAvailableProductException e) {
            JsonView.notFound(req, res, "No products were found. Check requested restaurant");
        } catch (IncompatibleTypeException e) {
            JsonView.badRequest(req, res, e.getMessage());
        } catch (MissingInformationException e) {
            JsonView.badRequest(req, res, "Request is missing some data");
        } catch (InfoClientUpdateException | ValiderCommandeException e) {
            JsonView.serverError(req, res, "Try again later");
        }

        if (!executed) return;


        // On fait la vue maintenant
        switch (todo) {
            case "inscription":
                JsonView.inscription(req, res);
                break;
            case "connexion":
                JsonView.connexion(req, res);
                break;
            case "majInfoClient":
                JsonView.majInfoClient(req, res);
                break;
            case "restaurantsPartenaires":
                JsonView.restaurantsPartenaires(req, res);
                break;
            case "produitsDisponible":
                JsonView.produitsDisponible(req, res);
                break;
            case "clientsGustatif":
                JsonView.clientsGustatif(req, res);
                break;
            case "livreursPartenaires":
                JsonView.livreursPartenaires(req, res);
                break;
            case "validerCommande":
                JsonView.validerCommande(req, res);
                break;
            case "cloturerLivraison":
                JsonView.cloturerLivraison(req, res);
                break;
            case "rechercherLivraison":
                JsonView.rechercherLivraison(req, res);
                break;
            case "deconnexion":
                JsonView.ok(req, res, "Vous avez bien été déconnecté(e)");
                break;
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
