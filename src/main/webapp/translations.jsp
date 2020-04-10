<%@ page import="edu.brockport.localization.utilities.database.Translation" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    table{
        font-family:Arial, sans-serif;
        border-collapse: collapse;
        width: 75%;
    }

    td, th {
        border: 1px solid black;
        padding: 8px;
        text-align: center;
    }

    th {
        font-weight: bold;
        text-transform: uppercase;
        background-color: lightgrey;
    }

    tr:nth-child(even){
        background-color: lightgrey;
    }

    tr:hover {
        background-color: rgb(85, 106, 165);
    }

</style>
<head>
    <title>Translations</title>
</head>
<body>
    <%
        String displayType = session.getAttribute("display").toString();
        ArrayList<Translation> translations = (ArrayList<Translation>) session.getAttribute("translations");
    %>

    <label for="translationchoice">Choose a translation:</label>

    <select align="center" id="translationchoice">
        <option value="javascript">Javascript</option>
        <option value="resx">Resx</option>
        <option value="all">All</option>
    </select>

    <table align="center">
        <caption>Translations List <%=displayType%></caption>
        <tr id="headers">
            <th>Key</th>
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
                        out.println("<td style=\"text-align:center\">" + translations.get(i).getStatus() + "</td>");
                        out.println("<td><input type=\"checkbox\"/></td>");
                        out.println("</tr>");
                    }
                }
            %>
        </tr>
    </table>
    <a href="index.jsp">Back to main</a>
</body>
</html>
