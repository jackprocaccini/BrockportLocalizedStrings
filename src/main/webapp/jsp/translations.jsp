<%@ page import="edu.brockport.localization.utilities.database.Translation" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Translations</title>
    <link rel="stylesheet" href="../tablesort.css">
</head>
<body>
    <%
        ArrayList<Translation> translations = (ArrayList<Translation>) session.getAttribute("translations");
    %>

    <select align="center" id="selectionchoice">
        <option value="javascript">Javascript</option>
        <option value="resx">Resx</option>
        <option value="en_US">en_US</option>
        <option value="es_ES">es_ES</option>
        <option value="all">All</option>
    </select>

    <table class="table table-sortable">
        <thead>
            <tr>
                <th>Key</th>
                <th>Translation</th>
                <th>Locale</th>
                <th>Status</th>
                <th>Select</th>
            </tr>
        </thead>

        <tbody>
            <%
                if(translations != null){
                    for(int i = 0; i < translations.size(); i++){
                        out.println("<tr class=\"" + translations.get(i).getResourceType() + " " + translations.get(i).getLocale() + "\">");
                        out.println("<td>" + translations.get(i).getTransKey() + "</td>");
                        out.println("<td>" + translations.get(i).getTransValue() + "</td>");
                        out.println("<td>" + translations.get(i).getLocale() + "</td>");
                        out.println("<td>" + translations.get(i).getStatus() + "</td>");
                        out.println("<td>" + "<input type=\"checkbox\" class=\"check\"" + "</td>");
                        out.println("</tr>");
                    }
                }
            %>
        </tbody>
    </table>
    <a href="../index.jsp">Back to main</a>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="../javascript/tablesort.js"></script>

    <script type="text/javascript">
        //checkbox stuff
        const $selectedInformation = $("#selectedInfoStrings");
        // $selectedInformation.hide();
        var $checkboxes = $(".check");
        for(var i = 0; i < $checkboxes.length; i++){
            var $thischeckbox = $($checkboxes[i]);
            $thischeckbox.click(function() {
                if($(this).is(":checked")){
                    console.log("Checked:" + $(this).parent().parent().text());

                    //sets selected text to a paragraph beneath the table with the id 'selectedInfoStrings'
                    $selectedInformation.text($selectedInformation.text() + $(this).parent().parent().text() + ",");
                } else {
                    //removes the previously selected string from the paragraph beneath the table
                    console.log("Unchecked:" + $(this).parent().parent().text());
                    var $tempSelected = $selectedInformation.text().replace($(this).parent().parent().text() + ",", "");
                    $selectedInformation.text($tempSelected);
                }
            });
        }
    </script>

    <script type="text/javascript">
        // Whenever the selection is changed in the dropdown menu, the table updates based on the selection
        $("#selectionchoice").change(function() {
            var selectedType = $("#selectionchoice :selected").val();
            console.log(selectedType);
            displayTranslations(selectedType);
            // console.log($("#translationchoice :selected").val());
        });

        // Displays table contents based on the drop down menu's selected type
        function displayTranslations(selectedType){
            console.log("display function: " + selectedType);
            console.log($("." + selectedType).text());

            var $tableRows = $("tbody tr");

            console.log($tableRows);

            if(selectedType == "all"){
                $tableRows.each(function(){
                    $(this).show();
                });
            } else {
                $tableRows.each(function(){
                    if(!$(this).hasClass(selectedType)){
                        $(this).hide();
                    } else {
                        $(this).show();
                    }
                });
            }
        }
    </script>
</body>
</html>
