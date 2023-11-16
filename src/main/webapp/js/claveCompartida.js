$(document).ready(function () {
    $("#chatbot").hide();

    $("#btnAbrirChat").click(function () {
        if ($("#chatbot").is(":visible")) {
            // Si el chatbot está visible, ocultarlo
            $("#chatbot").hide();
        } else {
            // Si el chatbot está oculto, mostrarlo
            $("#chatbot").show();
        }
    });
    $.ajax({
        url: 'http://localhost:8080/Sis_Gym/webresources/dto.usuario/obtenerClave', // Reemplaza con la URL de tu servicio
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            // Manipula los datos recibidos aquí
            console.log('Datos recibidos:', data);
            // Ejemplo de cómo acceder al campo 'resultado' del JSON
            clave = data.resultado;
            console.log('Resultado:', clave);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error('Error en la solicitud:', textStatus, errorThrown);
        }
    });
});

