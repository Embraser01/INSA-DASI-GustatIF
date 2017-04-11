package actions;

import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class Action {

    protected ServiceMetier serviceMetier;

    public Action(ServiceMetier serviceMetier) {
        this.serviceMetier = serviceMetier;
    }

    public abstract void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException;
}
