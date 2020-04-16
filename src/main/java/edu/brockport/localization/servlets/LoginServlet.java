package edu.brockport.localization.servlets;

import edu.brockport.localization.utilities.database.DatabaseConnector;
import edu.brockport.localization.utilities.database.Translation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class LoginServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String inputName = req.getParameter("reqUser");
        String inputPass = req.getParameter("reqPassword");
        String dbPassword = null;
        DatabaseConnector dbc = null;
        ArrayList<Translation> translations;

        try {
            dbc = DatabaseConnector.getInstance();
            ResultSet rs = dbc.selectFromTable("login", "Password", "Username", inputName);
            if(rs.next()){
                dbPassword = rs.getString("password");

            } else {
                HttpSession session = req.getSession();
                session.setAttribute("error", "Incorrect username. User " + inputName + " not found.");
                res.sendRedirect("index.jsp");
                return;
            }

            if(inputPass.equals(dbPassword)){
                translations = dbc.getTranslationList();
                HttpSession session = req.getSession();
                session.setAttribute("display", "all translations");
                session.setAttribute("translations", translations);
                res.sendRedirect("jsp/translations.jsp");
                return;

            } else {
                HttpSession session = req.getSession();
                session.setAttribute("error", "Incorrect password");
                res.sendRedirect("index.jsp");
                return;
            }
        } catch (Exception e) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Something went wrong: " + e.getLocalizedMessage());
            res.sendRedirect("index.jsp");
            return;
        }
    }
}
