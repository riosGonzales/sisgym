$(document).ready(function () {
    $.getJSON("validarsesion", function (data) {
        
        if (data.resultado === "error") 
            $(location).attr('href', "index.html");

    });


});




