package edu.brockport.localization.servlets;

import edu.brockport.localization.utilities.database.DatabaseConnector;
import edu.brockport.localization.utilities.database.Translation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerServlet extends HttpServlet {


    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if(req.getParameter("display") == null){
            HttpSession session = req.getSession();
            session.setAttribute("error", "display type is null");
            res.sendRedirect("index.jsp");
            return;
        }
        String displayType = req.getParameter("display");
        DatabaseConnector dbc = null;

        try{
            dbc = DatabaseConnector.getInstance();
        } catch (Exception e) {
            HttpSession session = req.getSession();
            session.setAttribute("error", e.getLocalizedMessage());
            res.sendRedirect("index.jsp");
            return;
        }

        ArrayList<Translation> translations = new ArrayList<>();

        try {
            translations = dbc.getTranslationList();
        } catch (Exception e) {
            HttpSession session = req.getSession();
            session.setAttribute("error", e.getLocalizedMessage());
            res.sendRedirect("index.jsp");
            return;
        }

        if(displayType.equalsIgnoreCase("javascript") || displayType.equalsIgnoreCase("resx")){
            HttpSession session = req.getSession();
            session.setAttribute("display", displayType);
            session.setAttribute("translations", translations);
            res.sendRedirect("translations.jsp");
            return;
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Unrecognized translation type: " + displayType);
            res.sendRedirect("index.jsp");
            return;
        }
    }
}
