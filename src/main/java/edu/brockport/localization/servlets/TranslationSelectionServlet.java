package edu.brockport.localization.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TranslationSelectionServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] selectedInfo = req.getParameterValues("translationsList");
        PrintWriter out = res.getWriter();

        if(selectedInfo == null){
            out.println("selectedInfo is null");
        } else {
            for(int i = 0; i < selectedInfo.length; i++){
                out.println("selected info " + i + ": " + selectedInfo[i] +"\n");
            }
        }
    }
}
