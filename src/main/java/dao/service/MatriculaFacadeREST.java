package dao.service;

import Entities.Matricula;
import Service.CorsUtil;
import Service.MatriculaService;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import jpa.exceptions.IllegalOrphanException;

@Stateless
@Path("entities.matricula")
public class MatriculaFacadeREST extends AbstractFacade<Matricula> {

    MatriculaService matriculaservice = new MatriculaService();

    public MatriculaFacadeREST() {
        super(Matricula.class);
    }

    @OPTIONS
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response optionsCrear() {
        return CorsUtil.buildCorsResponse();
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response crear(Matricula entity) {
        int codigo = 0;
        try {
            codigo = matriculaservice.crear(entity);
            return CorsUtil.buildCorsResponse(codigo);
        } catch (Exception e) {
            e.printStackTrace();
            return CorsUtil.buildCorsResponseError();
        }
    }

    @OPTIONS
    @Path("contar-mb")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response optionsCount() {
        return CorsUtil.buildCorsResponse();
    }

    @GET
    @Path("contar-mb")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response countMembresias() {
        try {
            List<Map<String, Object>> contador = matriculaservice.countMembresias();
            return CorsUtil.buildCorsResponse(contador);
        } catch (Exception e) {
            return CorsUtil.buildCorsResponseError();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Matricula entity) throws Exception {
        matriculaservice.editar(id, entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) throws IllegalOrphanException {
        matriculaservice.eliminar(id);
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Matricula find(@PathParam("id") Integer id) {
        return matriculaservice.buscar(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Matricula> findAll() {
        return matriculaservice.listar();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Matricula> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
