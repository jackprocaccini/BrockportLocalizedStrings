package edu.brockport.localization.servlets;

import edu.brockport.localization.utilities.database.DatabaseConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ControllerServlet extends HttpServlet {


    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if(req.getParameter("display") == null){
            res.sendRedirect("index.jsp");
        }
        String displayType = req.getParameter("display");
        DatabaseConnector dbc;

        try{
            dbc = DatabaseConnector.getInstance();
        } catch (Exception e) {
            HttpSession session = req.getSession();
            session.setAttribute("error", e.getLocalizedMessage());
            res.sendRedirect("index.jsp");
        }

        if(displayType.equalsIgnoreCase("javascript") || displayType.equalsIgnoreCase("resx")){
            HttpSession session = req.getSession();
            session.setAttribute("display", displayType);
            res.sendRedirect("translations.jsp");
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Unrecognized translation type: " + displayType);
            res.sendRedirect("index.jsp");
        }
    }
}
