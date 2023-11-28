$(document).ready(function () {
    let table;
    const token = obtenerCookie('token');

    const headers = {
        "token": token
    };

    $("#divtopbar").load("topbar.html");
    $("#divsidebar").load("sidebar.html");
    $("#divFooter").load("footer.html");
    $("#chat").load("chat.html");

    function cargarTabla() {
        $.ajax({
            url: 'http://localhost:8080/Sis_Gym/webresources/entities.prueba/json',
            dataType: 'json',
            headers: headers,
            success: function (data) {

                if (table) {
                    table.destroy();
                }
                data.data.forEach(function (item) {
                    item.registro = moment(item.registro).format('YYYY-MM-DD');
                });
                table = $('#miTabla').DataTable({
                    data: data.data,
                    columns: [
                        {data: 'codigo'},
                        {data: 'nombre'},
                        {data: 'registro'},
                        {
                            "data": null,
                            "render": function (data, type, row) {
                                return '<a href="#" title="Editar"><i class="fa fa-edit"></i></a> ' +
                                        '<a href="#" title="Eliminar"><i class="fa fa-trash"></i></a>';
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
    });

    $("#btnGuardarClase").click(function () {
        let nombre = $('#descripcion').val();
        let registro = $('#fecha').val();

        let fechaFormateada = moment(registro, 'YYYY-MM-DD').add(1, 'days').format('YYYY-MM-DD');

        var claseEntidad = {
            nombre: nombre,
            registro: fechaFormateada // Utilizar la fecha sumada
        };

        $.ajax({
            url: 'http://localhost:8080/Sis_Gym/webresources/entities.prueba',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(claseEntidad),
            headers: headers,
            success: function (response) {
                alert('Nueva clase creada');
                cargarTabla();
                $("#modalAgregarClase").modal("hide");
            },
            error: function (xhr, status, error) {
                console.log('Error al crear la clase:', error);
            }
        });
    });
});