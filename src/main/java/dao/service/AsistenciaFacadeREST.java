/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.service;

import Entities.Asistencia;
import Entities.Cliente;
import Service.AsistenciaService;
import Service.ClienteService;
import Service.ValidacionService;
import dto.AsistenciaDTO;
import java.util.List;
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
    ClienteService clienteService = new ClienteService();
    Cliente cliente = new Cliente();

    @PersistenceContext(unitName = "com.mycompany_Sis_Gym_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public AsistenciaFacadeREST() {
        super(Asistencia.class);
    }

    @OPTIONS
    public Response optionsCrear() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "http://localhost:4200")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, token")
                .header("Access-Control-Allow-Credentials", "true")
                .build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response crear(AsistenciaDTO entidad, @HeaderParam("token") String token) {
        if (validacion.ValidarToken(token)) {
            try {
                Cliente cliente = clienteService.buscarDNI(entidad.getClienteidCliente());
                if (cliente != null) {
                    asistenciaService.crear(entidad);
                    String nombre = cliente.getNombreCliente();
                    String apellido = cliente.getApellidos();
                    String respuesta = "{\"nombreCliente\": \"" + nombre + "\", \"apellidos\": \"" + apellido + "\"}";

                    return Response
                            .status(Response.Status.OK)
                            .header("Access-Control-Allow-Origin", "http://localhost:4200")
                            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, token")
                            .header("Access-Control-Allow-Credentials", "true")
                            .entity(respuesta)
                            .build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Cliente no encontrado").build();
                }
            } catch (Exception e) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Error: Token no válido").build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Error: Token no válido").build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Asistencia entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Asistencia find(@PathParam("id") Integer id) {
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
    public List<Asistencia> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
