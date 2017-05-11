package actions;

import exception.MissingInformationException;
import exception.NotLoggedException;
import exception.ValiderCommandeException;
import metier.modele.Client;
import metier.modele.Commande;
import metier.modele.Produit;
import metier.modele.Restaurant;
import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class ValiderCommandeAction extends Action {

    public ValiderCommandeAction(ServiceMetier serviceMetier) {
        super(serviceMetier);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, NotLoggedException, MissingInformationException, ValiderCommandeException {

        if (!isClient(req, res)) throw new NotLoggedException();


        try {

            Map<String, String[]> params = req.getParameterMap();
            Restaurant restaurant = new Restaurant();
            List<Produit> produitsDisponibles = null;

            Commande commande = new Commande((Client) req.getSession().getAttribute(SESSION_CLIENT_FIELD));

            for (Map.Entry<String, String[]> entry : params.entrySet()) {
                if (entry.getKey().equalsIgnoreCase("commande[restaurant]")) {
                    // Setting Restaurant
                    restaurant.setId(Long.valueOf(entry.getValue()[0]));
                    commande.setRestaurant(restaurant);
                    produitsDisponibles = this.serviceMetier.produitsDisponibles(restaurant);

                } else if (entry.getKey().startsWith("commande[produits]")
                        && entry.getKey().endsWith("[id]")
                        && produitsDisponibles != null
                        ) {

                    String key = entry.getKey().replaceFirst("\\[id]", "[qte]");

                    Produit curr = null;

                    int qte = Integer.parseInt(params.get(key)[0]);
                    long id = Long.valueOf(entry.getValue()[0]);

                    for (Produit pr : produitsDisponibles) {
                        if (id == pr.getId()) {
                            curr = pr;
                            break;
                        }
                    }

                    for (int i = 0; i < qte; i++) {
                        commande.ajouterProduit(curr);
                    }
                }
            }

            commande.setDateEnregistrementCommande(new Date());

            if (!this.serviceMetier.validerCommande(commande)) {
                throw new ValiderCommandeException();
            }
        } catch (ValiderCommandeException e) {
            throw e;
        } catch (Exception e) {
            throw new MissingInformationException();
        }
    }
}
