package dao.service;

import authenti.Autenticar;
import Entities.Usuario;
import Service.ValidacionService;
import chat.ClaveGeneratorTXT;
import chat.node;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.ws.rs.*;
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
    private String valorFijoClave;// Obtener el valor fijo una sola vez
    private ClaveGeneratorTXT claveGeneratorTXT = new ClaveGeneratorTXT();
    node server = new node();
    node client = new node();
    static node bob =new node();

    public UsuarioFacadeREST() {
        super(Usuario.class);
        ClaveGeneratorTXT.borrarContenido();

    }

    public static String obtenerClaveServidor() {
        System.out.println("Retornandno clave publica de bob...");
        return bob.getClavePublicaString();
    }
    
    @GET
    @Path("/obtenerClaveBob")
    @Produces({MediaType.APPLICATION_JSON})
    public String obtenerClaveBob() {
        String resultado = obtenerClaveServidor();
        System.out.println("Resultado clave publica BOB: " + resultado);
        String json = "{\"resultado\": \"" + resultado + "\"}";
        System.out.println("RESULTADO JSON: "+json);
        return json;
    }
    

    

    private String obtenerValorFijoClave(String AliceClaveJS) {

        //server.setClavePublicaReceptor(client.getClavePublica());
        //client.setClavePublicaReceptor(server.getClavePublica());
        ClaveGeneratorTXT.generarYGuardarClave(AliceClaveJS);
        return ClaveGeneratorTXT.recuperarClave();
    }

    public String obtenerClavee(String AliceClaveJS) {
        obtenerValorFijoClave(AliceClaveJS);
        valorFijoClave = ClaveGeneratorTXT.recuperarClave();
        return valorFijoClave;
    }

    @POST
    @Path("/obtenerClave")
    @Produces({MediaType.APPLICATION_JSON})
    public String obtenerClave(@FormParam("AliceClaveJS") String AliceClaveJS) {
        return obtenerClavee(AliceClaveJS);
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

    @PUT
    @Path("asociarLlave")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String asociarLlave(
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
                    return "{\"resultado\":\"valido\"}";
                } else {
                    return "{\"resultado\":\"QR ya existente\"}";
                }
            } else {
                return "{\"resultado\":\"no_valido\"}";
            }
        } catch (NoResultException e) {
            return "{\"resultado\":\"no_valido\"}";
        } finally {
            em.close();
        }
    }

//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @PUT
    @Path("autenticarCodigo")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String autenticarCodigo(
            @FormParam("logi") String logi,
            @FormParam("pass") String pass,
            @FormParam("codigo") String codigo
    ) throws NonexistentEntityException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Usuario usuario = em.createNamedQuery("Usuario.validar", Usuario.class)
                    .setParameter("logiUsua", logi)
                    .setParameter("passUsua", pass)
                    .getSingleResult();
            if (usuario != null) {
                boolean autenticarCodigo = Autenticar.autenticarCodigo(usuario, codigo);
                if (autenticarCodigo) {
                    return "{\"resultado\":\"valido\"}";
                } else {
                    return "{\"resultado\":\"no_valido\"}";
                }
            } else {
                return "{\"resultado\":\"no_valido\"}";
            }
        } catch (NoResultException e) {
            return "{\"resultado\":\"no_valido\"}";
        } finally {
            em.close();
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
    public String validarUsuario(
            @FormParam("logiUsua") String logiUsua,
            @FormParam("passUsua") String passUsua,
            @FormParam("fechUsua") String fechUsua
    ) {

        return jpaUsuario.validarUsuario(logiUsua, passUsua, fechUsua);

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
