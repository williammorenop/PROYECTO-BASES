/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Miembro;
import entities.Miembroxtransaccion;
import entities.MiembroxtransaccionPK;
import entities.Transaccion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JAIRINHO
 */
public class MiembroxtransaccionJpaController implements Serializable {

    public MiembroxtransaccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Miembroxtransaccion miembroxtransaccion) throws PreexistingEntityException, Exception {
        if (miembroxtransaccion.getMiembroxtransaccionPK() == null) {
            miembroxtransaccion.setMiembroxtransaccionPK(new MiembroxtransaccionPK());
        }
        miembroxtransaccion.getMiembroxtransaccionPK().setMiembroMiembroId(miembroxtransaccion.getMiembro().getMiembroId());
        miembroxtransaccion.getMiembroxtransaccionPK().setTransaccionTransaccionId(miembroxtransaccion.getTransaccion().getTransaccionId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Miembro miembro = miembroxtransaccion.getMiembro();
            if (miembro != null) {
                miembro = em.getReference(miembro.getClass(), miembro.getMiembroId());
                miembroxtransaccion.setMiembro(miembro);
            }
            Transaccion transaccion = miembroxtransaccion.getTransaccion();
            if (transaccion != null) {
                transaccion = em.getReference(transaccion.getClass(), transaccion.getTransaccionId());
                miembroxtransaccion.setTransaccion(transaccion);
            }
            em.persist(miembroxtransaccion);
            if (miembro != null) {
                miembro.getMiembroxtransaccionList().add(miembroxtransaccion);
                miembro = em.merge(miembro);
            }
            if (transaccion != null) {
                transaccion.getMiembroxtransaccionList().add(miembroxtransaccion);
                transaccion = em.merge(transaccion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMiembroxtransaccion(miembroxtransaccion.getMiembroxtransaccionPK()) != null) {
                throw new PreexistingEntityException("Miembroxtransaccion " + miembroxtransaccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Miembroxtransaccion miembroxtransaccion) throws NonexistentEntityException, Exception {
        miembroxtransaccion.getMiembroxtransaccionPK().setMiembroMiembroId(miembroxtransaccion.getMiembro().getMiembroId());
        miembroxtransaccion.getMiembroxtransaccionPK().setTransaccionTransaccionId(miembroxtransaccion.getTransaccion().getTransaccionId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Miembroxtransaccion persistentMiembroxtransaccion = em.find(Miembroxtransaccion.class, miembroxtransaccion.getMiembroxtransaccionPK());
            Miembro miembroOld = persistentMiembroxtransaccion.getMiembro();
            Miembro miembroNew = miembroxtransaccion.getMiembro();
            Transaccion transaccionOld = persistentMiembroxtransaccion.getTransaccion();
            Transaccion transaccionNew = miembroxtransaccion.getTransaccion();
            if (miembroNew != null) {
                miembroNew = em.getReference(miembroNew.getClass(), miembroNew.getMiembroId());
                miembroxtransaccion.setMiembro(miembroNew);
            }
            if (transaccionNew != null) {
                transaccionNew = em.getReference(transaccionNew.getClass(), transaccionNew.getTransaccionId());
                miembroxtransaccion.setTransaccion(transaccionNew);
            }
            miembroxtransaccion = em.merge(miembroxtransaccion);
            if (miembroOld != null && !miembroOld.equals(miembroNew)) {
                miembroOld.getMiembroxtransaccionList().remove(miembroxtransaccion);
                miembroOld = em.merge(miembroOld);
            }
            if (miembroNew != null && !miembroNew.equals(miembroOld)) {
                miembroNew.getMiembroxtransaccionList().add(miembroxtransaccion);
                miembroNew = em.merge(miembroNew);
            }
            if (transaccionOld != null && !transaccionOld.equals(transaccionNew)) {
                transaccionOld.getMiembroxtransaccionList().remove(miembroxtransaccion);
                transaccionOld = em.merge(transaccionOld);
            }
            if (transaccionNew != null && !transaccionNew.equals(transaccionOld)) {
                transaccionNew.getMiembroxtransaccionList().add(miembroxtransaccion);
                transaccionNew = em.merge(transaccionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MiembroxtransaccionPK id = miembroxtransaccion.getMiembroxtransaccionPK();
                if (findMiembroxtransaccion(id) == null) {
                    throw new NonexistentEntityException("The miembroxtransaccion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MiembroxtransaccionPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Miembroxtransaccion miembroxtransaccion;
            try {
                miembroxtransaccion = em.getReference(Miembroxtransaccion.class, id);
                miembroxtransaccion.getMiembroxtransaccionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The miembroxtransaccion with id " + id + " no longer exists.", enfe);
            }
            Miembro miembro = miembroxtransaccion.getMiembro();
            if (miembro != null) {
                miembro.getMiembroxtransaccionList().remove(miembroxtransaccion);
                miembro = em.merge(miembro);
            }
            Transaccion transaccion = miembroxtransaccion.getTransaccion();
            if (transaccion != null) {
                transaccion.getMiembroxtransaccionList().remove(miembroxtransaccion);
                transaccion = em.merge(transaccion);
            }
            em.remove(miembroxtransaccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Miembroxtransaccion> findMiembroxtransaccionEntities() {
        return findMiembroxtransaccionEntities(true, -1, -1);
    }

    public List<Miembroxtransaccion> findMiembroxtransaccionEntities(int maxResults, int firstResult) {
        return findMiembroxtransaccionEntities(false, maxResults, firstResult);
    }

    private List<Miembroxtransaccion> findMiembroxtransaccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Miembroxtransaccion.class));
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

    public Miembroxtransaccion findMiembroxtransaccion(MiembroxtransaccionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Miembroxtransaccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getMiembroxtransaccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Miembroxtransaccion> rt = cq.from(Miembroxtransaccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
