$(document).ready(function () {
    let poto = sessionStorage.getItem('token');
    let poto2 = sessionStorage.getItem('userFecha');
    
    
    
    console.log(poto);
    console.log(poto2);
    const urlParams = new URLSearchParams(window.location.search);
    const logi = urlParams.get('logi');
    const pass = urlParams.get('passCifrada');
    $("#divAlert").hide();
    $("#divAlert2").hide();
    $("#progressContainer").hide();
    $("#progressContainer2").hide();
    $("#btnVerificar").click(function (e) {


        e.preventDefault();
        let codigo = $("#txtCodigo").val();

        $.ajax({
            type: "PUT",
            url: "http://localhost:8080/Sis_Gym/webresources/dto.usuario/autenticarCodigo",
            data: {
                logi: logi, // Asegúrate de definir 'logi' antes de esta llamada
                pass: pass,
                codigo: codigo
            },
            dataType: "json",
            success: function (data) {
                if (data.resultado === "valido") {
                    let params = new URLSearchParams();
                    params.append("logi", logi);
                    let url = "principal.html?" + params.toString();

                    // Redirigir a la página con los parámetros en la URL
                    $(location).attr('href', url);
                } else {
                    $("#divAlert2").text("Codigo no valido");
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
            },
            error: function () {
                alert("Error en la solicitud");
            }
        });
    });

    $("#btnGenerarQR").click(function (e) {
        e.preventDefault();

        $.ajax({
            type: "PUT",
            url: "http://localhost:8080/Sis_Gym/webresources/dto.usuario/asociarLlave",
            data: {
                logi: logi, // Asegúrate de definir 'logi' antes de esta llamada
                pass: pass
            },
            dataType: "json",
            success: function (data) {
                if (data.resultado === "valido") {
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
                } else if (data.resultado === "QR ya existente") {
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
                } else {
                    $("#divAlert").text("No valido");
                    $("#divAlert").show();
                    setTimeout(function () {
                        $("#divAlert").hide();
                    }, 5000);
                }
            },
            error: function () {
                console.log("Error en la solicitud");
            }
        });
    });

});