let clave;
let respuestalogueo = "invalido";
let codiUsua;
let tipoUsua;
let logiUsuaGen;
$(document).ready(function () {
    $("#divAlert").hide();
    $("#progressContainer").hide();

    $("#btnIniciar").click(function () {
        let logi = $("#txtUsuario").val();
        let pass = $("#txtClave").val();
        let passCifrada = cifrar(pass);
        var fechaActual = moment().format('YYYY-MM-DD HH:mm:ss').toString();
        console.log(passCifrada);
        console.log(fechaActual);
        var requestDataCli = {
            logiUsua: logi,
            passUsua: passCifrada,
            fechUsua: fechaActual
        };
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/Sis_Gym/webresources/dto.usuario/validarUsuario",
            data: requestDataCli,
            success: function (data) {
                if (data.resultado === "valido") {
                    respuestalogueo = "ok";
                    codiUsua = data.codiUsua;
                    tipoUsua = data.tipoUsua;
                    logiUsuaGen = data.logiUsua;
                    console.log("Token generado:" + data.token);
                    let params = new URLSearchParams();
                    params.append("logi", logi);
                    params.append("passCifrada", passCifrada);
                    let url = "Autenticar.html?" + params.toString();
                    //Seteado en el sesstionStorage

                    establecerCookie('token', data.token, 7);
//                    sessionStorage.setItem("token", data.token);
//                    sessionStorage.setItem("userFecha", logi + fechaActual);
                    sessionStorage.setItem("logi", logi);
                    console.log(logi);
                    //alert(data.token);
                    //alert(logi+fechaActual);

                    $.ajax({
                        type: 'GET',
                        url: 'http://localhost:8080/Sis_Gym/webresources/dto.usuario/obtenerClaveBob', // Reemplaza esto con la URL real de tu servidor
                        dataType: 'json',
                        success: function (response) {
                            // Manejar la respuesta del servidor
                            alert('Resultado:' + response.resultado);
                            // Aquí puedes realizar cualquier acción adicional con la respuesta
                            let  claveBob = response.resultado;
                            // Llamar a runDH() para obtener la clave compartida
                            runDH(claveBob)
                                    .then(async (aliceSharedKey) => {
                                        // Hacer la solicitud AJAX con la clave compartida
                                        $.ajax({
                                            type: "POST",
                                            url: "http://localhost:8080/Sis_Gym/webresources/dto.usuario/obtenerClave",
                                            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                                            data: {
                                                "AliceClaveJS": aliceSharedKey // Utilizar la clave compartida obtenida
                                            },
                                            success: function (response) {

                                                if (respuestalogueo === "ok") {
                                                    $.getJSON("registrarsesion", {codi: codiUsua, logi: logiUsuaGen,
                                                        tipoUsuario: tipoUsua}, function (data) {
                                                        // La respuesta del servidor se encuentra en la variable "response"
                                                        console.log("Respuesta del servidor:", response.resultado);
                                                        sessionStorage.setItem("clave", response.resultado);
                                                        alert(response.resultado);
                                                        window.location.href = url;
                                                    });

                                                } else {

                                                    mostrarMensaje("alerta", "NO HAS INICIADO SESION");
                                                }



                                                // Puedes realizar otras acciones con la respuesta aquí
                                            },
                                            error: function (error) {
                                                // Maneja el error en caso de que la solicitud falle
                                                console.error("Error en la solicitud AJAX:", error);
                                            }
                                        });
                                    })
                                    .catch((error) => {
                                        console.error("Error al obtener la clave compartida:", error);
                                      });

                        },
                        error: function (error) {
                            // Manejar errores en la solicitud AJAX
                            console.error('Error en la solicitud AJAX:', error);
                        }
                    });
                } else {
                    // Mostrar mensaje de error y realizar otras acciones si es necesario
                    $("#divAlert").show();
                    $("#divAlert").text("Usuario o contraseña inválidos");
                    $("#progressContainer").show();

                    let progressBar = $("#progressBar");
                    let progress = 0;

                    let interval = setInterval(function () {
                        progress += 25;
                        progressBar.css("width", progress + "%");
                        progressBar.attr("aria-valuenow", progress);

                        if (progress >= 100) {
                            clearInterval(interval);
                        }
                    }, 200);

                    setTimeout(function () {
                        progressBar.css("width", "0%");
                        progressBar.attr("aria-valuenow", 0);
                        $("#progressContainer").hide();
                        $("#divAlert").hide();
                        clearInterval(interval);
                    }, 2000);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("Error en la solicitud:", textStatus, errorThrown);
            }
        });
    });
});
