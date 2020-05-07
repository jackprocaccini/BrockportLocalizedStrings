<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 4/6/2020
  Time: 3:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=iso-8859-15" language="java" %>
<html>
<head>
    <title>Brockport Localized Strings - Login</title>
    <link rel="stylesheet" href="/css/login_page.css">
</head>
<body>
<%
    String error = "";
    if(session.getAttribute("error") != null){
        error = (String)session.getAttribute("error");
    }
%>
<header class="heading">
    <h1 class="mainHeading">Localized Strings</h1>
</header>
<div class="login-page">
    <div class="form">
        <form action="/login" method="post" class="login-form">
<%--            <label for="reqUser">Username</label>--%>
            <input type="text" id="reqUser" name="reqUser" placeholder="username"><br>
<%--            <label for="reqPassword">Password</label>--%>
            <input type="password" id="reqPassword" name="reqPassword" placeholder="password">
            <br>
            <button type="submit">Login</button>
        </form>
        <p class="errorSection"><%=error%></p>
    </div>
</div>

<%--    <div id="errorSection">--%>
<%--        <%--%>
<%--            String error = (String)session.getAttribute("error");--%>

<%--            if(error != null){--%>
<%--                out.println("<p style=\"color: red\">" + error + "</p>");--%>
<%--            }--%>
<%--            session.removeAttribute("error");--%>
<%--        %>--%>
<%--    </div>--%>
</body>
</html>
