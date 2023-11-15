


$(document).ready(function () {
    let poto = obtenerCookie('token');
    $("#divtopbar").load("topbar.html");
    $("#divsidebar").load("sidebar.html");
    $("#divFooter").load("footer.html");
    const token = poto;
    var idEmpleado = 1;
    var idCliente = 0;
    var idMatricula = 0;
    var idPago = 0;
    var plan = 0;
    var costoMemb = 0;
    var fechaInicioGlobal;
    var fechaFinGlobal;
    var idFactura = 0;
    var idClienteOperacion = 0;

    // Evento cuando se cambia la fecha de inicio
    $("#input2").on("change", function () {
        const idMembresia = $("#input1").val();
        const fechaInicio = $(this).val();

        let momentFechaInicio = moment(fechaInicio);

        if (idMembresia === "1") {
            momentFechaInicio.add(90, 'days');
        } else if (idMembresia === "2") {
            momentFechaInicio.add(150, 'days');
        } else if (idMembresia === "3") {
            momentFechaInicio.add(30, 'days');
        }

        const fechaFin = momentFechaInicio.format('YYYY-MM-DD');
        $("#input3").val(fechaFin);
    });

    // Evento cuando se cambia el tipo de membresía
    $("#input1").change(function () {
        const fechaInicio = $("#input2").val();

        // Obtener la fecha de inicio y calcular la fecha de fin basándose en el tipo de membresía
        const idMembresia = $(this).val();
        let momentFechaInicio = moment(fechaInicio);

        if (idMembresia === "1") {
            momentFechaInicio.add(90, 'days');
        } else if (idMembresia === "2") {
            momentFechaInicio.add(150, 'days');
        } else if (idMembresia === "3") {
            momentFechaInicio.add(30, 'days');
        }

        const fechaFin = momentFechaInicio.format('YYYY-MM-DD');
        $("#input3").val(fechaFin);
    });

    var table;

    const headers = {
        "token": token
    };


    //cargarRegistros();

    function cargarDatosEnTabla() {
        $.ajax({
            url: 'http://localhost:8080/Sis_Gym/webresources/entities.cliente',
            method: 'GET',
            headers: headers,
            dataType: 'json',
            success: function (data) {
                if (table) {
                    // Si la DataTable ya existe, destrúyela para reconstruirla con los nuevos datos
                    table.destroy();
                }

                table = $('#miTabla').DataTable({
                    data: data,
                    columns: [
                        {data: 'idCliente'},
                        {data: 'nombreCliente'},
                        {data: 'apellidos'},
                        {data: 'dni'},
                        {data: 'emailClie'},
                        {data: 'telefonoCliente'},
                        {
                            "data": null,
                            "render": function () {
                                return '<a id="btnEditar" title="Editar"><i class="fa fa-edit"></i></a> ' +
                                        '<a id="btnEliminar" title="Eliminar"><i class="fa fa-trash"></i></a>';
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

    cargarDatosEnTabla();

    $('#miTabla').on('click', '#btnEditar', function () {
        const dato = table.row($(this).closest('tr')).data();
        console.log("boton editar");
        console.log(dato.idCliente);
        $("#modalAgregarClase").modal("show");
        $("#titulo").text("Editar");
        $("#btnRegistrar").text("Actualizar");
        $("#dni").val(dato.dni);
        $("#nombres").val(dato.nombreCliente);
        $("#apellidos").val(dato.apellidos);
        $("#correo").val(dato.emailClie);
        $("#telefono").val(dato.telefonoCliente);
        idClienteOperacion = dato.idCliente;
    });


    $('#miTabla').on('click', '#btnEliminar', function () {
        const dato = table.row($(this).closest('tr')).data();
        idClienteOperacion = dato.idCliente;
        console.log("Id cliente a eliminar: " + idClienteOperacion);

        // Mostrar mensaje de confirmación
        if (confirm('¿Estás seguro de que deseas eliminar a este cliente?')) {
            $.ajax({
                url: 'http://localhost:8080/Sis_Gym/webresources/entities.cliente/' + idClienteOperacion,
                type: 'DELETE',
                headers: headers,
                success: function (response) {
                    // Éxito: La solicitud DELETE fue exitosa
                    cargarDatosEnTabla();
                    console.log('El cliente ha sido eliminado correctamente');
                },
                error: function (xhr, status, error) {
                    // Error: Manejo de errores
                    if (xhr.status === 401) {
                        console.log('Error: Token no válido');
                    } else {
                        console.log('Error al eliminar el cliente:', error);
                    }
                }
            });
        } else {
            console.log('Eliminación cancelada');
        }
        idClienteOperacion = 0;
    });



    const MEMBRESIAS_COSTO = {
        1: 150,
        2: 250,
        3: 59
    };

    const MEMBRESIAS_TIEMPO_MESES = {
        1: 3,
        2: 5,
        3: 1
    };
    function openWindow(id) {
        try {
            window.open("http://localhost:8080/Sis_Gym/factura/factura" + id + ".pdf", 'factura', 'width=800, height=600');
        } catch (e) {
            console.error("Error al abrir la ventana emergente: " + e);
        }
    }

    $("#btnPagar").click(function () {
        var fechaActual = moment().format('YYYY-MM-DD').toString();
        let metoPago = $("#cboMetoPago").val();
        var PagoMap = {
            fechaPago: fechaActual,
            monto: costoMemb,
            metodoPago: metoPago,
            matricula: idMatricula
        };
        $.ajax({
            url: 'http://localhost:8080/Sis_Gym/webresources/entities.pago',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(PagoMap),
            headers: headers,
            success: function (response) {
                idPago = response;
                /*$("#titulo").text("Matricula");
                 $("#dni, #nombres, #apellidos, #correo, #telefono, #lblDNI, #lblApellidos, #lblNombres,#lblCorreo, #lblTelefono,#btnRegistrar", ).hide();
                 
                 $("#input1, #input2, #input3 , #lblMembresia, #lblFI,#lblFF,#btnMatricular").show();*/

                let Factura = {
                    fechaInicio: fechaInicioGlobal,
                    fechaFin: fechaFinGlobal,
                    subtotal: costoMemb - (costoMemb * 0.18),
                    total: costoMemb,
                    clienteidCliente: idCliente,
                    empleadoidEmpleado: idEmpleado, //falta
                    pago: idPago
                };
                $.ajax({
                    url: 'http://localhost:8080/Sis_Gym/webresources/entities.factura',
                    method: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(Factura),
                    headers: headers,
                    success: function (response) {
                        idFactura = response;
                        // URL del servicio REST
                        var servicioURL = "http://localhost:8080/Sis_Gym/webresources/entities.factura/generarFactura/" + idFactura; // Reemplaza esto con la URL de tu servicio

                        // Llamar al servicio REST usando jQuery
                        $.ajax({
                            type: "GET",
                            url: servicioURL,
                            success: function (data, textStatus, jqXHR) {
                                // La solicitud se completó con éxito (código de respuesta 200)
                                setTimeout(function () {
                                    console.log("La factura se generó con éxito."+idFactura);
                                    openWindow(idFactura);
                                    $("#modalAgregarClase").modal("hide");
                                }, 5000);
                            },
                            error: function (jqXHR, textStatus, errorThrown) {
                                // La solicitud falló, manejar el error
                                console.error("Error al generar la factura: " + errorThrown);
                            }
                        });


                        /*$("#titulo").text("Matricula");
                         $("#dni, #nombres, #apellidos, #correo, #telefono, #lblDNI, #lblApellidos, #lblNombres,#lblCorreo, #lblTelefono,#btnRegistrar", ).hide();
                         
                         $("#input1, #input2, #input3 , #lblMembresia, #lblFI,#lblFF,#btnMatricular").show();*/

                        console.log('Factura creada exitosamente:', idFactura);
                        
                    },
                    error: function (xhr, status, error) {
                        console.error('Error al crear la factura', error);
                    }
                });
                console.log('Pago creado exitosamente:', response);
            },
            error: function (xhr, status, error) {
                console.error('Error al crear el pago:', error);
            }
            
        });
    }
    );
    $("#btnAgregarClase").click(function () {
        $("#modalAgregarClase").modal("show");
        $("#dni").on("input", function () {
            var dni = $(this).val();
            if (dni.length === 8) {
                let urlCreate = "?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Ind0a2U5MEBnbWFpbC5jb20ifQ.Y-Tamk0c0yQeyiW3Lnh9cY2y3UtsSkG9DP7rP78rbFw";
                $.get("https://dniruc.apisperu.com/api/v1/dni/" + dni + urlCreate, function (data) {
                    $("#nombres").val(data.nombres);
                    $("#apellidos").val(data.apellidoPaterno + " " + data.apellidoMaterno);
                });
            } else if (dni.length === 0) {
                limpiar();
            }
        });
    });

    $("#btnMatricular").click(function () {
        let idMembresia = $("#input1").val();
        let fechaInicio = $("#input2").val(); // Usamos el valor del input2 actual
        let fechaFin = $("#input3").val();

        // Resto de tu código sin cambios
        let momentFechaInicio = moment(fechaInicio);
        let momentFechaFin = moment(fechaFin);

        momentFechaInicio.add(1, 'days');
        momentFechaFin.add(1, 'days');

        fechaInicio = momentFechaInicio.format('YYYY-MM-DD');
        fechaFin = momentFechaFin.format('YYYY-MM-DD');

        console.log(idMembresia + fechaInicio + fechaFin);
        plan = idMembresia;

        var Matricula = {
            fechaInicio: fechaInicio,
            fechaFin: fechaFin,
            idCliente: idCliente,
            idMembresia: idMembresia
        };
        $.ajax({
            url: 'http://localhost:8080/Sis_Gym/webresources/entities.matricula',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(Matricula),
            headers: headers,
            success: function (response) {
                fechaInicioGlobal = fechaInicio;
                fechaFinGlobal = fechaFin;
                idMatricula = response;
                //plan = Membresias.idMembresia;
                costoMemb = MEMBRESIAS_COSTO[plan];
                console.log("costo Mem: " + costoMemb);
                $("#titulo").text("Pago");

                $("#input1, #input2, #input3 , #lblMembresia, #lblFI,#lblFF,#btnMatricular").hide();
                $("#lblPago, #cboMetoPago, #lblMonto, #txtMonto, #btnPagar").show();
                $("#txtMonto").val(costoMemb);
                console.log('Matriculado exitosamente', response);
            },
            error: function (xhr, status, error) {
                console.error('Error al crear el cliente:', error);
            }
        });
    });

    $("#input2").on("input", function () {
        let idMembresia = $("#input1").val();

        if (idMembresia === "1") { // Si el valor del select es 1 (Gold)
            let fechaInicio = $(this).val();
            let momentFechaInicio = moment(fechaInicio);
            momentFechaInicio.add(90, 'days'); // Sumar 150 días a la fecha de inicio
            let fechaFin = momentFechaInicio.format('YYYY-MM-DD'); // Actualizar la fecha de fin
            $("#input3").val(fechaFin); // Actualiza el valor en el campo de entrada #input3
        } else if (idMembresia === "2") {
            let fechaInicio = $(this).val();
            let momentFechaInicio = moment(fechaInicio);
            momentFechaInicio.add(150, 'days'); // Sumar 150 días a la fecha de inicio
            let fechaFin = momentFechaInicio.format('YYYY-MM-DD'); // Actualizar la fecha de fin
            $("#input3").val(fechaFin); // Actualiza el valor en el campo de entrada #input3
        } else if (idMembresia === "3") {
            let fechaInicio = $(this).val();
            let momentFechaInicio = moment(fechaInicio);
            momentFechaInicio.add(50, 'days'); // Sumar 150 días a la fecha de inicio
            let fechaFin = momentFechaInicio.format('YYYY-MM-DD'); // Actualizar la fecha de fin
            $("#input3").val(fechaFin); // Actualiza el valor en el campo de entrada #input3
        }
    });

    $("#btnRegistrar").click(function () {
        let dni = $("#dni").val();
        let nombres = $("#nombres").val();
        let apellidos = $("#apellidos").val();
        let correo = $("#correo").val();
        let telf = $("#telefono").val();
        var ClienteM = {
            dni: dni,
            nombreCliente: nombres, // Reemplaza con los valores correctos
            apellidos: apellidos, // Reemplaza con los valores correctos
            emailClie: correo, // Reemplaza con los valores correctos
            telefonoCliente: telf,
            estaClie: 1
        };

        if (idClienteOperacion === 0) {
            $.ajax({
                url: 'http://localhost:8080/Sis_Gym/webresources/entities.cliente',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(ClienteM),
                headers: headers,
                success: function (response) {
                    idCliente = response;
                    console.log(idCliente);

                    $("#titulo").text("Matricula");
                    $("#dni, #nombres, #apellidos, #correo, #telefono, #lblDNI, #lblApellidos, #lblNombres,#lblCorreo, #lblTelefono,#btnRegistrar", ).hide();

                    $("#input1, #input2, #input3 , #lblMembresia, #lblFI,#lblFF,#btnMatricular").show();
                    console.log('Cliente creado exitosamente:', response);
                    cargarDatosEnTabla();

                    //table.ajax.reload();
                },
                error: function (xhr, status, error) {
                    console.error('Error al crear el cliente:', error);
                }
            });
        } else {

            $.ajax({
                type: 'PUT', // Método HTTP
                url: 'http://localhost:8080/Sis_Gym/webresources/entities.cliente/' + idClienteOperacion, // URL de tu servicio REST
                headers: headers,
                data: JSON.stringify(ClienteM), // Convierte la entidad a JSON si es necesario
                contentType: 'application/json', // Tipo de contenido
                success: function (data, textStatus, jqXHR) {
                    $("#modalAgregarClase").modal("hide");
                    //location.reload();
//                    table = $('#miTabla').DataTable();
                    //                  table.ajax.reload();
                    cargarDatosEnTabla();

                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log("ERRORES WAWAWAW");
                }
            });

            $("#btnRegistrar").text("Siguiente");

        }
        idClienteOperacion = 0;

    });


    $("#btnGenerarReporte").click(function () {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/Sis_Gym/webresources/entities.cliente/imprimirPDF",
            headers: headers,
            success: function (data, textStatus, jqXHR) {
                // La solicitud fue exitosa, puedes manejar la respuesta aquí
                console.log("Generando PDF....");
                setTimeout(function () {
                    console.log("PDF Generado exitosamente!");
                    openReporte();
                }, 3000);

            },
            error: function (jqXHR, textStatus, errorThrown) {
                // La solicitud no fue exitosa, maneja el error
                console.error("Error en la solicitud: " + errorThrown);
            }
        });
    });



    function openReporte() {
        try {
            window.open("http://localhost:8080/Sis_Gym/reporte/Registro.pdf", 'width=800, height=600');
        } catch (e) {
            console.error("Error al abrir la ventana emergente: " + e);
        }
    }
});

$("#modalAgregarClase").on('hidden.bs.modal', function (e) {
    limpiar();
    $("#titulo").text("Registrar Cliente");
    $("#dni, #nombres, #apellidos, #correo, #telefono, #lblDNI, #lblApellidos, #lblNombres, #lblCorreo, #lblTelefono, #btnRegistrar").show();
    $("#input1, #input2, #input3, #lblMembresia, #lblFI, #lblFF, #btnMatricular").hide();
    $("#lblPago, #cboMetoPago, #lblMonto, #txtMonto, #btnPagar").hide();
});