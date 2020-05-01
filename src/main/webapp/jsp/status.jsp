<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 4/30/2020
  Time: 8:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=iso-8859-15" language="java" %>
<html>
<head>
    <title>Brockport Localized Strings - Status</title>
</head>
<body style="text-align: center">
    <%
        String status = (String)session.getAttribute("status");

        out.println("<h1>" + status + "</h1>");
    %>

    <form action="/controller" method="post">
        <button type="submit" name="changeState" value="translations">Return to Translations</button>
    </form>
</body>
</html>
