/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpa;

import Entities.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa.exceptions.NonexistentEntityException;
import security.AES;
import security.JwtHelper;

/**
 *
 * @author Usuario
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController() {
    }

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_Sis_Gym_war_1.0-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            usuario = em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getCodiUsua();
                if (findUsuario(id) == null) {
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

    public void destroy(Integer id) throws NonexistentEntityException {
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

    public String validarUsuario(String logiUsua, String passUsua, String fecha) {
        EntityManager em = getEntityManager();
        try {
            Usuario usuario = em.createNamedQuery("Usuario.validar", Usuario.class)
                    .setParameter("logiUsua", logiUsua)
                    .setParameter("passUsua", passUsua)
                    .getSingleResult();

            if (usuario != null) {
                String UsuarioYFecha = logiUsua + fecha;

                String token = JwtHelper.generateToken(UsuarioYFecha);

                //String token = AES.encrypt(UsuarioYFecha, "lafedecuto");
                System.out.println(token);
                int idEmpleado = usuario.getEmpleadoidEmpleado().getIdEmpleado();
                int  codiUsua = usuario.getCodiUsua();
                String tipoUsua = usuario.getTipoUsuario();
               
            String jsonResultado = "{\"resultado\":\"valido\", \"token\":\"" + token +
                    "\", \"idEmpleado\":\"" + idEmpleado +
                    "\", \"codiUsua\":\"" + codiUsua + "\", \"logiUsua\":\"" +
                    logiUsua + "\", \"tipoUsua\":\"" + tipoUsua + "\"}";
            return jsonResultado;
            } else {
                return "{\"resultado\":\"no_valido_usuario_null\"}";
            }
        } catch (NoResultException e) {
            e.printStackTrace();

            return "{\"resultado\":\"no_valido_trycatch\"}";
        } finally {
            em.close();
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------------
    public String cambiarPass(String logi, String pass, String newpass) throws Exception {

        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Usuario usua = em.createNamedQuery("Usuario.validar", Usuario.class)
                    .setParameter("logiUsua", logi)
                    .setParameter("passUsua", pass)
                    .getSingleResult();

            if (usua != null) {
                usua.setPassUsua(newpass);
                edit(usua);
                //em.getTransaction().commit();
                return "{\"resultado\":\"valido\"}";
            } else {
                return "{\"resultado\":\"no_valido\"}";
            }
        } catch (NoResultException e) {
            return "{\"resultado\":\"nooooUnU\"}";
        } finally {
            em.close();
        }

    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        try {
            UsuarioJpaController usujpa = new UsuarioJpaController();
            String aa = usujpa.validarUsuario("tUki", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "2023-10-13");
            System.out.println(aa);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
