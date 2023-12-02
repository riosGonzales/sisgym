$(document).ready(function () {

    $.getJSON("validarsesion", function (data) {
        if (data.resultado === "error") {
            $(location).attr('href', "index.html");

        } else {
            // Obtener parámetros de la URL y cookies
            const urlParams = new URLSearchParams(window.location.search);
            const logi = urlParams.get('logi');
            const pass = urlParams.get('passCifrada');
            const token = obtenerCookie('token');

            // Función para manejar errores en las solicitudes AJAX
            function handleError() {
                alert("Error en la solicitud");
            }

            // Función para redirigir a la página principal
            function redirectToPrincipal() {
                let params = new URLSearchParams();
                params.append("logi", logi);
                let url = "principal.html?" + params.toString();
                $(location).attr('href', url);
            }

            // Verificar código al hacer clic en el botón
            $("#btnVerificar").click(function (e) {
                e.preventDefault();
                let codigo = $("#txtCodigo").val();

                $.ajax({
                    type: "PUT",
                    url: "http://localhost:8080/Sis_Gym/webresources/dto.usuario/autenticarCodigo",
                    data: {
                        logi: logi,
                        pass: pass,
                        codigo: codigo
                    },
                    dataType: "json",
                    success: function (data) {
                        if (data.resultado === "valido") {
                            redirectToPrincipal();
                        } else {
                            // Manejar caso de código no válido
                            handleInvalidCode();
                        }
                    },
                    error: handleError
                });
            });

            // Generar QR al hacer clic en el botón
            $("#btnGenerarQR").click(function (e) {
                e.preventDefault();

                $.ajax({
                    type: "PUT",
                    url: "http://localhost:8080/Sis_Gym/webresources/dto.usuario/asociarLlave",
                    data: {
                        logi: logi,
                        pass: pass
                    },
                    dataType: "json",
                    success: function (data) {
                        if (data.resultado === "valido") {
                            handleValidQR();
                        } else if (data.resultado === "QR ya existente") {
                            // Manejar caso de QR ya existente
                            handleExistingQR();
                        } else {
                            // Manejar caso de resultado no válido
                            handleInvalidResult();
                        }
                    },
                    error: handleError
                });
            });

            // Función para manejar caso de código no válido
            function handleInvalidCode() {
                $("#divAlert2").text("Código no válido");
                $("#divAlert2").show();
                $("#progressContainer2").show();
                let progressBar = $("#progressBar2");
                let progress = 0;

                let interval = setInterval(function () {
                    progress += 9;
                    progressBar.css("width", progress + "%");
                    progressBar.attr("aria-valuenow", progress);

                    if (progress >= 100) {
                        clearInterval(interval);
                    }
                }, 200);

                setTimeout(function () {
                    progressBar.css("width", "0%");
                    progressBar.attr("aria-valuenow", 0);
                    $("#progressContainer2").hide();
                    $("#divAlert2").hide();
                    clearInterval(interval);
                }, 3000);
            }

            // Función para manejar caso de QR válido
            function handleValidQR() {
                let params = new URLSearchParams();
                params.append("logi", logi);
                params.append("passCifrada", pass);
                let url = "codigoqr.html?" + params.toString();

                $("#progressContainer").show();
                let progressBar = $("#progressBar");
                let progress = 0;

                // Cambia el color a verde (éxito)
                progressBar.css("background-color", "#28a745");

                let interval = setInterval(function () {
                    progress += 15;
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
                    clearInterval(interval);
                    window.location.href = url;
                }, 3000);
            }

            // Función para manejar caso de QR ya existente
            function handleExistingQR() {
                $("#divAlert").text("QR ya existente");
                $("#divAlert").show();
                $("#progressContainer").show();
                let progressBar = $("#progressBar");
                let progress = 0;

                let interval = setInterval(function () {
                    progress += 5;
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
                }, 5000);
            }

            // Función para manejar caso de resultado no válido
            function handleInvalidResult() {
                $("#divAlert").text("No válido");
                $("#divAlert").show();
                setTimeout(function () {
                    $("#divAlert").hide();
                }, 5000);
            }

        }

    });


});

