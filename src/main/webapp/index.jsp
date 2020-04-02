<html>
    <head>
        <Title>Brockport Localized Strings</Title>
    </head>

    <body style="text-align: center">
        <header class="pageHeader">
            <h1 id="pageTitle">Localized Strings</h1>
        </header>

        <nav id="indexButtonsNav" style="align-content: space-evenly">
            <form id="formButtons" action="/controller" method="post">
                <button type="submit" class="buttons" name="display" value="javascript">Javascript Translations</button>
                <button type="submit" class="buttons" name="display" value="resx">Resx Translations</button>
                <button type="submit" class="buttons" name="display" value="all">All</button>
            </form>
        </nav>

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
