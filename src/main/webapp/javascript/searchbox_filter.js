$("#searchBox").keyup(function(){
    let searchVal = $("#searchBox").val();

    if(!searchVal){ //if x is an empty string, display all translations based on the current dropdown selection
        console.log("Emtpy String!")
        displayTranslations($("#selectionChoice").val());
    } else { //otherwise, get all currently visible information and check if the input from the search box is present
        let $visibleData = $("tbody ." + $("#selectionChoice").val());
        console.log("visible data: " + $visibleData.text());
        let temp = "";

        $visibleData.each(function(){
            temp = $(this).text();
            console.log("Index of " + searchVal + " in " + temp + ": " + temp.indexOf(searchVal));

            if(temp.indexOf(searchVal) == -1){
                $(this).hide();
            } else {
                $(this).show();
            }
        });
    }
});