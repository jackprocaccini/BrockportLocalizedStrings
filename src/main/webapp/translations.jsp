<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Translations</title>
</head>
<body>
    <%
        String displayType = session.getAttribute("display").toString();
        int[] nums = new int[4];
        for(int i = 0; i < nums.length; i++){
            nums[i] = i + 1;
        }
    %>

    <table align="center">
        <caption>Translations List <%=displayType%></caption>
        <tr id="headers">
            <th>Key</th>
            <th>Translation</th>
            <th>Locale</th>
            <th>Status</th>
        </tr>

        <tr>
            <%
                for(int i : nums){
                    out.println("<td style=\"text-align:center\">" + i + "</td>");
                }
            %>
        </tr>
    </table>
    <a href="index.jsp">Back to main</a>
</body>
</html>
