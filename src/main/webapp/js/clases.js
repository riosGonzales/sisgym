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

    let fechaCalendario;

    function cargarTabla() {
        $.ajax({
            url: 'http://localhost:8080/Sis_Gym/webresources/entities.clases/json',
            dataType: 'json',
            headers: headers,
            success: function (data) {
                data.data.forEach(function (item) {
                    var formattedDate = moment(item.fecha, 'MMM D, YYYY h:mm:ss A').format('YYYY-MM-DD');
                    item.fecha = formattedDate;
                });

                if (table) {
                    table.destroy();
                }

                table = $('#miTabla').DataTable({
                    data: data.data,
                    columns: [
                        {data: 'idClases'},
                        {data: 'descripcion'},
                        {data: 'fecha'},
                        {data: 'horario'},
                        {data: 'instructor'},
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

                fechaCalendario = sessionStorage.getItem("fechaCalendario"); // Asignar el valor a la variable
                const fechaFormateada = moment(fechaCalendario, 'DD/MM/YYYY').format('YYYY-MM-DD');
                table.search(fechaFormateada).draw();
            }
        });
    }

    cargarTabla();

    $("#btnAgregarClase").click(function () {
        $("#modalAgregarClase").modal("show");
        const fechaFormateada = moment(fechaCalendario, 'DD/MM/YYYY').format('YYYY-MM-DD');
        $("#fecha").val(fechaFormateada);
    });

    $("#btnGuardarClase").click(function () {
        let descripcion = $('#descripcion').val();
        let fecha = $('#fecha').val();
        let hora = $('#hora').val();
        let instructor = $('#instructor').val();

        // Sumar un día a la fecha antes de enviarla
        let fechaFormateada = moment(fecha, 'YYYY-MM-DD').add(1, 'days').format('YYYY-MM-DD');

        var claseEntidad = {
            instructor: instructor,
            fecha: fechaFormateada, // Utilizar la fecha sumada
            horario: hora,
            descripcion: descripcion
        };

        $.ajax({
            url: 'http://localhost:8080/Sis_Gym/webresources/entities.clases',
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