/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Cliente;
import Entities.Matricula;
import Entities.Membresia;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author jano_
 */
public class MatriculaJpaController implements Serializable {

    public MatriculaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
   private  EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_Sis_Gym_war_1.0-SNAPSHOTPU");

    public MatriculaJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Matricula matricula) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente idCliente = matricula.getIdCliente();
            if (idCliente != null) {
                idCliente = em.getReference(idCliente.getClass(), idCliente.getIdCliente());
                matricula.setIdCliente(idCliente);
            }
            Membresia idMembresia = matricula.getIdMembresia();
            if (idMembresia != null) {
                idMembresia = em.getReference(idMembresia.getClass(), idMembresia.getIdMembresia());
                matricula.setIdMembresia(idMembresia);
            }
            em.persist(matricula);
            if (idCliente != null) {
                idCliente.getMatriculaList().add(matricula);
                idCliente = em.merge(idCliente);
            }
            if (idMembresia != null) {
                idMembresia.getMatriculaList().add(matricula);
                idMembresia = em.merge(idMembresia);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Matricula matricula) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Matricula persistentMatricula = em.find(Matricula.class, matricula.getIdMatricula());
            Cliente idClienteOld = persistentMatricula.getIdCliente();
            Cliente idClienteNew = matricula.getIdCliente();
            Membresia idMembresiaOld = persistentMatricula.getIdMembresia();
            Membresia idMembresiaNew = matricula.getIdMembresia();
            if (idClienteNew != null) {
                idClienteNew = em.getReference(idClienteNew.getClass(), idClienteNew.getIdCliente());
                matricula.setIdCliente(idClienteNew);
            }
            if (idMembresiaNew != null) {
                idMembresiaNew = em.getReference(idMembresiaNew.getClass(), idMembresiaNew.getIdMembresia());
                matricula.setIdMembresia(idMembresiaNew);
            }
            matricula = em.merge(matricula);
            if (idClienteOld != null && !idClienteOld.equals(idClienteNew)) {
                idClienteOld.getMatriculaList().remove(matricula);
                idClienteOld = em.merge(idClienteOld);
            }
            if (idClienteNew != null && !idClienteNew.equals(idClienteOld)) {
                idClienteNew.getMatriculaList().add(matricula);
                idClienteNew = em.merge(idClienteNew);
            }
            if (idMembresiaOld != null && !idMembresiaOld.equals(idMembresiaNew)) {
                idMembresiaOld.getMatriculaList().remove(matricula);
                idMembresiaOld = em.merge(idMembresiaOld);
            }
            if (idMembresiaNew != null && !idMembresiaNew.equals(idMembresiaOld)) {
                idMembresiaNew.getMatriculaList().add(matricula);
                idMembresiaNew = em.merge(idMembresiaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = matricula.getIdMatricula();
                if (findMatricula(id) == null) {
                    throw new NonexistentEntityException("The matricula with id " + id + " no longer exists.");
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
            Matricula matricula;
            try {
                matricula = em.getReference(Matricula.class, id);
                matricula.getIdMatricula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The matricula with id " + id + " no longer exists.", enfe);
            }
            Cliente idCliente = matricula.getIdCliente();
            if (idCliente != null) {
                idCliente.getMatriculaList().remove(matricula);
                idCliente = em.merge(idCliente);
            }
            Membresia idMembresia = matricula.getIdMembresia();
            if (idMembresia != null) {
                idMembresia.getMatriculaList().remove(matricula);
                idMembresia = em.merge(idMembresia);
            }
            em.remove(matricula);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Matricula> findMatriculaEntities() {
        return findMatriculaEntities(true, -1, -1);
    }

    public List<Matricula> findMatriculaEntities(int maxResults, int firstResult) {
        return findMatriculaEntities(false, maxResults, firstResult);
    }

    private List<Matricula> findMatriculaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Matricula.class));
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

    public Matricula findMatricula(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Matricula.class, id);
        } finally {
            em.close();
        }
    }
public List<Object[]> countMembresias() {
    EntityManager em = getEntityManager();
    try {
        Query query = em.createNamedQuery("Matricula.countByMembresia");
        return query.getResultList();
    } finally {
        em.close();
    }
}

    
    public int getMatriculaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Matricula> rt = cq.from(Matricula.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
        public static void main(String[] args) {
        MatriculaJpaController controller = new MatriculaJpaController();
        List<Object[]> result = controller.countMembresias();
        for (Object[] row : result) {
            String nombreMembresia = Objects.toString(row[0], "N/A");
            long cantidad = (long) row[1];
            System.out.println(nombreMembresia + ": " + cantidad);
        }
    }
}
