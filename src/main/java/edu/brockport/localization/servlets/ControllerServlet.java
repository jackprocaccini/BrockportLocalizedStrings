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
import java.sql.ResultSet;
import java.util.ArrayList;

public class ControllerServlet extends HttpServlet {


    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        DatabaseConnector dbc = null;

        ArrayList<Translation> translations;

        try {
            ResultSet rs = dbc.selectJoinFromTable(dbc.getConnection(), new QueryBuilder());
            translations = Translation.getTranslationList(rs);
//            translations = dbc.getTranslationList();
        } catch (Exception e) {
            HttpSession session = req.getSession();
            session.setAttribute("error", e.getLocalizedMessage());
            res.sendRedirect("index.jsp");
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("display", "all translations");
        session.setAttribute("translations", translations);
        res.sendRedirect("translations.jsp");
        return;

    }
}
