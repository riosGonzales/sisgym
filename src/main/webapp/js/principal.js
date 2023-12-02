$(document).ready(function () {
    $.getJSON("validarsesion", function (data) {

        if (data.resultado === "error") {
            $(location).attr('href', "index.html");
        } else {


            let poto = obtenerCookie('token');

            console.log("token: " + poto + " logi: ");
            const token = poto;

            const headers = {
                "token": token
            };
            //$("#cambiarClaveForm").hide();
            const urlParams = new URLSearchParams(window.location.search);
            let logi = urlParams.get('logi');

            $("#divLogi").text("Usuario: " + logi);

            //$("#cambiarClaveForm").hide();
            $("#btnCambiarPass").click(function () {
                $("#cambiarClaveForm").show();

            });
            $("#btnGuardarContrasena").click(function () {



                let pass = $("#txtActualPass").val();
                let passCif = cifrar(pass);

                let newpass = $("#txtNuevaContrasena").val();
                let newpassCif = cifrar(newpass);
                console.log(passCif);
                console.log(newpassCif);
                let confpass = $("#txtConfirmarContraseña").val();

                if (newpass === confpass) {

                    if (pass != newpass) {
                        $.ajax({
                            type: "PUT",
                            url: "http://localhost:8080/Sis_Gym/webresources/dto.usuario/cambiarPass",
                            data: {
                                logi: logi, // Asegúrate de definir 'logi' antes de esta llamada
                                pass: passCif,
                                newpass: newpassCif
                            },
                            headers: headers,
                            dataType: "json",
                            success: function (data) {
                                if (data.resultado === "valido") {
                                    cambiarClave();
                                    alert("Contraseña cambiada exitosamente");
                                    window.location.href = "index.html";
                                } else {
                                    alert("Error al cambiar la contraseña, logi o contraseña incorrecto");
                                }
                            },
                            error: function () {
                                alert("Error en la solicitud, logi o contaseña incorrecto");
                            }
                        });
                    } else {
                        alert("La nueva contraseña debe ser diferente a la actual")


                    }

                } else {
                    alert("Nueva contraseña y confirmar contraseña deben ser iguales")
                }


            });
            $("#spanCambiarPagina").click(function () {
                let params = new URLSearchParams();
                params.append("logi", logi);
                let url = "cambiarClave.html?" + params.toString();
                window.location.href = url;
            });

            $('.dropdown-item').on('click', function () {
                // Acciones a realizar cuando se hace clic en el elemento <a>
                $.getJSON("cerrarSesion", function (data) {
                    alert("click");
                    if (data.resultado === "ok") {
                        $(location).attr('href', "index.html");
                    }else{
                        alert("Error al cerrar sesion");
                    }
                });
                // Puedes agregar aquí cualquier acción que desees realizar al hacer clic en el enlace
            });


        }
    });


});


function cambiarClave() {

    $("#txtActualPass").val("");
    $("#txtNuevaContrasena").val("");
    $("#btnGuardar").text("Guardar");
    $("#cambiarClaveForm").hide();
}


$("#btnCancelarContrasena").click(function () {
    $("#txtActualLogi").val("");
    $("#txtActualPass").val("");
    $("#txtNuevaContrasena").val("");
    // Ocultar el formulario
    $("#cambiarClaveForm").hide();
});