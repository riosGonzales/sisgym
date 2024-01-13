package dao.service;

import authenti.Autenticar;
import Entities.Usuario;
import Service.ClaveCompartidaSingleton;
import Service.CorsUtil;
import Service.ValidacionService;
import Session.Sesion;
import chat.node;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import jpa.UsuarioJpaController;
import jpa.exceptions.NonexistentEntityException;

@Stateless
@Path("dto.usuario")
public class UsuarioFacadeREST extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "com.mycompany_Sis_Gym_war_1.0-SNAPSHOTPU")
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_Sis_Gym_war_1.0-SNAPSHOTPU");
    UsuarioJpaController jpaUsuario = new UsuarioJpaController();
    ValidacionService vService = new ValidacionService();
    static node bob = new node();

    public UsuarioFacadeREST() {
        super(Usuario.class);
//        System.out.println("antes");
//        System.out.println("despues");
    }

// RETORNA LA CLAVE PÚBLICA DEL SERVIDOR :D
    public static String obtenerClaveServidor() {
        System.out.println("Retornandno clave publica de bob...");
        return bob.getClavePublicaString();
    }

    //REST DEL DE ARRIBA :o
    @GET
    @Path("/obtenerClaveBob")
    @Produces({MediaType.APPLICATION_JSON})
    public String obtenerClaveBob() {
        String resultado = obtenerClaveServidor();
        System.out.println("Resultado clave publica BOB: " + resultado);
        String json = "{\"resultado\": \"" + resultado + "\"}";
        System.out.println("RESULTADO JSON: " + json);
        return json;
    }

    //REST para los anteriores 2 métodos
//    @POST
//    @Path("/obtenerClave")
//    @Produces({MediaType.APPLICATION_JSON})
//    public String obtenerClave(@FormParam("AliceClaveJS") String AliceClaveJS) {
//        return obtenerClavee(AliceClaveJS);
//    }
    @POST
    @Path("/obtenerClave")
    @Produces({MediaType.APPLICATION_JSON})
    public String obtenerClaveREST(@FormParam("AliceClaveJS") String AliceClaveJS) {
        ClaveCompartidaSingleton singleton = ClaveCompartidaSingleton.getInstance();
        String claveCompartida = singleton.getClaveCompartida();
        String rpta;
        // Verificar si la clave ya existe
        if (!claveCompartida.isEmpty()) {
            System.out.println("Singleton no vacio: " + claveCompartida);
            rpta = "{\"resultado\": \"" + claveCompartida + "\"}";
            return rpta; // Devuelve la clave existente sin generar una nueva
        }
        System.out.println("Singleton  vacio");

        // Generar una nueva clave si no existe
        System.out.println("Antes de:");
        String nuevaClave = AliceClaveJS; // Método para generar la nueva clave
        System.out.println("Clave de alice compartida.: " + nuevaClave);
        singleton.setClaveCompartida(nuevaClave); // Guarda la nueva clave en el Singleton
        //  System.out.println(nuevaClave);
        rpta = "{\"resultado\": \"" + nuevaClave + "\"}";
        return rpta;
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Usuario entity) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Usuario entity) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            entity = em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                id = entity.getCodiUsua();
                if (find(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getCodiUsua();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @OPTIONS
    @Path("asociarLlave")
    public Response handleOptionsAsociarLlave() {
        return CorsUtil.buildCORSResponse("valido");
    }

    @PUT
    @Path("asociarLlave")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response asociarLlave(
            @FormParam("logi") String logi,
            @FormParam("pass") String pass
    ) throws NonexistentEntityException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Usuario usuario = em.createNamedQuery("Usuario.validar", Usuario.class)
                    .setParameter("logiUsua", logi)
                    .setParameter("passUsua", pass)
                    .getSingleResult();
            if (usuario != null) {
                if (usuario.getToknUsua().equals("vacio")) {
                    String autenticado = Autenticar.asociarLlave(usuario);
                    usuario.setToknUsua(autenticado);
                    edit(usuario.getCodiUsua(), usuario);
                    return CorsUtil.buildCORSResponse("{\"resultado\":\"valido\"}");
                } else {
                    return CorsUtil.buildCORSResponse("{\"resultado\":\"no_valido\"}");                    
                }
            } else {
                    return CorsUtil.buildCORSResponse("{\"resultado\":\"no_valido\"}");                    
            }
        } catch (NoResultException e) {
                    return CorsUtil.buildCORSResponse("{\"resultado\":\"no_valido\"}");                    
        } finally {
            em.close();
        }
    }

//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @OPTIONS
    @Path("autenticarCodigo")
    public Response handleOptionsAutenticarCodigo() {
        return CorsUtil.buildCORSResponse("valido");
    }

    @PUT
    @Path("autenticarCodigo")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response autenticarCodigo(@Context HttpServletRequest request,
            @FormParam("logi") String logi, @FormParam("pass") String pass,
            @FormParam("codigo") String codigo) throws NonexistentEntityException {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            Usuario usuario = em.createNamedQuery("Usuario.validar", Usuario.class)
                    .setParameter("logiUsua", logi)
                    .setParameter("passUsua", pass)
                    .getSingleResult();

            if (usuario != null) {
                boolean autenticarCodigo = Autenticar.autenticarCodigo(usuario, codigo);

                if (autenticarCodigo) {
                    return CorsUtil.buildCORSResponse("{\"resultado\":\"valido\"}");
                } else {
                    return CorsUtil.buildCORSResponse("{\"resultado\":\"no_valido\"}");                    
                }
            } else {
                    return CorsUtil.buildCORSResponse("{\"resultado\":\"no_valido\"}");                    
            }
        } catch (NoResultException e) {
                    return CorsUtil.buildCORSResponse("{\"resultado\":\"no_valido\"}");                    
        }
    }

//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Usuario find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usuario> findAll() {
        return super.findAll();
    }

    //------------------------------------------------------------------------------------------------------------------------------
    @POST
    @Path("validarUsuario")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response validarUsuario(
            @Context HttpServletRequest request,
            @FormParam("logiUsua") String logiUsua,
            @FormParam("passUsua") String passUsua,
            @FormParam("fechUsua") String fechUsua
    ) {
        try {
            String rpta = jpaUsuario.validarUsuario(logiUsua, passUsua, fechUsua);
            JsonObject jsonObject = new Gson().fromJson(rpta, JsonObject.class);
            String tipoUsuaValue = jsonObject.get("tipoUsua").getAsString();
            Sesion.crearSesion(request.getSession(), tipoUsuaValue);

            // Retornar la respuesta directamente con CORS
                    return CorsUtil.buildCORSResponse(rpta);
        } catch (Exception e) {
            // Manejar el error y retornar una respuesta CORS
                    return CorsUtil.buildCORSResponse("{\"resultado\":\"no_valido\"}");                    
        }
    }

    //-----------------------------------------------------------------------------------------
    @PUT
    @Path("cambiarPass")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response cambiarPass(
            @FormParam("logi") String logi,
            @FormParam("pass") String pass,
            @FormParam("newpass") String newpass,
            @HeaderParam("token") String token
    ) throws NonexistentEntityException, UnsupportedEncodingException, Exception {
        {
            if (vService.ValidarToken(token)) {
                return Response.status(Response.Status.OK).entity(jpaUsuario.cambiarPass(logi, pass, newpass)).build(); // Código 200 para solicitud exitosa

            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Error: Token no válido").build(); // Código 401 para no autorizado

            }
        }
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usuario> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to
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
        return emf.createEntityManager();
    }

}
