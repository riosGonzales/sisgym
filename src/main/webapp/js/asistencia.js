
$(document).ready(function () {
    //75626947
    const token = obtenerCookie('token');

    const headers = {
        "Content-Type": "application/json",
        "token": token
    };
    $("#divtopbar").load("topbar.html");
    $("#divsidebar").load("sidebar.html");
    $("#divFooter").load("footer.html");
    $("#chat").load("chat.html");

    function registrarAsistencia() {
        var dni = $("#inputDNI").val();
        if (dni.trim() === "") {
            alert("Por favor, ingresa un DNI válido.");
            return;
        }
        var AsistenciaM = {
            clienteidCliente: dni
        };
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/Sis_Gym/webresources/entities.asistencia",
            data: JSON.stringify(AsistenciaM),
            headers: headers,
            dataType: "text",
            contentType: "application/json",
            success: function (data, textStatus, jqXHR) {
                limpiar();
                alert("Asistencia asistida");
                alert(data);
                console.log("Asistió !");
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud: " + errorThrown);
                alert("Error!: " + jqXHR.responseText);
            }
        });
    }
    $("#inputDNI").on("keydown", function (event) {
        if (event.key === "Enter") {
            registrarAsistencia();
        }
    });
    $("#btnAsistencia").click(registrarAsistencia);
});

