/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.service;

import Entities.Asistencia;
import Entities.Cliente;
import Entities.Matricula;
import Service.AsistenciaService;
import Service.ClienteService;
import Service.CorsUtil;
import Service.MatriculaService;
import Service.ValidacionService;
import dto.AsistenciaDTO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author anshi
 */
@Stateless
@Path("entities.asistencia")
public class AsistenciaFacadeREST extends AbstractFacade<Asistencia> {

    ValidacionService validacion = new ValidacionService();
    AsistenciaService asistenciaService = new AsistenciaService();
    MatriculaService matriculaService = new MatriculaService();
    ClienteService clienteService = new ClienteService();
    Cliente cliente = new Cliente();

    @PersistenceContext(unitName = "com.mycompany_Sis_Gym_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public AsistenciaFacadeREST() {
        super(Asistencia.class);
    }

    @OPTIONS
    public Response optionsCrear() {
        return CorsUtil.buildCorsResponseToken();
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response crear(AsistenciaDTO entidad, @HeaderParam("token") String token) {
        if (!validacion.ValidarToken(token)) {
            return CorsUtil.buildUnauthorizedResponse();
        }

        try {
            Cliente cliente = clienteService.buscarDNI(entidad.getClienteidCliente());
            if (cliente == null) {
                return CorsUtil.buildNotFoundResponse("Cliente no encontrado");
            }

            if (cliente.getEstaClie() != 1) {
                return CorsUtil.buildNotFoundResponse("Matricula expirada");
            }

            Matricula matriculaConFechas = matriculaService.obtenerFechas(cliente.getDni());
            if (matriculaConFechas != null) {
                Date fechaActual = new Date();
                Date fechaInicio = matriculaConFechas.getFechaInicio();
                Date fechaFin = matriculaConFechas.getFechaFin();

                if (fechaActual.after(fechaInicio) && fechaActual.before(fechaFin)) {
                    asistenciaService.crear(entidad);
                    String nombre = cliente.getNombreCliente();
                    String apellido = cliente.getApellidos();
                    long diasDiferencia = (fechaFin.getTime() - fechaActual.getTime()) / (1000 * 60 * 60 * 24);
                    String respuesta = "{\"nombreCliente\": \"" + nombre + "\", \"apellidos\": \"" + apellido
                            + "\", \"diasDiferencia\": " + diasDiferencia + "}";
                    return CorsUtil.buildOkResponse(respuesta);
                } else if (fechaActual.before(fechaInicio)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    String fechaInicioString = dateFormat.format(fechaInicio);
                    String respuesta = "Membresia inactiva\", \"fechaInicio\": \"" + fechaInicioString;
                    return CorsUtil.buildNotFoundResponse(respuesta);
                } else {
                    clienteService.LogicDelete(cliente.getIdCliente());
                    return CorsUtil.buildNotFoundResponse("Matricula expirada");
                }
            } else {
                return CorsUtil.buildNotFoundResponse("No encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CorsUtil.buildUnauthorizedResponse();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Asistencia entity
    ) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id
    ) {
        super.remove(super.find(id));
    }

    @OPTIONS
    @Path("contar-asis")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response optionsCount() {
        return CorsUtil.buildCorsResponse();
    }

    @GET
    @Path("contar-asis")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response countAsistencias() {
        try {
            List<Map<String, Object>> contador = asistenciaService.countAsistencia();
            return CorsUtil.buildCorsResponse(contador);
        } catch (Exception e) {
            return CorsUtil.buildCorsResponseError();
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Asistencia find(@PathParam("id") Integer id
    ) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Asistencia> findAll() {
        return asistenciaService.Listar();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Asistencia> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to
    ) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
