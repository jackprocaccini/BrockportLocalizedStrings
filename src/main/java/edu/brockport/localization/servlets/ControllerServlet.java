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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import org.apache.commons.text.StringEscapeUtils;
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
                logContents.add(StringEscapeUtils.escapeHtml4(sc.nextLine()));
            }
            HttpSession session = req.getSession();
            session.setAttribute("logContents", logContents);
            res.sendRedirect("jsp/log.jsp");
            return;

        } else if(stateChange.equals("selections")){ //go to the page that lists all selections
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

            for(int i = 0; i < selectedInfoNotes.length; i++){
                if(selectedInfoNotes[i].contains("<script>") || selectedInfoNotes[i].contains("</script>")){
                    res.sendRedirect("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
                    return;
                }
            }

            DatabaseConnector dbc = DatabaseConnector.getInstance();

            try{
                Connection connection = dbc.getConnection();
                dbc.insertFlaggedTranslations(connection, selectedInfo, selectedInfoNotes);
                connection.close();
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

        } else if(stateChange.equals("viewTranslations")){ //views all translations, regardless of flagging or not
            HttpSession session = req.getSession();
            ArrayList<Translation> translations = (ArrayList<Translation>) session.getAttribute("translationsList");

            try {
                DatabaseConnector dbc = DatabaseConnector.getInstance();
                Connection connection = dbc.getConnection();
                ResultSet translationsRs = dbc.selectJoinQueryMain(connection, new QueryBuilder());
                translations = Translation.getTranslationList(translationsRs);
                connection.close();
                session.setAttribute("translationsList", translations);
                res.sendRedirect("jsp/translations.jsp");
                return;
            } catch(SQLException e){
                log.error("Error in Controller Servlet: " + e.getMessage());
                session.setAttribute("status", "Could not get all translations: " + e.getMessage());
                res.sendRedirect("jsp/status.jsp");
                return;
            }

        } else if(stateChange.equals("viewFlagged")){ //views all currently flagged translations
            try {
                DatabaseConnector dbc = DatabaseConnector.getInstance();
                Connection connection = dbc.getConnection();
                ResultSet flaggedTranslationsRs = dbc.selectJoinQueryFlagged(connection, new QueryBuilder());
                ArrayList<FlaggedTranslation> flaggedTranslationList = FlaggedTranslation.getFlaggedTranslationList(flaggedTranslationsRs);
                flaggedTranslationsRs.close();
                connection.close();
                for(int i = 0; i < flaggedTranslationList.size(); i++){
                    flaggedTranslationList.get(i).setNotes(StringEscapeUtils.escapeHtml4(flaggedTranslationList.get(i).getNotes()));
                }
                HttpSession session = req.getSession();
                session.setAttribute("flaggedTranslationsList", flaggedTranslationList);
                res.sendRedirect("jsp/viewflaggedtranslations.jsp");
                return;

            } catch(SQLException e){
                HttpSession session = req.getSession();
                session.setAttribute("status", "Unable to view flagged translations. Check the log for more information: " + e.getMessage());
                res.sendRedirect("jsp/status.jsp");
                return;
            }

        } else if(stateChange.equals("resolveFlaggedTranslations")){ //resolves currently flagged translations
            String[] selectedInfo = req.getParameterValues("flaggedSelectionsList");
            HttpSession session = req.getSession();

            if(selectedInfo == null){
                log.warn("No information selected from viewflaggedtranslations.jsp");
                res.sendRedirect("jsp/viewflaggedtranslations.jsp");
                return;
            }

            try{
                DatabaseConnector dbc = DatabaseConnector.getInstance();
                Connection connection = dbc.getConnection();

                for(int i = 0; i < selectedInfo.length; i++){
                    selectedInfo[i] = StringEscapeUtils.unescapeHtml4(selectedInfo[i]);
                }
                dbc.resolveFlaggedTranslations(connection, new QueryBuilder(), selectedInfo);
                connection.close();
                session.setAttribute("status" ,"Successfully resolved selected translations!");
                res.sendRedirect("jsp/status.jsp");
                return;
            } catch(SQLException e){
                log.error("Error in ControllerServlet: Could not resolve selected translations: " + e.getMessage());
                session.setAttribute("status", "Error in ControllerServlet: Could not resolve selected translations: " + e.getMessage());
                res.sendRedirect("jsp/status.jsp");
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
