<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 4/27/2020
  Time: 9:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=iso-8859-15" language="java" %>
<html>
<head>
    <title>Log File</title>
</head>
<body>
    <%
        ArrayList<String> logContents = (ArrayList<String>)session.getAttribute("logContents");
        if(logContents != null){
            for(int i = 0; i < logContents.size(); i++){
                out.println(logContents.get(i) + "\n");
            }
        }
    %>

    <a href="translations.jsp">Back to Translations</a>
</body>
</html>
