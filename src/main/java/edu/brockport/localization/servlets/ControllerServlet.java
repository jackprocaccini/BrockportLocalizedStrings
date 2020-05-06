package edu.brockport.localization.servlets;

import edu.brockport.localization.utilities.database.DatabaseConnector;
import edu.brockport.localization.utilities.database.FlaggedTranslation;
import edu.brockport.localization.utilities.database.QueryBuilder;
import edu.brockport.localization.utilities.database.Translation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ControllerServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(ControllerServlet.class);
    private static DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    private static Calendar cal = Calendar.getInstance();

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String stateChange = req.getParameter("changeState");

        /*https://coderanch.com/t/488280/java/Accented-Characters-Displayed-Wrongly
         *https://stackoverflow.com/questions/3182328/handling-spanish-characters-in-java-jsp
         * Was not displaying some characters correctly in browser, so we had to set the character encoding
         * to this iso-8859-15 thing for every response. Also had to be included on every JSP that displays characters
         * Code ranch one is gave the answer we needed, but SO link looked helpful too
         */
        res.setCharacterEncoding("iso-8859-15");

        if(stateChange.equals("log")){ //view the log page
            Scanner sc = new Scanner(new File("src/main/java/edu/brockport/localization/logs/ErrorLog.log"));
            ArrayList<String> logContents = new ArrayList<>();
            while(sc.hasNextLine()){
                logContents.add(sc.nextLine());
            }
            HttpSession session = req.getSession();
            session.setAttribute("logContents", logContents);
            res.sendRedirect("jsp/log.jsp");
            return;

        } else if(stateChange.equals("selections")){ //go to the page that lists all selections from the main page
            String[] selectedInfo = req.getParameterValues("selectionsList");
            if(selectedInfo == null){
                log.warn("No information selected from translations.jsp");
                res.sendRedirect("jsp/translations.jsp");
                return;
            }

            HttpSession session = req.getSession();
            session.setAttribute("translationsToFlagList", selectedInfo);
            res.sendRedirect("jsp/flagtranslations.jsp");
            return;

        } else if(stateChange.equals("flagTranslations")) { //flag translations from the "selections" page
            HttpSession session = req.getSession();
            String[] selectedInfo = (String[])session.getAttribute("translationsToFlagList");
            String[] selectedInfoNotes = req.getParameterValues("notes");

            DatabaseConnector dbc = DatabaseConnector.getInstance();

            try{
                dbc.insertFlaggedTranslations(selectedInfo, selectedInfoNotes);
            } catch(SQLException e){
                log.error("Error in ControllerServlet flagging: " + e.getMessage());
                session.setAttribute("status", "Unable to flag selected translation(s): " + e.getMessage());
                res.sendRedirect("jsp/status.jsp");
                return;
            }

            session.removeAttribute("translationsToFlagList");
            session.setAttribute("status", "Successfully flagged selected translations!");
            res.sendRedirect("jsp/status.jsp");
            return;

        } else if(stateChange.equals("viewTranslations")){
            HttpSession session = req.getSession();
            ArrayList<Translation> translations = (ArrayList<Translation>) session.getAttribute("translationsList");

            try {
                DatabaseConnector dbc = DatabaseConnector.getInstance();
                ResultSet translationsRs = dbc.selectJoinQueryMain(dbc.getConnection(), new QueryBuilder());
                translations = Translation.getTranslationList(translationsRs);
                session.setAttribute("translationsList", translations);
                res.sendRedirect("jsp/translations.jsp");
                return;
            } catch(SQLException e){
                log.error("Error in Controller Servlet: " + e.getMessage());
                System.out.println(e);
                session.setAttribute("error", "Could not get all translations: " + e.getMessage());
                res.sendRedirect("jsp/translations.jsp");
                return;
            }

        } else if(stateChange.equals("viewFlagged")){
            try {
                DatabaseConnector dbc = DatabaseConnector.getInstance();
                ResultSet flaggedTranslationsRs = dbc.selectJoinQueryFlagged(dbc.getConnection(), new QueryBuilder());
                ArrayList<FlaggedTranslation> flaggedTranslationList = FlaggedTranslation.getFlaggedTranslationList(flaggedTranslationsRs);
                HttpSession session = req.getSession();
                session.setAttribute("flaggedTranslationsList", flaggedTranslationList);
                res.sendRedirect("jsp/flaggedtranslations.jsp");
                return;

            } catch(SQLException e){

            }

        } else {
            log.error("Error in ControllerServlet: stateChange parameter is null!");
            HttpSession session = req.getSession();
            session.setAttribute("error", "Error in ControllerServlet: stateChange parameter (" + stateChange + ") is null or unrecognized!");
            res.sendRedirect("index.jsp");
            return;
        }
    }
}
