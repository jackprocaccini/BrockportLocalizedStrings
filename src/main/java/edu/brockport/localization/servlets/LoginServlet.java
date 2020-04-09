package edu.brockport.localization.servlets;

import edu.brockport.localization.utilities.database.DatabaseConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;

public class LoginServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String inputName = req.getParameter("reqUser");
        String inputPass = req.getParameter("reqPassword");
        String dbPassword = null;

        if(inputName.equalsIgnoreCase("spolv")){
            res.sendRedirect("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
            return;
        }

        try {
            DatabaseConnector dbo = DatabaseConnector.getInstance();
            ResultSet rs = dbo.selectFromTable("login", "Password", "Username", inputName);
            if(rs.next()){
                dbPassword = rs.getString("password");

            } else {
                HttpSession session = req.getSession();
                session.setAttribute("error", "Incorrect username. User " + inputName + " not found.");
                res.sendRedirect("index.jsp");
                return;
            }

            if(inputPass.equals(dbPassword)){
                res.sendRedirect("stringchoices.jsp");
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
