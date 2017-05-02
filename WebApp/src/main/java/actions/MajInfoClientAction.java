package actions;

import exception.NotLoggedException;
import metier.service.ServiceMetier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MajInfoClientAction extends Action {

    public MajInfoClientAction(ServiceMetier serviceMetier) {
        super(serviceMetier);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException, NotLoggedException {

        if (!isClient(req, res)) throw new NotLoggedException();
    }
}
