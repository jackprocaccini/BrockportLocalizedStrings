package edu.brockport.localization.servlets;

import edu.brockport.localization.utilities.database.DatabaseConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ControllerServlet extends HttpServlet {

    //MAKE SURE TO MAP THIS SERVLET IN WEB.XML YA DINGUS @JACK

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if(req.getParameter("display") == null){
            res.sendRedirect("index.jsp");
        }
        HttpSession session = req.getSession();
        String displayType = req.getParameter("display");
        DatabaseConnector dbc;

        try{
            dbc = DatabaseConnector.getInstance();
        } catch (Exception e) {
            session.setAttribute("error", e.getLocalizedMessage());
            res.sendRedirect("index.jsp");
        }

        if(displayType.equalsIgnoreCase("javascript")){
            //use the dbc to get all of the js translations
        } else if(displayType.equalsIgnoreCase("resx")){
            //use the dbc to get all of the resx translations
        } else {
            session.setAttribute("error", "Unrecognized translation type: " + displayType);
            res.sendRedirect("index.jsp");
        }
    }
}
