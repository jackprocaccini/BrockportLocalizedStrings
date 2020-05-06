<%@ page import="edu.brockport.localization.utilities.database.FlaggedTranslation" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=iso-8859-15" language="java" %>
<html>
<head>
    <title>Flagged Translations</title>
    <link rel="stylesheet" href="../css/tablesort.css">
</head>
<body>
    <%
        ArrayList<FlaggedTranslation> translations = (ArrayList<FlaggedTranslation>) session.getAttribute("flaggedTranslationsList");
    %>

    <table class="table">
        <thead>
            <tr>
                <th>Key</th>
                <th>Translation</th>
                <th>Locale</th>
                <th>Date Flagged</th>
                <th>Date Resolved</th>
                <th>Notes</th>
                <th>Translation Status</th>
                <th>Unflag</th>
            </tr>
        </thead>

        <tbody>
            <%
                if(translations != null){
                    for(int i = 0; i < translations.size(); i++){
                        out.println("<tr class=\"" + translations.get(i).getResourceType() + " " + translations.get(i).getLocale() + " all\">");
                        out.println("<td>" + translations.get(i).getTransKey() + "</td>");
                        out.println("<td>" + translations.get(i).getTransValue() + "</td>");
                        out.println("<td>" + translations.get(i).getLocale() + "</td>");
                        out.println("<td>" + translations.get(i).getDateFlagged() + "</td>");
                        out.println("<td>" + translations.get(i).getDateResolved() + "</td>");
                        out.println("<td>" + translations.get(i).getNotes() + "</td>");
                        out.println("<td>" + translations.get(i).getStatus() + "</td>");
                        out.println("<td>" + "<input type=\"checkbox\" class=\"check\" name=\"selectionsList\" value=\"" + translations.get(i).toString() + "\">" + "</td>");
                        out.println("</tr>");
                    }
                }
            %>
        </tbody>
    </table>

    <form action="/controller" method="post">
        <button type="submit" name="changeState" value="viewTranslations">Return to Translations</button>
    </form>
</body>
</html>
