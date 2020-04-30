package edu.brockport.localization.servlets;

import edu.brockport.localization.utilities.database.DatabaseConnector;
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
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ControllerServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(ControllerServlet.class);

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String stateChange = req.getParameter("changeState");

        /*https://coderanch.com/t/488280/java/Accented-Characters-Displayed-Wrongly
         *https://stackoverflow.com/questions/3182328/handling-spanish-characters-in-java-jsp
         * Was not displaying some characters correctly, so we had to set the character encoding
         * to this iso-8859-15 thing for every response. Also had to be included on every JSP that display characters
         * Code ranch one is gave the answer we needed, but SO link looked helpful too
         */
        res.setCharacterEncoding("iso-8859-15");

        if(stateChange.equals("log")){
            Scanner sc = new Scanner(new File("src/main/java/edu/brockport/localization/logs/ErrorLog.log"));
            ArrayList<String> logContents = new ArrayList<>();
            while(sc.hasNextLine()){
                logContents.add(sc.nextLine());
            }
            HttpSession session = req.getSession();
            session.setAttribute("logContents", logContents);
            res.sendRedirect("jsp/log.jsp");
            return;

        } else if(stateChange.equals("selections")){
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

        } else if(stateChange.equals("flagTranslations")) {
            HttpSession session = req.getSession();
            String[] selectedInfo = (String[])session.getAttribute("translationsToFlagList");
            String[] selectedInfoNotes = req.getParameterValues("notes");
            PrintWriter out = res.getWriter();
//
//
//
//            for(int i = 0; i < selectedInfo.length; i++){
//                out.println("selected info: " + selectedInfo[i] + selectedInfoNotes[i]);
//            }

            DatabaseConnector dbc = DatabaseConnector.getInstance();

            try{
                for(int i = 0; i < selectedInfo.length; i++){
                    String[] currentEntryToFlag = selectedInfo[i].split("#");
                    ResultSet transKeyIDResultSet = dbc.selectFromTable(dbc.getConnection(), new QueryBuilder(), "translationkeys", "ID",
                            "TransKey", currentEntryToFlag[0]);
                    transKeyIDResultSet.next();
                    String transKeyID = transKeyIDResultSet.getString("ID");
//                    out.println(selectedInfo[i] + "#" + selectedInfoNotes[i] + " TransKeyID: " + transKeyID);

                }
            } catch(SQLException e){
                out.println("something went wrong: " + e.getMessage());
            }

        } else {
            log.error("Error in ControllerServlet: stateChange parameter is null!");
            HttpSession session = req.getSession();
            session.setAttribute("error", "Error in ControllerServlet: stateChange parameter is null!");
            res.sendRedirect("jsp/login.jsp");
            return;
        }
    }
}
