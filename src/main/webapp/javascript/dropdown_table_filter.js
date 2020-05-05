// Whenever the selection is changed in the dropdown menu, the table updates based on the selection
$("#selectionChoice").change(function() {
    var selectedType = $("#selectionChoice :selected").val();
    console.log(selectedType);
    displayTranslations(selectedType);
    // console.log($("#translationchoice :selected").val());
});

// Displays table contents based on the drop down menu's selected type
function displayTranslations(translationType){
    console.log("display function: " + translationType);
    console.log($("." + translationType).text());

    var $tableRows = $("tbody tr");

    console.log($tableRows);

    $tableRows.each(function(){
        if($(this).hasClass(translationType)){
            $(this).show();
        } else {
            $(this).hide();
        }
    });
}