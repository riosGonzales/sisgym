
$(document).ready(function () {
    //75626947
    const token = "uCwYgxJr1hijbV8xDpyQ6w==";
    const usu = "hola";
    const headers = {
        "Content-Type": "application/json",
        "token": token,
        "usu": usu
    };
    $("#divtopbar").load("topbar.html");
    $("#divsidebar").load("sidebar.html");
    $("#divFooter").load("footer.html");
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

