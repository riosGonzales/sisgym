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
import Entities.Empleado;
import Entities.Factura;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author Usuario
 */
public class FacturaJpaController implements Serializable {

    public FacturaJpaController() {
    }

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_Sis_Gym_war_1.0-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Factura factura) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente clienteidCliente = factura.getClienteidCliente();
            if (clienteidCliente != null) {
                clienteidCliente = em.getReference(clienteidCliente.getClass(), clienteidCliente.getIdCliente());
                factura.setClienteidCliente(clienteidCliente);
            }
            Empleado empleadoidEmpleado = factura.getEmpleadoidEmpleado();
            if (empleadoidEmpleado != null) {
                empleadoidEmpleado = em.getReference(empleadoidEmpleado.getClass(), empleadoidEmpleado.getIdEmpleado());
                factura.setEmpleadoidEmpleado(empleadoidEmpleado);
            }
            em.persist(factura);
            if (clienteidCliente != null) {
                clienteidCliente.getFacturaList().add(factura);
                clienteidCliente = em.merge(clienteidCliente);
            }
            if (empleadoidEmpleado != null) {
                empleadoidEmpleado.getFacturaList().add(factura);
                empleadoidEmpleado = em.merge(empleadoidEmpleado);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Factura factura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura persistentFactura = em.find(Factura.class, factura.getIdFactura());
            Cliente clienteidClienteOld = persistentFactura.getClienteidCliente();
            Cliente clienteidClienteNew = factura.getClienteidCliente();
            Empleado empleadoidEmpleadoOld = persistentFactura.getEmpleadoidEmpleado();
            Empleado empleadoidEmpleadoNew = factura.getEmpleadoidEmpleado();
            if (clienteidClienteNew != null) {
                clienteidClienteNew = em.getReference(clienteidClienteNew.getClass(), clienteidClienteNew.getIdCliente());
                factura.setClienteidCliente(clienteidClienteNew);
            }
            if (empleadoidEmpleadoNew != null) {
                empleadoidEmpleadoNew = em.getReference(empleadoidEmpleadoNew.getClass(), empleadoidEmpleadoNew.getIdEmpleado());
                factura.setEmpleadoidEmpleado(empleadoidEmpleadoNew);
            }
            factura = em.merge(factura);
            if (clienteidClienteOld != null && !clienteidClienteOld.equals(clienteidClienteNew)) {
                clienteidClienteOld.getFacturaList().remove(factura);
                clienteidClienteOld = em.merge(clienteidClienteOld);
            }
            if (clienteidClienteNew != null && !clienteidClienteNew.equals(clienteidClienteOld)) {
                clienteidClienteNew.getFacturaList().add(factura);
                clienteidClienteNew = em.merge(clienteidClienteNew);
            }
            if (empleadoidEmpleadoOld != null && !empleadoidEmpleadoOld.equals(empleadoidEmpleadoNew)) {
                empleadoidEmpleadoOld.getFacturaList().remove(factura);
                empleadoidEmpleadoOld = em.merge(empleadoidEmpleadoOld);
            }
            if (empleadoidEmpleadoNew != null && !empleadoidEmpleadoNew.equals(empleadoidEmpleadoOld)) {
                empleadoidEmpleadoNew.getFacturaList().add(factura);
                empleadoidEmpleadoNew = em.merge(empleadoidEmpleadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = factura.getIdFactura();
                if (findFactura(id) == null) {
                    throw new NonexistentEntityException("The factura with id " + id + " no longer exists.");
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
            Factura factura;
            try {
                factura = em.getReference(Factura.class, id);
                factura.getIdFactura();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The factura with id " + id + " no longer exists.", enfe);
            }
            Cliente clienteidCliente = factura.getClienteidCliente();
            if (clienteidCliente != null) {
                clienteidCliente.getFacturaList().remove(factura);
                clienteidCliente = em.merge(clienteidCliente);
            }
            Empleado empleadoidEmpleado = factura.getEmpleadoidEmpleado();
            if (empleadoidEmpleado != null) {
                empleadoidEmpleado.getFacturaList().remove(factura);
                empleadoidEmpleado = em.merge(empleadoidEmpleado);
            }
            em.remove(factura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Factura> findFacturaEntities() {
        return findFacturaEntities(true, -1, -1);
    }

    public List<Factura> findFacturaEntities(int maxResults, int firstResult) {
        return findFacturaEntities(false, maxResults, firstResult);
    }

    private List<Factura> findFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Factura.class));
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

    public Factura findFactura(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Factura.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Factura> rt = cq.from(Factura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
