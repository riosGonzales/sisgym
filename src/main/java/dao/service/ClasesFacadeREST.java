package dao.service;

import Entities.Clases;
import Service.ClaseService;
import Service.ValidacionService;
import com.google.gson.Gson;
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
import jpa.ClasesJpaController;

/**
 *
 * @author wtke9
 */
@Stateless
@Path("entities.clases")
public class ClasesFacadeREST extends AbstractFacade<Clases> {

    @PersistenceContext(unitName = "com.mycompany_Sis_Gym_war_1.0-SNAPSHOTPU")
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_Sis_Gym_war_1.0-SNAPSHOTPU");
    ClasesJpaController jpaClases = new ClasesJpaController();
    ClaseService cs = new ClaseService();
    ValidacionService vs = new ValidacionService();

    public ClasesFacadeREST() {
        super(Clases.class);
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response crear(Clases entity, @HeaderParam("token") String token, @HeaderParam("usu") String usu) {
        if (vs.ValidarToken(usu, token)) {
            cs.crearRapido( entity);
            return Response.status(Response.Status.OK).build(); // Código 200 para solicitud exitosa

        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Error: Token no válido").build(); // Código 401 para no autorizado

        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Clases entity) {
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
    public Clases find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("json")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response listar(@HeaderParam("token") String token, @HeaderParam("usu") String usu) {
        if (token != null && usu != null) {
            if (vs.ValidarToken(usu, token)) {
                return Response.status(Response.Status.OK).entity(cs.getJSon()).build(); // Código 200 para solicitud exitosa
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
    public List<Clases> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
        return emf.createEntityManager();
    }

}
