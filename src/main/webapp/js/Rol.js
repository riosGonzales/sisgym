$(document).ready(function () {
    let table;
    let codigo;
    const token = obtenerCookie('token');
    let tipo = sessionStorage.getItem("Tipo");
    console.log(tipo);
    //$("#btnEditarClase").hide();
    const headers = {
        "token": token
    };

    $("#divtopbar").load("topbar.html");
    $("#divsidebar").load("sidebar.html");
    $("#divFooter").load("footer.html");
    $("#chat").load("chat.html");

    function cargarTabla() {
        $.ajax({
            url: 'http://localhost:8080/Sis_Gym/webresources/entities.rol/json',
            dataType: 'json',
            headers: headers,
            success: function (data) {

                if (table) {
                    table.destroy();
                }
                data.data.forEach(function (item) {
                    item.fechRol = moment(item.registro).format('YYYY-MM-DD');
                    item.fechRolCifrada = cifradoAES(item.fechRol, 'tuClaveSecreta');
                });
                table = $('#miTabla').DataTable({
                    data: data.data,
                    columns: [
                        {data: 'codRol'},
                        {data: 'nombRol'},
                        {data: 'fechRolCifrada'},
                        {
                            "data": null,
                            "render": function (data, type, row) {
                                return '<a href="#" title="Editar" id="btnEditarClase"><i class="fa fa-edit"></i></a> ' +
                                        '<a href="#" title="Eliminar" id="btnEliminar"><i class="fa fa-trash"></i></a>';
                            }
                        }
                    ],
                    "language": {
                        url: '//cdn.datatables.net/plug-ins/1.13.6/i18n/es-ES.json'
                    }
                });
            }
        });
    }

    cargarTabla();

    $("#btnAgregarClase").click(function () {
        $("#modalAgregarClase").modal("show");
        $("#btnGuardarClase").show();
        $("#btnEditar").hide();
        //Asignar
        $("#miSelect").hide();
        $("#miSelect2").hide();
        $("#checklbl").hide();
        $("#check").hide();
        $("#lblUsuario").hide();
        $("#lblRol").hide();
        $("#btnSeleccionar").hide();

        //Agregar
        $("#txtTitulo").text("Crear");
        $('#descripcion').show();
        $('#fecha').show();
        $('#lblNombre').show();
        $('#lblFecha').show();
    });



    $("#btnGuardarClase").click(function () {
        let nombre = $('#descripcion').val();
        let registro = $('#fecha').val();

        let fechaFormateada = moment(registro, 'YYYY-MM-DD').add(1, 'days').format('YYYY-MM-DD');

        var claseEntidad = {
            nombRol: nombre,
            fechRol: fechaFormateada // Utilizar la fecha sumada
        };

        $.ajax({
            url: 'http://localhost:8080/Sis_Gym/webresources/entities.rol',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(claseEntidad),
            headers: headers,
            success: function (response) {
                alert('Nuevol rol creado');
                cargarTabla();
                $("#modalAgregarClase").modal("hide");
            },
            error: function (xhr, status, error) {
                console.log('Error al crear la clase:', error);
            }
        });
    });

    $('#miTabla').on('click', '#btnEditarClase', function () {
        $("#modalAgregarClase").modal("show");
        $("#txtTitulo").text("Editar");
        const dato = table.row($(this).closest('tr')).data();
        console.log(dato.codRol);
        $('#descripcion').show();
        $('#fecha').show();
        $('#lblNombre').show();
        $('#lblFecha').show();
        //Asignar
        $("#miSelect").hide();
        $("#miSelect2").hide();
        $("#checklbl").hide();
        $("#check").hide();
        $("#lblUsuario").hide();
        $("#lblRol").hide();
        $("#btnSeleccionar").hide();

        //
        $('#descripcion').val(dato.nombRol);
        $('#fecha').val(dato.fechRol);
        $("#btnGuardarClase").hide();

        $("#btnEditar").show();
        codigo = dato.codRol;
    });

    $("#btnEditar").click(function () {
        let nombre = $('#descripcion').val();
        let registro = $('#fecha').val();
        let fechaFormateada = moment(registro, 'YYYY-MM-DD').add(1, 'days').format('YYYY-MM-DD');
        var claseEntidad = {
            codRol: codigo,
            nombRol: nombre,
            fechRol: fechaFormateada // Utilizar la fecha sumada
        };
        $.ajax({
            url: 'http://localhost:8080/Sis_Gym/webresources/entities.rol/' + codigo,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(claseEntidad),
            headers: headers,
            success: function (response) {
                alert('Nueva clase editada');
                cargarTabla();
                $("#modalAgregarClase").modal("hide");
            },
            error: function (xhr, status, error) {
                console.log('Error al crear la clase:', error);
            }});
    });

    $("#btnGenerar").click(function () {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/Sis_Gym/webresources/entities.rolusuario/imprimirPDF",
            headers: headers,
            success: function (data, textStatus, jqXHR) {
                console.log("Generando PDF....");
                setTimeout(function () {
                    console.log("PDF Generado exitosamente!");
                    openReporte();
                }, 3000);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud: " + errorThrown);
            }
        });
    });
    function openReporte() {
        try {
            window.open("http://localhost:8080/Sis_Gym/reporte/Usuarios.pdf", 'width=800, height=600');
        } catch (e) {
            console.error("Error al abrir la ventana emergente: " + e);
        }
    }
    $("#btnAsignar").click(function () {
        $("#modalAgregarClase").modal("show");
        $("#txtTitulo").text("Asignar rol");
        //asignar
        $("#miSelect").show();
        $("#miSelect2").show();
        $("#checklbl").show();
        $("#check").show();
        $("#lblUsuario").show();
        $("#lblRol").show();
        $("#btnSeleccionar").show();

        //Agregar
        $('#descripcion').hide();
        $('#fecha').hide();
        $('#lblNombre').hide();
        $('#lblFecha').hide();
        $("#btnGuardarClase").hide();
        $("#btnEditar").hide();
    });

    $("#btnSeleccionar").click(function () {
        let codRol = $('#miSelect').val();
        let codiUsua = $('#miSelect2').val();
        let estado = $('#check').is(':checked') ? 1 : 0;
        console.log(estado);
        var claseEntidad = {
            codRol: codRol,
            codiUsua: codiUsua,
            actvRol: estado
        };
        $.ajax({
            url: 'http://localhost:8080/Sis_Gym/webresources/entities.rolusuario',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(claseEntidad),
            headers: headers,
            success: function (response) {
                if (estado === 1) {
                    alert('Nuevo rol asignado');
                } else {
                    alert('Rol quitado');
                }
                cargarTabla();
                $("#modalAgregarClase").modal("hide");
            },
            error: function (xhr, status, error) {
                console.log('Error al crear la clase:', error);
            }
        });
    });


    $('#miTabla').on('click', '#btnEliminar', function () {
        const dato = table.row($(this).closest('tr')).data();
        console.log(dato.codigo);
        let id = dato.codigo;
        $.ajax({
            url: 'http://localhost:8080/Sis_Gym/webresources/entities.rol/' + id,
            type: 'DELETE',
            contentType: 'application/json',
            data: JSON.stringify(id),
            headers: headers,
            success: function (response) {
                alert('Nueva clase eliminada');
                cargarTabla();
            },
            error: function (xhr, status, error) {
                console.log('Error al eliminar la clase:', error);
            }});
    });



    function cifradoAES(mensaje, clave) {
        var ciphertext = CryptoJS.AES.encrypt(mensaje, clave).toString();
        return ciphertext;
    }

    function descifradoAES(ciphertext, clave) {
        var bytes = CryptoJS.AES.decrypt(ciphertext, clave);
        var originalText = bytes.toString(CryptoJS.enc.Utf8);
        return originalText;
    }
});