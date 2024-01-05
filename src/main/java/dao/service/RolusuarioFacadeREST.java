package dao.service;

import Entities.Rol;
import Entities.Rolusuario;
import Service.RolUsuService;
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
import jpa.RolusuarioJpaController;

/**
 *
 * @author wtke9
 */
@Stateless
@Path("entities.rolusuario")
public class RolusuarioFacadeREST extends AbstractFacade<Rolusuario> {

    @PersistenceContext(unitName = "com.mycompany_Sis_Gym_war_1.0-SNAPSHOTPU")
    private EntityManagerFactory em = Persistence.createEntityManagerFactory("com.mycompany_Sis_Gym_war_1.0-SNAPSHOTPU");
    ValidacionService vs = new ValidacionService();
    RolUsuService servicio = new RolUsuService();
    RolusuarioJpaController objRolUsu = new RolusuarioJpaController();

    public RolusuarioFacadeREST() {
        super(Rolusuario.class);
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response crear(Rolusuario entity, @HeaderParam("token") String token) throws Exception {
        if (vs.ValidarToken(token)) {
            objRolUsu.create(entity);
            return Response.status(Response.Status.OK).build(); // Código 200 para solicitud exitosa
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Error: Token no válido").build(); // Código 401 para no autorizado
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Rolusuario entity) {
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
    public Rolusuario find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Rolusuario> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Rolusuario> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("imprimirPDF")
    public Response imprimirPDF(@HeaderParam("token") String token) {
        if (vs.ValidarToken(token)) {
            servicio.imprimirReporte();
            return Response.status(Response.Status.OK).entity("Reporte Generado Exitosamente").build(); // Código 200 para solicitud exitosa
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Error: Token no válido").build(); // Código 401 para no autorizado
        }
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
