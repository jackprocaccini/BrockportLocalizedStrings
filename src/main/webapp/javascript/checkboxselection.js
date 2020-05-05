//whenever a checkbox is clicked, all the data pertaining to that checkbox's row is collected
const $selectedInformation = $("#selectedInfoStrings");
$selectedInformation.hide();
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