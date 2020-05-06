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

<style>
    body{
        background-color: lightgrey;
        display: table;
        width: 100%;
        height: 100%;
        margin: 0;
        padding: 0;
        font-family: Futura;
    }

    .form{
        display: table-cell;
        vertical-align: middle;
    }
    .login_form{
        display: table;
        margin: auto;
        text-align: center;
    }

    .header{
        background-color: whitesmoke;
    }

</style>
</head>
<body>
<div class="row">
<div class="header">
    <h1>Localization of Strings</h1>
</div>
</div>

    <div class="row">
    <div class="form">
    <form class="login_form" action="/login" method="post">
        <label for="reqUser">Username</label>
        <input type="text" id="reqUser" name="reqUser"><br>
        <label for="reqPassword">Password</label>
        <input type="password" id="reqPassword" name="reqPassword">
        <br>
        <input type="submit" value="Submit">
    </form>
    </div>
    </div>
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
