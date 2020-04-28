<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 4/27/2020
  Time: 9:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log File</title>
</head>
<body>
    <%
        String logContents = (String)session.getAttribute("logContents");
        if(logContents != null){
            out.println("<p>" + logContents + "</p>");
        }
    %>

    <a href="translations.jsp">Back to Translations</a>
</body>
</html>
