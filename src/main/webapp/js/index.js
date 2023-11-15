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
                    window.location.href = url;
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
