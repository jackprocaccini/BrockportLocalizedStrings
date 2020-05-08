<%@ page import="edu.brockport.localization.utilities.database.Translation" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=iso-8859-15" language="java" %>
<html>
<head>
    <title>Translations</title>
    <link rel="stylesheet" href="../css/table_sort2.css">
</head>
<body>
    <%
        ArrayList<Translation> translations = (ArrayList<Translation>) session.getAttribute("translationsList");
    %>
<div class="main-page">

    <div class="filters">
        <select align="center" id="selectionChoice">
            <option value="all">All</option>
            <option value="js">Javascript</option>
            <option value="resx">.NET</option>
            <option value="en_US">en_US</option>
            <option value="es_ES">es_ES</option>
        </select>
        <input type="text" id="searchBox" placeholder="Search">
    </div>

    <div class="main-table">
    <form action="/controller" method="post">
        <table class="table table-sortable">
            <thead>
                <tr>
                    <th>Key</th>
                    <th>Translation</th>
                    <th>Locale</th>
                    <th>Status</th>
                    <th>Select</th>
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
                            out.println("<td>" + translations.get(i).getStatus() + "</td>");
                            out.println("<td>" + "<input type=\"checkbox\" class=\"check\" name=\"selectionsList\" value=\"" + translations.get(i).toString() + "\">" + "</td>");
                            out.println("</tr>");
                        }
                    }
                %>
            </tbody>
        </table>
        <div class="buttons">
            <button type="submit" name="changeState" value="selections">Flag Translations</button>
            <button type="submit" name="changeState" value="log">View Logs</button>
            <button type="submit" name="changeState" value="viewFlagged">View Currently Flagged Translations</button>
        </div>
    </form>
    </div>

    <p id="selectedInfoStrings"></p>
</div>
    <a href="../index.jsp">Back to main</a>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="../javascript/tablesort.js"></script>
    <script type="text/javascript" src="../javascript/checkboxselection.js"></script>
    <script type="text/javascript" src="../javascript/dropdown_table_filter.js"></script>
    <script type="text/javascript" src="../javascript/searchbox_filter.js"></script>
</body>
</html>
