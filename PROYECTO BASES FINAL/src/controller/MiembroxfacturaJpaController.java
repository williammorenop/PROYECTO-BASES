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
import entities.Factura;
import entities.Miembro;
import entities.Miembroxfactura;
import entities.MiembroxfacturaPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JAIRINHO
 */
public class MiembroxfacturaJpaController implements Serializable {

    public MiembroxfacturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Miembroxfactura miembroxfactura) throws PreexistingEntityException, Exception {
        if (miembroxfactura.getMiembroxfacturaPK() == null) {
            miembroxfactura.setMiembroxfacturaPK(new MiembroxfacturaPK());
        }
        miembroxfactura.getMiembroxfacturaPK().setFacturaFacturaId(miembroxfactura.getFactura().getFacturaId());
        miembroxfactura.getMiembroxfacturaPK().setMiembroMiembroId(miembroxfactura.getMiembro().getMiembroId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura factura = miembroxfactura.getFactura();
            if (factura != null) {
                factura = em.getReference(factura.getClass(), factura.getFacturaId());
                miembroxfactura.setFactura(factura);
            }
            Miembro miembro = miembroxfactura.getMiembro();
            if (miembro != null) {
                miembro = em.getReference(miembro.getClass(), miembro.getMiembroId());
                miembroxfactura.setMiembro(miembro);
            }
            em.persist(miembroxfactura);
            if (factura != null) {
                factura.getMiembroxfacturaList().add(miembroxfactura);
                factura = em.merge(factura);
            }
            if (miembro != null) {
                miembro.getMiembroxfacturaList().add(miembroxfactura);
                miembro = em.merge(miembro);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMiembroxfactura(miembroxfactura.getMiembroxfacturaPK()) != null) {
                throw new PreexistingEntityException("Miembroxfactura " + miembroxfactura + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Miembroxfactura miembroxfactura) throws NonexistentEntityException, Exception {
        miembroxfactura.getMiembroxfacturaPK().setFacturaFacturaId(miembroxfactura.getFactura().getFacturaId());
        miembroxfactura.getMiembroxfacturaPK().setMiembroMiembroId(miembroxfactura.getMiembro().getMiembroId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Miembroxfactura persistentMiembroxfactura = em.find(Miembroxfactura.class, miembroxfactura.getMiembroxfacturaPK());
            Factura facturaOld = persistentMiembroxfactura.getFactura();
            Factura facturaNew = miembroxfactura.getFactura();
            Miembro miembroOld = persistentMiembroxfactura.getMiembro();
            Miembro miembroNew = miembroxfactura.getMiembro();
            if (facturaNew != null) {
                facturaNew = em.getReference(facturaNew.getClass(), facturaNew.getFacturaId());
                miembroxfactura.setFactura(facturaNew);
            }
            if (miembroNew != null) {
                miembroNew = em.getReference(miembroNew.getClass(), miembroNew.getMiembroId());
                miembroxfactura.setMiembro(miembroNew);
            }
            miembroxfactura = em.merge(miembroxfactura);
            if (facturaOld != null && !facturaOld.equals(facturaNew)) {
                facturaOld.getMiembroxfacturaList().remove(miembroxfactura);
                facturaOld = em.merge(facturaOld);
            }
            if (facturaNew != null && !facturaNew.equals(facturaOld)) {
                facturaNew.getMiembroxfacturaList().add(miembroxfactura);
                facturaNew = em.merge(facturaNew);
            }
            if (miembroOld != null && !miembroOld.equals(miembroNew)) {
                miembroOld.getMiembroxfacturaList().remove(miembroxfactura);
                miembroOld = em.merge(miembroOld);
            }
            if (miembroNew != null && !miembroNew.equals(miembroOld)) {
                miembroNew.getMiembroxfacturaList().add(miembroxfactura);
                miembroNew = em.merge(miembroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MiembroxfacturaPK id = miembroxfactura.getMiembroxfacturaPK();
                if (findMiembroxfactura(id) == null) {
                    throw new NonexistentEntityException("The miembroxfactura with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MiembroxfacturaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Miembroxfactura miembroxfactura;
            try {
                miembroxfactura = em.getReference(Miembroxfactura.class, id);
                miembroxfactura.getMiembroxfacturaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The miembroxfactura with id " + id + " no longer exists.", enfe);
            }
            Factura factura = miembroxfactura.getFactura();
            if (factura != null) {
                factura.getMiembroxfacturaList().remove(miembroxfactura);
                factura = em.merge(factura);
            }
            Miembro miembro = miembroxfactura.getMiembro();
            if (miembro != null) {
                miembro.getMiembroxfacturaList().remove(miembroxfactura);
                miembro = em.merge(miembro);
            }
            em.remove(miembroxfactura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Miembroxfactura> findMiembroxfacturaEntities() {
        return findMiembroxfacturaEntities(true, -1, -1);
    }

    public List<Miembroxfactura> findMiembroxfacturaEntities(int maxResults, int firstResult) {
        return findMiembroxfacturaEntities(false, maxResults, firstResult);
    }

    private List<Miembroxfactura> findMiembroxfacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Miembroxfactura.class));
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

    public Miembroxfactura findMiembroxfactura(MiembroxfacturaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Miembroxfactura.class, id);
        } finally {
            em.close();
        }
    }

    public int getMiembroxfacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Miembroxfactura> rt = cq.from(Miembroxfactura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
