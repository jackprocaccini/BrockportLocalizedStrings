package edu.brockport.localization.servlets;

import edu.brockport.localization.utilities.database.DatabaseConnector;
import edu.brockport.localization.utilities.database.QueryBuilder;
import edu.brockport.localization.utilities.database.Translation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.commons.text.StringEscapeUtils;

public class LoginServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(LoginServlet.class);

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String inputName = req.getParameter("reqUser");
        String inputPass = req.getParameter("reqPassword");
        String dbPassword = null;
        DatabaseConnector dbc = DatabaseConnector.getInstance();
        ArrayList<Translation> translations;

        try {
            Connection connection = dbc.getConnection();
            if(connection == null){
                log.error("Connection to database is null. Please make sure the database is up and running");
                HttpSession session = req.getSession();
                session.setAttribute("error", "Connection to database is null. Please make sure the database is up and running.");
                res.sendRedirect("index.jsp");
                return;
            }
            ResultSet rs = dbc.selectFromTable(connection, new QueryBuilder(),"login", "Password", "Username", inputName);
            if(rs.next()){
                dbPassword = rs.getString("password");
                rs.close();

            } else {
                log.warn("Incorrect username. User " + StringEscapeUtils.escapeHtml4(inputName) + " not found.");
                HttpSession session = req.getSession();
                session.setAttribute("error", "Incorrect username. User " + StringEscapeUtils.escapeHtml4(inputName) + " not found.");
                connection.close();
                res.sendRedirect("index.jsp");
                return;
            }

            if(inputPass.equals(dbPassword)){
                log.info("Login successful for user " + StringEscapeUtils.escapeHtml4(inputName));
                ResultSet translationsRs = dbc.selectJoinQueryMain(connection, new QueryBuilder());
                translations = Translation.getTranslationList(translationsRs);
                HttpSession session = req.getSession();
//                session.setAttribute("display", "all translations");
                session.setAttribute("translationsList", translations);
                session.removeAttribute("error");
                connection.close();
                res.sendRedirect("jsp/translations.jsp");
                return;

            } else {
                log.warn("Incorrect password for user: " + StringEscapeUtils.escapeHtml4(inputName));
                HttpSession session = req.getSession();
                session.setAttribute("error", "Incorrect password.");
                connection.close();
                res.sendRedirect("index.jsp");
                return;
            }
        } catch (SQLException e) {
            log.error("Error in LoginServlet " + e.getMessage());
            HttpSession session = req.getSession();
            session.setAttribute("error", "Something went wrong in Login Servlet: " + e.getMessage());
            res.sendRedirect("index.jsp");
            return;
        }
    }

    public DatabaseConnector getDatabaseConnector(){
        return DatabaseConnector.getInstance();
    }
}
