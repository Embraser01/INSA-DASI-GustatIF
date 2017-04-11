import actions.Action;
import actions.ProduitsDisponibleAction;
import actions.RestaurantsPartenairesAction;
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
            case "produitsDisponible":
                action = new ProduitsDisponibleAction(serviceMetier);
                break;
            case "restaurantsPartenaires":
                action = new RestaurantsPartenairesAction(serviceMetier);
                break;
            default:
                res.sendError(404);
                return;
        }

        action.execute(req, res);
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
