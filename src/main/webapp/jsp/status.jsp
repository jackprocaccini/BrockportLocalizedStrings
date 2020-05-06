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

    <%
        session.removeAttribute("status");
    %>

    <form action="/controller" method="post">
        <button type="submit" name="changeState" value="viewTranslations">Return to Translations</button>
    </form>
</body>
</html>
