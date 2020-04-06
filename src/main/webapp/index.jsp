<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 4/6/2020
  Time: 3:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Brockport Localized Strings - Login</title>
</head>
<body style="text-align: center">
    <form action="/login" method="post">
        <label for="reqUser">Username</label>
        <input type="text" id="reqUser" name="reqUser"><br>
        <label for="reqPassword">Password</label>
        <input type="password" id="reqPassword" name="reqPassword">
        <br>
        <input type="submit" value="Submit">
    </form>

    <div id="errorSection">
        <%
            String error = (String)session.getAttribute("error");

            if(error != null){
                out.println("<p style=\"color: red\">" + error + "</p>");
            }
        %>
    </div>
</body>
</html>
