<%@ page import="edu.brockport.localization.utilities.database.Translation" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Translations</title>
</head>
<body>
    <%
        String displayType = session.getAttribute("display").toString();
        ArrayList<Translation> translations = (ArrayList<Translation>) session.getAttribute("translations");
        int[] nums = new int[4];
        for(int i = 0; i < nums.length; i++){
            nums[i] = i + 1;
        }
    %>

    <table align="center">
        <caption>Translations List <%=displayType%></caption>
        <tr id="headers">
            <th>Key Test</th>
            <th>Translation</th>
            <th>Locale</th>
            <th>Status</th>
        </tr>

        <tr>
            <%
                if(translations != null){
                    for(int i = 0; i < translations.size(); i++){
                        out.print("<tr>");
                        out.println("<td style=\"text-align:center\">" + translations.get(i).getTransKey() + "</td>");
                        out.println("<td style=\"text-align:center\">" + translations.get(i).getTransValue() + "</td>");
                        out.println("<td style=\"text-align:center\">" + translations.get(i).getLocale() + "</td>");
                        out.println("<td style=\"text-align:center\">Active</td>");
                        out.println("</tr>");
                    }
                }
            %>
        </tr>
    </table>
    <a href="index.jsp">Back to main</a>
</body>
</html>
