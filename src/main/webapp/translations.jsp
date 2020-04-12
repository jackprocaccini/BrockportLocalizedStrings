<%@ page import="edu.brockport.localization.utilities.database.Translation" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Translations</title>
    <link rel="stylesheet" href="tablesort.css">
</head>
<body>
    <%
        ArrayList<Translation> translations = (ArrayList<Translation>) session.getAttribute("translations");
    %>

    <label for="translationchoice">Choose a translation:</label>

    <select align="center" id="translationchoice">
        <option value="javascript">Javascript</option>
        <option value="resx">Resx</option>
        <option value="all">All</option>
    </select>

    <table class="table table-sortable">
        <thead>
            <tr>
                <th>Key</th>
                <th>Translation</th>
                <th>Locale</th>
                <th>Status</th>
            </tr>
        </thead>

        <tbody>
            <%
                if(translations != null){
                    for(int i = 0; i < translations.size(); i++){
                        out.print("<tr>");
                        out.println("<td>" + translations.get(i).getTransKey() + "</td>");
                        out.println("<td>" + translations.get(i).getTransValue() + "</td>");
                        if(translations.get(i).getLocale().equalsIgnoreCase("en_us")){
                            out.println("<td>" + "<img src=\"usa.png\" alt=\"en_US\" style=\"width:75px;height:75px;\"></img>" + "</td>");
                        } else if(translations.get(i).getLocale().equalsIgnoreCase("es_es")){
                            out.println("<td>" + "<img src=\"spain.png\" alt=\"es_ES\" style=\"width:75px;height:75px;\"></img>" + "</td>");
                        } else {
                            out.println("<td>" + translations.get(i).getLocale() + "</td>");
                        }
//                        out.println("<td>" + translations.get(i).getLocale() + "</td>");
                        out.println("<td>" + translations.get(i).getStatus() + "</td>");
                        out.println("</tr>");
                    }
                }
            %>
        </tbody>
    </table>
    <a href="index.jsp">Back to main</a>
    <script src="tablesort.js"></script>
</body>
</html>
