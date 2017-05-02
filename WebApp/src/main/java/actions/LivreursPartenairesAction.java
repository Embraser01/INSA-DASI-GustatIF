package actions;

import com.google.gson.Gson;
import metier.modele.Livreur;
import metier.modele.Produit;
import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class LivreursPartenairesAction extends Action {

    public LivreursPartenairesAction(ServiceMetier serviceMetier) {
        super(serviceMetier);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        List<Livreur> livreurs = this.serviceMetier.livreursPartenaires();

        res.getWriter().print(new Gson().toJson(livreurs));
    }
}
