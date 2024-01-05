package dao.service.service;

import Entities.Prueba;
import Entities.Rol;
import Service.PruebaService;
import Service.RolService;
import Service.ValidacionService;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import jpa.RolJpaController;
import jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author wtke9
 */
@Stateless
@Path("entities.rol")
public class RolFacadeREST extends AbstractFacade<Rol> {

    @PersistenceContext(unitName = "com.mycompany_Sis_Gym_war_1.0-SNAPSHOTPU")
    private EntityManagerFactory em = Persistence.createEntityManagerFactory("com.mycompany_Sis_Gym_war_1.0-SNAPSHOTPU");
    ValidacionService vs = new ValidacionService();
    RolService pruebaService = new RolService();
    RolJpaController objPrueba = new RolJpaController();

    public RolFacadeREST() {
        super(Rol.class);
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response crear(Rol entity, @HeaderParam("token") String token) throws Exception {
        if (vs.ValidarToken(token)) {
            objPrueba.create(entity);
            return Response.status(Response.Status.OK).build(); // Código 200 para solicitud exitosa
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Error: Token no válido").build(); // Código 401 para no autorizado
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Integer id, Rol entity, @HeaderParam("token") String token) throws Exception {
        if (vs.ValidarToken(token)) {
            objPrueba.edit(entity);
            return Response.status(Response.Status.OK).build(); // Código 200 para solicitud exitosa
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Error: Token no válido").build(); // Código 401 para no autorizado
        }
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Integer id, @HeaderParam("token") String token) throws NonexistentEntityException {
        if (vs.ValidarToken(token)) {
            objPrueba.destroy(id);
            return Response.status(Response.Status.OK).build(); // Código 200 para solicitud exitosa
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Error: Token no válido").build(); // Código 401 para no autorizado
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Rol find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Rol> findAll() {
        return super.findAll();
    }

    @GET
    @Path("json")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response listar(@HeaderParam("token") String token) {
        if (token != null) {
            if (vs.ValidarToken(token)) {
                return Response.status(Response.Status.OK).entity(pruebaService.getJSon()).build(); // Código 200 para solicitud exitosa
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Error: Token no válido").build(); // Código 401 para no autorizado
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error: Token no reconocido").build(); // Código 401 para no autorizado
        }
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Rol> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
        return em.createEntityManager();
    }

}
