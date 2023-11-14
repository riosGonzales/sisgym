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
import Entities.Matricula;
import Entities.Membresia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.exceptions.IllegalOrphanException;
import jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author Usuario
 */
public class MembresiaJpaController implements Serializable {

    public MembresiaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public MembresiaJpaController() {
    }
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_Sis_Gym_war_1.0-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Membresia membresia) {
        if (membresia.getMatriculaList() == null) {
            membresia.setMatriculaList(new ArrayList<Matricula>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Matricula> attachedMatriculaList = new ArrayList<Matricula>();
            for (Matricula matriculaListMatriculaToAttach : membresia.getMatriculaList()) {
                matriculaListMatriculaToAttach = em.getReference(matriculaListMatriculaToAttach.getClass(), matriculaListMatriculaToAttach.getIdMatricula());
                attachedMatriculaList.add(matriculaListMatriculaToAttach);
            }
            membresia.setMatriculaList(attachedMatriculaList);
            em.persist(membresia);
            for (Matricula matriculaListMatricula : membresia.getMatriculaList()) {
                Membresia oldIdMembresiaOfMatriculaListMatricula = matriculaListMatricula.getIdMembresia();
                matriculaListMatricula.setIdMembresia(membresia);
                matriculaListMatricula = em.merge(matriculaListMatricula);
                if (oldIdMembresiaOfMatriculaListMatricula != null) {
                    oldIdMembresiaOfMatriculaListMatricula.getMatriculaList().remove(matriculaListMatricula);
                    oldIdMembresiaOfMatriculaListMatricula = em.merge(oldIdMembresiaOfMatriculaListMatricula);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Membresia membresia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Membresia persistentMembresia = em.find(Membresia.class, membresia.getIdMembresia());
            List<Matricula> matriculaListOld = persistentMembresia.getMatriculaList();
            List<Matricula> matriculaListNew = membresia.getMatriculaList();
            List<String> illegalOrphanMessages = null;
            for (Matricula matriculaListOldMatricula : matriculaListOld) {
                if (!matriculaListNew.contains(matriculaListOldMatricula)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Matricula " + matriculaListOldMatricula + " since its idMembresia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Matricula> attachedMatriculaListNew = new ArrayList<Matricula>();
            for (Matricula matriculaListNewMatriculaToAttach : matriculaListNew) {
                matriculaListNewMatriculaToAttach = em.getReference(matriculaListNewMatriculaToAttach.getClass(), matriculaListNewMatriculaToAttach.getIdMatricula());
                attachedMatriculaListNew.add(matriculaListNewMatriculaToAttach);
            }
            matriculaListNew = attachedMatriculaListNew;
            membresia.setMatriculaList(matriculaListNew);
            membresia = em.merge(membresia);
            for (Matricula matriculaListNewMatricula : matriculaListNew) {
                if (!matriculaListOld.contains(matriculaListNewMatricula)) {
                    Membresia oldIdMembresiaOfMatriculaListNewMatricula = matriculaListNewMatricula.getIdMembresia();
                    matriculaListNewMatricula.setIdMembresia(membresia);
                    matriculaListNewMatricula = em.merge(matriculaListNewMatricula);
                    if (oldIdMembresiaOfMatriculaListNewMatricula != null && !oldIdMembresiaOfMatriculaListNewMatricula.equals(membresia)) {
                        oldIdMembresiaOfMatriculaListNewMatricula.getMatriculaList().remove(matriculaListNewMatricula);
                        oldIdMembresiaOfMatriculaListNewMatricula = em.merge(oldIdMembresiaOfMatriculaListNewMatricula);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = membresia.getIdMembresia();
                if (findMembresia(id) == null) {
                    throw new NonexistentEntityException("The membresia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Membresia membresia;
            try {
                membresia = em.getReference(Membresia.class, id);
                membresia.getIdMembresia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The membresia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Matricula> matriculaListOrphanCheck = membresia.getMatriculaList();
            for (Matricula matriculaListOrphanCheckMatricula : matriculaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Membresia (" + membresia + ") cannot be destroyed since the Matricula " + matriculaListOrphanCheckMatricula + " in its matriculaList field has a non-nullable idMembresia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(membresia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Membresia> findMembresiaEntities() {
        return findMembresiaEntities(true, -1, -1);
    }

    public List<Membresia> findMembresiaEntities(int maxResults, int firstResult) {
        return findMembresiaEntities(false, maxResults, firstResult);
    }

    private List<Membresia> findMembresiaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Membresia.class));
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

    public Membresia findMembresia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Membresia.class, id);
        } finally {
            em.close();
        }
    }

    public int getMembresiaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Membresia> rt = cq.from(Membresia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
