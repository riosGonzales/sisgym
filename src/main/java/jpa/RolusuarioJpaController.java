package jpa;

import Entities.Cliente;
import Entities.Rolusuario;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa.exceptions.NonexistentEntityException;
import jpa.exceptions.PreexistingEntityException;

public class RolusuarioJpaController implements Serializable {

    public RolusuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_Sis_Gym_war_1.0-SNAPSHOTPU");

    public RolusuarioJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rolusuario rolusuario) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(rolusuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRolusuario(rolusuario.getCodRol()) != null) {
                throw new PreexistingEntityException("Rolusuario " + rolusuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rolusuario rolusuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            rolusuario = em.merge(rolusuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rolusuario.getCodRol();
                if (findRolusuario(id) == null) {
                    throw new NonexistentEntityException("The rolusuario with id " + id + " no longer exists.");
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
            Rolusuario rolusuario;
            try {
                rolusuario = em.getReference(Rolusuario.class, id);
                rolusuario.getCodRol();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rolusuario with id " + id + " no longer exists.", enfe);
            }
            em.remove(rolusuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rolusuario> findRolusuarioEntities() {
        return findRolusuarioEntities(true, -1, -1);
    }

    public List<Rolusuario> findRolusuarioEntities(int maxResults, int firstResult) {
        return findRolusuarioEntities(false, maxResults, firstResult);
    }

    private List<Rolusuario> findRolusuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rolusuario.class));
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

    public Rolusuario findRolusuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rolusuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolusuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rolusuario> rt = cq.from(Rolusuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Rolusuario> findClientesByEstado(boolean estado) {
        TypedQuery<Rolusuario> query = getEntityManager().createNamedQuery("Rolusuario.findByEstado", Rolusuario.class);
        query.setParameter("actvRol", estado);
        return query.getResultList();
    }

    public static void main(String[] args) throws Exception {
        RolusuarioJpaController objRol = new RolusuarioJpaController();
        Rolusuario entidad = new Rolusuario();
        entidad.setCodRol(2);
        entidad.setCodiUsua(2);
        entidad.setActvRol(false);
        objRol.create(entidad);
    }
}
