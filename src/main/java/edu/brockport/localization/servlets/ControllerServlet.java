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
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ControllerServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(ControllerServlet.class);

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String stateChange = req.getParameter("changeState");

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
            String[] selectedInfo = req.getParameterValues("translationsList");
            PrintWriter out = res.getWriter();

            if(selectedInfo == null){
                out.println("selectedInfo is null");
            } else {
                for(int i = 0; i < selectedInfo.length; i++){
                    out.println("selected info " + i + ": " + selectedInfo[i] +"\n");
                }
            }
            return;
        } else {
            log.error("Error in ControllerServlet: stateChange parameter is null!");
            HttpSession session = req.getSession();
            session.setAttribute("error", "Error in ControllerServlet: stateChange parameter is null!");
            res.sendRedirect("index.jsp");
            return;
        }


    }
}
