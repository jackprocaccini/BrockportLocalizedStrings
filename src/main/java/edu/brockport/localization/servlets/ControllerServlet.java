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
//        if(req.getParameter("display") == null){
//            HttpSession session = req.getSession();
//            session.setAttribute("error", "display type is null");
//            res.sendRedirect("stringchoices.jsp");
//            return;
//        }
//        String displayType = req.getParameter("display");
        DatabaseConnector dbc = null;
//
//        try{
//            dbc = DatabaseConnector.getInstance();
//        } catch (Exception e) {
//            HttpSession session = req.getSession();
//            session.setAttribute("error", e.getLocalizedMessage());
//            res.sendRedirect("stringchoices.jsp");
//            return;
//        }

        ArrayList<Translation> translations;

        try {
            translations = dbc.getTranslationList();
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

//        if(displayType.equalsIgnoreCase("javascript")){
//            ArrayList<Translation> jsTranslations = new ArrayList<Translation>();
//            for(int i = 0; i < translations.size(); i++){
//                if(translations.get(i).getResourceType().equalsIgnoreCase("js")){
//                    jsTranslations.add(translations.get(i));
//                }
//            }
//
//            HttpSession session = req.getSession();
//            session.setAttribute("display", displayType);
//            session.setAttribute("translations", jsTranslations);
//            session.setAttribute("error", "");
//            res.sendRedirect("translations.jsp");
//            return;
//
//        } else if(displayType.equalsIgnoreCase("resx")){
//            ArrayList<Translation> resxTranslations = new ArrayList<Translation>();
//            for(int i = 0; i < translations.size(); i++){
//                if(translations.get(i).getResourceType().equalsIgnoreCase(".NET")){
//                    resxTranslations.add(translations.get(i));
//                }
//            }
//
//            HttpSession session = req.getSession();
//            session.setAttribute("display", displayType);
//            session.setAttribute("translations", resxTranslations);
//            session.setAttribute("error", "");
//            res.sendRedirect("translations.jsp");
//            return;
//
//        } else if(displayType.equalsIgnoreCase("all")) {
//            HttpSession session = req.getSession();
//            session.setAttribute("display", displayType);
//            session.setAttribute("translations", translations);
//            session.setAttribute("error", "");
//            res.sendRedirect("translations.jsp");
//            return;
//
//        } else {
//            HttpSession session = req.getSession();
//            session.setAttribute("error", "Unrecognized translation type: " + displayType);
//            res.sendRedirect("stringchoices.jsp");
//            return;
//        }
    }
}

//else {
//        HttpSession session = req.getSession();
//        session.setAttribute("error", "Unrecognized translation type: " + displayType);
//        res.sendRedirect("stringchoices.jsp");
//        return;
