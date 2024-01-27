package dao.service;

import Entities.Clases;
import Service.ClaseService;
import Service.CorsUtil;
import Service.ValidacionService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.ws.rs.OPTIONS;
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

    @OPTIONS
    @Produces(MediaType.APPLICATION_JSON)
    public Response optionsCrear() {
        return CorsUtil.buildCorsResponseToken();
    }
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response crear(Clases entity, @HeaderParam("token") String token) {
        if (vs.ValidarToken(token)) {
            cs.crearRapido(entity);
            return CorsUtil.buildCorsResponseToken();
        } else {
            return CorsUtil.buildUnauthorizedResponse();
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

    @OPTIONS
    @Path("json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response options() {
        return CorsUtil.buildCorsResponseToken();
    }

    @GET
    @Path("json")
    @Produces({MediaType.APPLICATION_JSON})
    public Response listar(@HeaderParam("token") String token) {
        try {
            if (vs.ValidarToken(token)) {
                return CorsUtil.buildCorsResponseToken(cs.getJSon());
            } else {
                return CorsUtil.buildUnauthorizedResponse();
            }
        } catch (Exception e) {
            return CorsUtil.buildCorsResponseError();
        }
    }

    @OPTIONS
    @Path("buscarFecha/{fecha}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response optionsBuscarFecha() {
        return CorsUtil.buildCorsResponseToken();
    }

    @GET
    @Path("buscarFecha/{fecha}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response buscarFecha(@HeaderParam("token") String token, @PathParam("fecha") String fechaString) {
        try {
            if (vs.ValidarToken(token)) {
                String fechaBusqueda = fechaString;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fecha = sdf.parse(fechaBusqueda);
                return CorsUtil.buildCorsResponseToken(cs.buscarFecha(fecha));
            } else {
                return CorsUtil.buildUnauthorizedResponse();
            }
        } catch (ParseException e) {
            return CorsUtil.buildCorsResponseError();
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
