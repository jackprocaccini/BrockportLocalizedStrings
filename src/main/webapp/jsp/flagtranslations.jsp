<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 4/30/2020
  Time: 2:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=iso-8859-15" language="java" %>
<html>
<head>
    <title>Flag Translations</title>
    <link rel="stylesheet" href="../css/tablesort.css">
</head>
<body>
    <%
        String[] translationsToFlag = (String[])session.getAttribute("translationsToFlagList");
    %>

    <form action="/controller" method="post">
        <table class="table">
            <thead>
                <tr>
                    <th>Key</th>
                    <th>Translation</th>
                    <th>Locale</th>
                    <th>Status</th>
                    <th>Notes</th>
                </tr>
            </thead>

            <tbody>
                <%
                    if(translationsToFlag != null){
                        for(int i = 0; i < translationsToFlag.length; i++){
                            String[] temp = translationsToFlag[i].split("#"); //just used to display selected info on table. All we're really interested in is the Notes section
                            out.println("<tr>");
                            out.println("<td>" + temp[0] + "</td>");
                            out.println("<td>" + temp[1] + "</td>");
                            out.println("<td>" + temp[2] + "</td>");
                            out.println("<td>" + temp[3] + "</td>");
                            out.println("<td>" + "<input type=\"text\" name=\"notes\">" + "</td>");
                            out.println("</tr>");
                        }
                    }
                %>
            </tbody>
        </table>
        <button type="submit" name="changeState" value="flagTranslations">Flag Selected Translations</button>
    </form>
</body>
</html>
