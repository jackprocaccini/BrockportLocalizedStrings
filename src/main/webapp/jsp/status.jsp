<%@ page contentType="text/html; charset=iso-8859-15" language="java" %>
<html>
<head>
    <title>Brockport Localized Strings - Status</title>
    <link rel="stylesheet" href="../css/table_sort2.css">
</head>
<body style="text-align: center">
    <div class="main-page">
        <div class="msg">
            <%
                String status = (String)session.getAttribute("status");

                out.println("<h1>" + status + "</h1>");
            %>

            <%
                session.removeAttribute("status");
            %>
        </div>

        <form action="/controller" method="post">
            <div class="buttons">
                <button type="submit" name="changeState" value="viewTranslations">Return to Translations</button>
            </div>
        </form>
    </div>
</body>
</html>
