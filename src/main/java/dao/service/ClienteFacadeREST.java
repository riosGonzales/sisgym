package dao.service;

import Entities.Cliente;
import Service.ClienteService;
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

/**
 *
 * @author Usuario
 */
@Stateless
@Path("entities.cliente")
public class ClienteFacadeREST extends AbstractFacade<Cliente> {

//    @PersistenceContext(unitName = "com.mycompany_Sis_Gym_war_1.0-SNAPSHOTPU")
//    private EntityManager em;
    ClienteService servicio = new ClienteService();
    ValidacionService validacion = new ValidacionService();

    public ClienteFacadeREST() {
        super(Cliente.class);
    }

//@POST
//@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//public void create(ClienteDTO clienteDTO, @HeaderParam("token") String token, @HeaderParam("usu") String usu) {
//    if (validacion.ValidarToken(usu, token)) {
//        servicio.crear(clienteDTO);
//    }
//}
    @OPTIONS
    public Response optionsCreate() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "http://localhost:4200")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, token")
                .header("Access-Control-Allow-Credentials", "true")
                .build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Cliente entidad, @HeaderParam("token") String token) {
        if (validacion.ValidarToken(token)) {
            int codigo = servicio.crear(entidad);
            return Response
                    .status(Response.Status.OK)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, token")
                    .header("Access-Control-Allow-Credentials", "true")
                    .entity(codigo).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Error: Token no válido").build(); // Código 401 para no autorizado
        }
    }

//    @PUT
//    @Path("/{id}")
//    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public void edit(@PathParam("id") Integer id, ClienteDTO clienteDTO) {
//        try {
//            clienteDTO.setId(id);
//            servicio.editar(clienteDTO);
//        } catch (NonexistentEntityException ex) {
//            Logger.getLogger(ClienteFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    
    
    @PUT
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Integer id, Cliente entidad, @HeaderParam("token") String token) {
        try {
            if (validacion.ValidarToken(token)) {
                servicio.editar2(id, entidad);
                return Response
                        .status(Response.Status.OK)
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, token")
                        .header("Access-Control-Allow-Credentials", "true")
                        .entity(entidad).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Error: Token no válido").build(); // Código 401 para no autorizado

            }
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ClienteFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.EXPECTATION_FAILED).entity("Error: Token no válido").build(); // Código 401 para no autorizado

        }
    }

    @OPTIONS
    @Path("/{id}")
    public Response optionsDelete() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "http://localhost:4200")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, token")
                .header("Access-Control-Allow-Credentials", "true")
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response remove(@PathParam("id") Integer id, @HeaderParam("token") String token) throws IllegalOrphanException, NonexistentEntityException {
        if (validacion.ValidarToken(token)) {
            servicio.LogicDelete(id);
            return Response
                    .status(Response.Status.OK)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, token")
                    .header("Access-Control-Allow-Credentials", "true")
                    .build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Error: Token no válido").build(); // Código 401 para no autorizado
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
        return Response.ok()
                .header("Access-Control-Allow-Origin", "http://localhost:4200")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, token")
                .header("Access-Control-Allow-Credentials", "true")
                .build();
    }

    @GET
    @Path("imprimirPDF")
    public Response imprimirPDF(@HeaderParam("token") String token) {
        if (validacion.ValidarToken(token)) {
            servicio.imprimirReporte();
            return Response
                    .status(Response.Status.OK)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, token")
                    .header("Access-Control-Allow-Credentials", "true")
                    .entity("{\"status\": \"valido\"}")
                    .build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Error: Token no válido").build(); // Código 401 para no autorizad
        }
    }

//    @GET
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public List<Cliente> findAll(@HeaderParam("token") String token, @HeaderParam("usu") String usu) {
//        if (validacion.ValidarToken(usu, token)) {
//            return servicio.ListaClientes();
//
//        } else {
//            return null;
//        }
//    }
    private Response buildCORSResponse(String jsonResponse) {
        return Response.ok(jsonResponse)
                .header("Access-Control-Allow-Origin", "http://localhost:4200")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, token")
                .header("Access-Control-Allow-Credentials", "true")
                .build();
    }

    @OPTIONS
    @Produces(MediaType.APPLICATION_JSON)
    public Response options() {
        return buildCORSResponse("");
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAll(@HeaderParam("token") String token) {
        try {
            if (validacion.ValidarToken(token)) {
                return Response
                        .status(Response.Status.OK)
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, token")
                        .header("Access-Control-Allow-Credentials", "true")
                        .entity(servicio.ListaClientes())
                        .build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Error de autenticación: Token inválido").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el servidor").build();
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
