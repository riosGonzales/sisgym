package jpa;

import Entities.Clases;
import Entities.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author wtke9
 */
public class ClasesJpaController implements Serializable {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_Sis_Gym_war_1.0-SNAPSHOTPU");

    public ClasesJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clases clases) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(clases);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clases clases) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            clases = em.merge(clases);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clases.getIdClases();
                if (findClases(id) == null) {
                    throw new NonexistentEntityException("The clases with id " + id + " no longer exists.");
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
            Clases clases;
            try {
                clases = em.getReference(Clases.class, id);
                clases.getIdClases();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clases with id " + id + " no longer exists.", enfe);
            }
            em.remove(clases);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clases> findClasesEntities() {
        return findClasesEntities(true, -1, -1);
    }

    public List<Clases> findClasesEntities(int maxResults, int firstResult) {
        return findClasesEntities(false, maxResults, firstResult);
    }

    private List<Clases> findClasesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clases.class));
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

    public Clases findClases(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clases.class, id);
        } finally {
            em.close();
        }
    }

    public int getClasesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clases> rt = cq.from(Clases.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Clases> findClasesByFecha(Date fecha) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("Clases.findByFecha");
            query.setParameter("fecha", fecha);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        ClasesJpaController controller = new ClasesJpaController();
        String fechaBusqueda = "2023-11-02";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = sdf.parse(fechaBusqueda);
            List<Clases> clasesList = controller.findClasesByFecha(fecha);          
            for (Clases clases : clasesList) {
                System.out.println(clases.getIdClases());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
