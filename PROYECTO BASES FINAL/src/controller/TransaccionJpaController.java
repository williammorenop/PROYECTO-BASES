/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Miembroxtransaccion;
import entities.Transaccion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JAIRINHO
 */
public class TransaccionJpaController implements Serializable {

    public TransaccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transaccion transaccion) throws PreexistingEntityException, Exception {
        if (transaccion.getMiembroxtransaccionList() == null) {
            transaccion.setMiembroxtransaccionList(new ArrayList<Miembroxtransaccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Miembroxtransaccion> attachedMiembroxtransaccionList = new ArrayList<Miembroxtransaccion>();
            for (Miembroxtransaccion miembroxtransaccionListMiembroxtransaccionToAttach : transaccion.getMiembroxtransaccionList()) {
                miembroxtransaccionListMiembroxtransaccionToAttach = em.getReference(miembroxtransaccionListMiembroxtransaccionToAttach.getClass(), miembroxtransaccionListMiembroxtransaccionToAttach.getMiembroxtransaccionPK());
                attachedMiembroxtransaccionList.add(miembroxtransaccionListMiembroxtransaccionToAttach);
            }
            transaccion.setMiembroxtransaccionList(attachedMiembroxtransaccionList);
            em.persist(transaccion);
            for (Miembroxtransaccion miembroxtransaccionListMiembroxtransaccion : transaccion.getMiembroxtransaccionList()) {
                Transaccion oldTransaccionOfMiembroxtransaccionListMiembroxtransaccion = miembroxtransaccionListMiembroxtransaccion.getTransaccion();
                miembroxtransaccionListMiembroxtransaccion.setTransaccion(transaccion);
                miembroxtransaccionListMiembroxtransaccion = em.merge(miembroxtransaccionListMiembroxtransaccion);
                if (oldTransaccionOfMiembroxtransaccionListMiembroxtransaccion != null) {
                    oldTransaccionOfMiembroxtransaccionListMiembroxtransaccion.getMiembroxtransaccionList().remove(miembroxtransaccionListMiembroxtransaccion);
                    oldTransaccionOfMiembroxtransaccionListMiembroxtransaccion = em.merge(oldTransaccionOfMiembroxtransaccionListMiembroxtransaccion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTransaccion(transaccion.getTransaccionId()) != null) {
                throw new PreexistingEntityException("Transaccion " + transaccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Transaccion transaccion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaccion persistentTransaccion = em.find(Transaccion.class, transaccion.getTransaccionId());
            List<Miembroxtransaccion> miembroxtransaccionListOld = persistentTransaccion.getMiembroxtransaccionList();
            List<Miembroxtransaccion> miembroxtransaccionListNew = transaccion.getMiembroxtransaccionList();
            List<String> illegalOrphanMessages = null;
            for (Miembroxtransaccion miembroxtransaccionListOldMiembroxtransaccion : miembroxtransaccionListOld) {
                if (!miembroxtransaccionListNew.contains(miembroxtransaccionListOldMiembroxtransaccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Miembroxtransaccion " + miembroxtransaccionListOldMiembroxtransaccion + " since its transaccion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Miembroxtransaccion> attachedMiembroxtransaccionListNew = new ArrayList<Miembroxtransaccion>();
            for (Miembroxtransaccion miembroxtransaccionListNewMiembroxtransaccionToAttach : miembroxtransaccionListNew) {
                miembroxtransaccionListNewMiembroxtransaccionToAttach = em.getReference(miembroxtransaccionListNewMiembroxtransaccionToAttach.getClass(), miembroxtransaccionListNewMiembroxtransaccionToAttach.getMiembroxtransaccionPK());
                attachedMiembroxtransaccionListNew.add(miembroxtransaccionListNewMiembroxtransaccionToAttach);
            }
            miembroxtransaccionListNew = attachedMiembroxtransaccionListNew;
            transaccion.setMiembroxtransaccionList(miembroxtransaccionListNew);
            transaccion = em.merge(transaccion);
            for (Miembroxtransaccion miembroxtransaccionListNewMiembroxtransaccion : miembroxtransaccionListNew) {
                if (!miembroxtransaccionListOld.contains(miembroxtransaccionListNewMiembroxtransaccion)) {
                    Transaccion oldTransaccionOfMiembroxtransaccionListNewMiembroxtransaccion = miembroxtransaccionListNewMiembroxtransaccion.getTransaccion();
                    miembroxtransaccionListNewMiembroxtransaccion.setTransaccion(transaccion);
                    miembroxtransaccionListNewMiembroxtransaccion = em.merge(miembroxtransaccionListNewMiembroxtransaccion);
                    if (oldTransaccionOfMiembroxtransaccionListNewMiembroxtransaccion != null && !oldTransaccionOfMiembroxtransaccionListNewMiembroxtransaccion.equals(transaccion)) {
                        oldTransaccionOfMiembroxtransaccionListNewMiembroxtransaccion.getMiembroxtransaccionList().remove(miembroxtransaccionListNewMiembroxtransaccion);
                        oldTransaccionOfMiembroxtransaccionListNewMiembroxtransaccion = em.merge(oldTransaccionOfMiembroxtransaccionListNewMiembroxtransaccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = transaccion.getTransaccionId();
                if (findTransaccion(id) == null) {
                    throw new NonexistentEntityException("The transaccion with id " + id + " no longer exists.");
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
            Transaccion transaccion;
            try {
                transaccion = em.getReference(Transaccion.class, id);
                transaccion.getTransaccionId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transaccion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Miembroxtransaccion> miembroxtransaccionListOrphanCheck = transaccion.getMiembroxtransaccionList();
            for (Miembroxtransaccion miembroxtransaccionListOrphanCheckMiembroxtransaccion : miembroxtransaccionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transaccion (" + transaccion + ") cannot be destroyed since the Miembroxtransaccion " + miembroxtransaccionListOrphanCheckMiembroxtransaccion + " in its miembroxtransaccionList field has a non-nullable transaccion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(transaccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Transaccion> findTransaccionEntities() {
        return findTransaccionEntities(true, -1, -1);
    }

    public List<Transaccion> findTransaccionEntities(int maxResults, int firstResult) {
        return findTransaccionEntities(false, maxResults, firstResult);
    }

    private List<Transaccion> findTransaccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transaccion.class));
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

    public Transaccion findTransaccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transaccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransaccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transaccion> rt = cq.from(Transaccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
