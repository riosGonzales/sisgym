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
import Entities.Factura;
import java.util.ArrayList;
import java.util.List;
import Entities.Asistencia;
import Entities.Cliente;
import Entities.Matricula;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import jpa.exceptions.IllegalOrphanException;
import jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author jano_
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public ClienteJpaController() {
    }

    public List<Cliente> findClientesByEstado(int estado) {
        TypedQuery<Cliente> query = getEntityManager().createNamedQuery("Cliente.findByEstado", Cliente.class);
        query.setParameter("estado", estado);
        return query.getResultList();
    }

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_Sis_Gym_war_1.0-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Cliente findByDni(String dni) {
        Query query = getEntityManager().createNamedQuery("Cliente.findByDni");
        query.setParameter("dni", dni);
        query.setMaxResults(1); // Limitar los resultados a uno

        try {
            return (Cliente) query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Puedes manejar el caso cuando no se encuentra ningún resultado
        }
    }

    public static void main(String[] args) {
        ClienteJpaController clijpa = new ClienteJpaController();

        System.out.println(clijpa.findByDni("1"));
    }

    public void create(Cliente cliente) {
        if (cliente.getFacturaList() == null) {
            cliente.setFacturaList(new ArrayList<Factura>());
        }
        if (cliente.getAsistenciaList() == null) {
            cliente.setAsistenciaList(new ArrayList<Asistencia>());
        }
        if (cliente.getMatriculaList() == null) {
            cliente.setMatriculaList(new ArrayList<Matricula>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Factura> attachedFacturaList = new ArrayList<Factura>();
            for (Factura facturaListFacturaToAttach : cliente.getFacturaList()) {
                facturaListFacturaToAttach = em.getReference(facturaListFacturaToAttach.getClass(), facturaListFacturaToAttach.getIdFactura());
                attachedFacturaList.add(facturaListFacturaToAttach);
            }
            cliente.setFacturaList(attachedFacturaList);
            List<Asistencia> attachedAsistenciaList = new ArrayList<Asistencia>();
            for (Asistencia asistenciaListAsistenciaToAttach : cliente.getAsistenciaList()) {
                asistenciaListAsistenciaToAttach = em.getReference(asistenciaListAsistenciaToAttach.getClass(), asistenciaListAsistenciaToAttach.getIdAsistencia());
                attachedAsistenciaList.add(asistenciaListAsistenciaToAttach);
            }
            cliente.setAsistenciaList(attachedAsistenciaList);
            List<Matricula> attachedMatriculaList = new ArrayList<Matricula>();
            for (Matricula matriculaListMatriculaToAttach : cliente.getMatriculaList()) {
                matriculaListMatriculaToAttach = em.getReference(matriculaListMatriculaToAttach.getClass(), matriculaListMatriculaToAttach.getIdMatricula());
                attachedMatriculaList.add(matriculaListMatriculaToAttach);
            }
            cliente.setMatriculaList(attachedMatriculaList);
            em.persist(cliente);
            for (Factura facturaListFactura : cliente.getFacturaList()) {
                Cliente oldClienteidClienteOfFacturaListFactura = facturaListFactura.getClienteidCliente();
                facturaListFactura.setClienteidCliente(cliente);
                facturaListFactura = em.merge(facturaListFactura);
                if (oldClienteidClienteOfFacturaListFactura != null) {
                    oldClienteidClienteOfFacturaListFactura.getFacturaList().remove(facturaListFactura);
                    oldClienteidClienteOfFacturaListFactura = em.merge(oldClienteidClienteOfFacturaListFactura);
                }
            }
            for (Asistencia asistenciaListAsistencia : cliente.getAsistenciaList()) {
                Cliente oldClienteidClienteOfAsistenciaListAsistencia = asistenciaListAsistencia.getClienteidCliente();
                asistenciaListAsistencia.setClienteidCliente(cliente);
                asistenciaListAsistencia = em.merge(asistenciaListAsistencia);
                if (oldClienteidClienteOfAsistenciaListAsistencia != null) {
                    oldClienteidClienteOfAsistenciaListAsistencia.getAsistenciaList().remove(asistenciaListAsistencia);
                    oldClienteidClienteOfAsistenciaListAsistencia = em.merge(oldClienteidClienteOfAsistenciaListAsistencia);
                }
            }
            for (Matricula matriculaListMatricula : cliente.getMatriculaList()) {
                Cliente oldIdClienteOfMatriculaListMatricula = matriculaListMatricula.getIdCliente();
                matriculaListMatricula.setIdCliente(cliente);
                matriculaListMatricula = em.merge(matriculaListMatricula);
                if (oldIdClienteOfMatriculaListMatricula != null) {
                    oldIdClienteOfMatriculaListMatricula.getMatriculaList().remove(matriculaListMatricula);
                    oldIdClienteOfMatriculaListMatricula = em.merge(oldIdClienteOfMatriculaListMatricula);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getIdCliente());
            List<Factura> facturaListOld = persistentCliente.getFacturaList();
            List<Factura> facturaListNew = cliente.getFacturaList();
            List<Asistencia> asistenciaListOld = persistentCliente.getAsistenciaList();
            List<Asistencia> asistenciaListNew = cliente.getAsistenciaList();
            List<Matricula> matriculaListOld = persistentCliente.getMatriculaList();
            List<Matricula> matriculaListNew = cliente.getMatriculaList();
            List<String> illegalOrphanMessages = null;
            for (Factura facturaListOldFactura : facturaListOld) {
                if (!facturaListNew.contains(facturaListOldFactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Factura " + facturaListOldFactura + " since its clienteidCliente field is not nullable.");
                }
            }
            for (Asistencia asistenciaListOldAsistencia : asistenciaListOld) {
                if (!asistenciaListNew.contains(asistenciaListOldAsistencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asistencia " + asistenciaListOldAsistencia + " since its clienteidCliente field is not nullable.");
                }
            }
            for (Matricula matriculaListOldMatricula : matriculaListOld) {
                if (!matriculaListNew.contains(matriculaListOldMatricula)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Matricula " + matriculaListOldMatricula + " since its idCliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Factura> attachedFacturaListNew = new ArrayList<Factura>();
            for (Factura facturaListNewFacturaToAttach : facturaListNew) {
                facturaListNewFacturaToAttach = em.getReference(facturaListNewFacturaToAttach.getClass(), facturaListNewFacturaToAttach.getIdFactura());
                attachedFacturaListNew.add(facturaListNewFacturaToAttach);
            }
            facturaListNew = attachedFacturaListNew;
            cliente.setFacturaList(facturaListNew);
            List<Asistencia> attachedAsistenciaListNew = new ArrayList<Asistencia>();
            for (Asistencia asistenciaListNewAsistenciaToAttach : asistenciaListNew) {
                asistenciaListNewAsistenciaToAttach = em.getReference(asistenciaListNewAsistenciaToAttach.getClass(), asistenciaListNewAsistenciaToAttach.getIdAsistencia());
                attachedAsistenciaListNew.add(asistenciaListNewAsistenciaToAttach);
            }
            asistenciaListNew = attachedAsistenciaListNew;
            cliente.setAsistenciaList(asistenciaListNew);
            List<Matricula> attachedMatriculaListNew = new ArrayList<Matricula>();
            for (Matricula matriculaListNewMatriculaToAttach : matriculaListNew) {
                matriculaListNewMatriculaToAttach = em.getReference(matriculaListNewMatriculaToAttach.getClass(), matriculaListNewMatriculaToAttach.getIdMatricula());
                attachedMatriculaListNew.add(matriculaListNewMatriculaToAttach);
            }
            matriculaListNew = attachedMatriculaListNew;
            cliente.setMatriculaList(matriculaListNew);
            cliente = em.merge(cliente);
            for (Factura facturaListNewFactura : facturaListNew) {
                if (!facturaListOld.contains(facturaListNewFactura)) {
                    Cliente oldClienteidClienteOfFacturaListNewFactura = facturaListNewFactura.getClienteidCliente();
                    facturaListNewFactura.setClienteidCliente(cliente);
                    facturaListNewFactura = em.merge(facturaListNewFactura);
                    if (oldClienteidClienteOfFacturaListNewFactura != null && !oldClienteidClienteOfFacturaListNewFactura.equals(cliente)) {
                        oldClienteidClienteOfFacturaListNewFactura.getFacturaList().remove(facturaListNewFactura);
                        oldClienteidClienteOfFacturaListNewFactura = em.merge(oldClienteidClienteOfFacturaListNewFactura);
                    }
                }
            }
            for (Asistencia asistenciaListNewAsistencia : asistenciaListNew) {
                if (!asistenciaListOld.contains(asistenciaListNewAsistencia)) {
                    Cliente oldClienteidClienteOfAsistenciaListNewAsistencia = asistenciaListNewAsistencia.getClienteidCliente();
                    asistenciaListNewAsistencia.setClienteidCliente(cliente);
                    asistenciaListNewAsistencia = em.merge(asistenciaListNewAsistencia);
                    if (oldClienteidClienteOfAsistenciaListNewAsistencia != null && !oldClienteidClienteOfAsistenciaListNewAsistencia.equals(cliente)) {
                        oldClienteidClienteOfAsistenciaListNewAsistencia.getAsistenciaList().remove(asistenciaListNewAsistencia);
                        oldClienteidClienteOfAsistenciaListNewAsistencia = em.merge(oldClienteidClienteOfAsistenciaListNewAsistencia);
                    }
                }
            }
            for (Matricula matriculaListNewMatricula : matriculaListNew) {
                if (!matriculaListOld.contains(matriculaListNewMatricula)) {
                    Cliente oldIdClienteOfMatriculaListNewMatricula = matriculaListNewMatricula.getIdCliente();
                    matriculaListNewMatricula.setIdCliente(cliente);
                    matriculaListNewMatricula = em.merge(matriculaListNewMatricula);
                    if (oldIdClienteOfMatriculaListNewMatricula != null && !oldIdClienteOfMatriculaListNewMatricula.equals(cliente)) {
                        oldIdClienteOfMatriculaListNewMatricula.getMatriculaList().remove(matriculaListNewMatricula);
                        oldIdClienteOfMatriculaListNewMatricula = em.merge(oldIdClienteOfMatriculaListNewMatricula);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getIdCliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getIdCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Factura> facturaListOrphanCheck = cliente.getFacturaList();
            for (Factura facturaListOrphanCheckFactura : facturaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Factura " + facturaListOrphanCheckFactura + " in its facturaList field has a non-nullable clienteidCliente field.");
            }
            List<Asistencia> asistenciaListOrphanCheck = cliente.getAsistenciaList();
            for (Asistencia asistenciaListOrphanCheckAsistencia : asistenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Asistencia " + asistenciaListOrphanCheckAsistencia + " in its asistenciaList field has a non-nullable clienteidCliente field.");
            }
            List<Matricula> matriculaListOrphanCheck = cliente.getMatriculaList();
            for (Matricula matriculaListOrphanCheckMatricula : matriculaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Matricula " + matriculaListOrphanCheckMatricula + " in its matriculaList field has a non-nullable idCliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
