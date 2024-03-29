package dao.service;

import Entities.Cliente;
import Service.ClienteService;
import Service.CorsUtil;
import Service.ValidacionService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
import jpa.exceptions.IllegalOrphanException;
import jpa.exceptions.NonexistentEntityException;

@Stateless
@Path("entities.cliente")
public class ClienteFacadeREST extends AbstractFacade<Cliente> {

    /*@PersistenceContext(unitName = "com.mycompany_Sis_Gym_war_1.0-SNAPSHOTPU")
    private EntityManager em; */
    ClienteService servicio = new ClienteService();
    ValidacionService validacion = new ValidacionService();

    public ClienteFacadeREST() {
        super(Cliente.class);
    }

    @OPTIONS
    public Response optionsCreate() {
        return CorsUtil.buildCorsResponseToken();
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Cliente entidad, @HeaderParam("token") String token) {
        if (validacion.ValidarToken(token)) {
            int codigo = servicio.crear(entidad);
            if (codigo == -1) {
                return CorsUtil.buildResponse(Response.Status.BAD_REQUEST, "{\"error\": \"Cliente ya existente\"}");
            } else {
                return CorsUtil.buildCorsResponseToken(codigo);
            }
        } else {
            return CorsUtil.buildUnauthorizedResponse();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Integer id, Cliente entidad, @HeaderParam("token") String token) {
        try {
            if (validacion.ValidarToken(token)) {
                servicio.editar2(id, entidad);
                return CorsUtil.buildCorsResponseToken(entidad);
            } else {
                return CorsUtil.buildUnauthorizedResponse();
            }
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ClienteFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return CorsUtil.buildNotFoundResponse("Error");
        }
    }

    @OPTIONS
    @Path("/{id}")
    public Response optionsDelete() {
        return CorsUtil.buildCorsResponseToken();
    }

    @DELETE
    @Path("/{id}")
    public Response remove(@PathParam("id") Integer id, @HeaderParam("token") String token) throws IllegalOrphanException, NonexistentEntityException {
        if (validacion.ValidarToken(token)) {
            servicio.LogicDelete(id);
            return CorsUtil.buildCorsResponseToken();
        } else {
            return CorsUtil.buildUnauthorizedResponse();
        }
    }

    @OPTIONS
    @Path("/renovar/{id}")
    public Response optionsRenovar() {
        return CorsUtil.buildCorsResponseToken();
    }

    @DELETE
    @Path("/renovar/{id}")
    public Response renovar(@PathParam("id") Integer id, @HeaderParam("token") String token) throws IllegalOrphanException, NonexistentEntityException {
        if (validacion.ValidarToken(token)) {
            servicio.Renovar(id);
            return CorsUtil.buildCorsResponseToken();
        } else {
            return CorsUtil.buildUnauthorizedResponse();
        }
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Integer id, @HeaderParam("token") String token) throws IllegalOrphanException {
        if (validacion.ValidarToken(token)) {
            return Response.status(Response.Status.OK).entity(servicio.buscar(id)).build(); // Código 200 para solicitud exitosa
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Error: Token no válido").build(); // Código 401 para no autorizado

        }
    }

//-----------------------------------------------------------------------------------------------------------------------------------    
    @OPTIONS
    @Path("imprimirPDF")
    public Response optionsimprimirPDF() {
        return CorsUtil.buildCorsResponseToken();
    }

    @GET
    @Path("imprimirPDF")
    public Response imprimirPDF(@HeaderParam("token") String token) {
        if (validacion.ValidarToken(token)) {
            servicio.imprimirReporte();
            return CorsUtil.buildOkResponse("{\"status\": \"valido\"}");
        } else {
            return CorsUtil.buildUnauthorizedResponse();
        }
    }

    @OPTIONS
    @Path("imprimirPDF2")
    public Response optionsimprimirPDF2() {
        return CorsUtil.buildCorsResponseToken();
    }

    @GET
    @Path("imprimirPDF2")
    public Response imprimirPDF2(@HeaderParam("token") String token) {
        if (validacion.ValidarToken(token)) {
            servicio.imprimirReporte2();
            return CorsUtil.buildOkResponse("{\"status\": \"valido\"}");
        } else {
            return CorsUtil.buildUnauthorizedResponse();
        }
    }

    @OPTIONS
    @Produces(MediaType.APPLICATION_JSON)
    public Response options() {
        return CorsUtil.buildCorsResponseToken();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAll(@HeaderParam("token") String token) {
        try {
            if (validacion.ValidarToken(token)) {
                return CorsUtil.buildCorsResponseToken(servicio.ListaClientes());
            } else {
                return CorsUtil.buildUnauthorizedResponse();
            }
        } catch (Exception e) {
            return CorsUtil.buildCorsResponseError();
        }
    }

    @OPTIONS
    @Path("/findAll2")
    @Produces(MediaType.APPLICATION_JSON)
    public Response optionsEC() {
        return CorsUtil.buildCorsResponseToken();
    }

    @GET
    @Path("/findAll2")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAll2(@HeaderParam("token") String token) {
        try {
            if (validacion.ValidarToken(token)) {
                return CorsUtil.buildCorsResponseToken(servicio.ListaExClientes());
            } else {
                return CorsUtil.buildUnauthorizedResponse();
            }
        } catch (Exception e) {
            return CorsUtil.buildCorsResponseError();
        }
    }

    @GET
    @Path("/{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cliente> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return null;
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
