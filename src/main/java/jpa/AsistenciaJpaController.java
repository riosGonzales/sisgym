/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpa;

import Entities.Asistencia;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Cliente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author Usuario
 */
public class AsistenciaJpaController implements Serializable {

    public AsistenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_Sis_Gym_war_1.0-SNAPSHOTPU");

    public AsistenciaJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asistencia asistencia) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente clienteidCliente = asistencia.getClienteidCliente();
            if (clienteidCliente != null) {
                clienteidCliente = em.getReference(clienteidCliente.getClass(), clienteidCliente.getIdCliente());
                asistencia.setClienteidCliente(clienteidCliente);
            }
            em.persist(asistencia);
            if (clienteidCliente != null) {
                clienteidCliente.getAsistenciaList().add(asistencia);
                clienteidCliente = em.merge(clienteidCliente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asistencia asistencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asistencia persistentAsistencia = em.find(Asistencia.class, asistencia.getIdAsistencia());
            Cliente clienteidClienteOld = persistentAsistencia.getClienteidCliente();
            Cliente clienteidClienteNew = asistencia.getClienteidCliente();
            if (clienteidClienteNew != null) {
                clienteidClienteNew = em.getReference(clienteidClienteNew.getClass(), clienteidClienteNew.getIdCliente());
                asistencia.setClienteidCliente(clienteidClienteNew);
            }
            asistencia = em.merge(asistencia);
            if (clienteidClienteOld != null && !clienteidClienteOld.equals(clienteidClienteNew)) {
                clienteidClienteOld.getAsistenciaList().remove(asistencia);
                clienteidClienteOld = em.merge(clienteidClienteOld);
            }
            if (clienteidClienteNew != null && !clienteidClienteNew.equals(clienteidClienteOld)) {
                clienteidClienteNew.getAsistenciaList().add(asistencia);
                clienteidClienteNew = em.merge(clienteidClienteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asistencia.getIdAsistencia();
                if (findAsistencia(id) == null) {
                    throw new NonexistentEntityException("The asistencia with id " + id + " no longer exists.");
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
            Asistencia asistencia;
            try {
                asistencia = em.getReference(Asistencia.class, id);
                asistencia.getIdAsistencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asistencia with id " + id + " no longer exists.", enfe);
            }
            Cliente clienteidCliente = asistencia.getClienteidCliente();
            if (clienteidCliente != null) {
                clienteidCliente.getAsistenciaList().remove(asistencia);
                clienteidCliente = em.merge(clienteidCliente);
            }
            em.remove(asistencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asistencia> findAsistenciaEntities() {
        return findAsistenciaEntities(true, -1, -1);
    }

    public List<Asistencia> findAsistenciaEntities(int maxResults, int firstResult) {
        return findAsistenciaEntities(false, maxResults, firstResult);
    }

    private List<Asistencia> findAsistenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asistencia.class));
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

    public Asistencia findAsistencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asistencia.class, id);
        } finally {
            em.close();
        }
    }

    public List<Object[]> countAsistenciasPorDia() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("Asistencia.countAsistenciasPorDia");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

  public int getAsistenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asistencia> rt = cq.from(Asistencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        AsistenciaJpaController controller = new AsistenciaJpaController();

        try {
            List<Object[]> result = controller.countAsistenciasPorDia();

            System.out.println("Resultados de countAsistenciasPorDia:");
            for (Object[] row : result) {
                System.out.println("Fecha: " + row[0] + ", Asistencias: " + row[1]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    } 
}
