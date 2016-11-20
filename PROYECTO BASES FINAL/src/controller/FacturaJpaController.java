/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entities.Factura;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Miembroxfactura;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JAIRINHO
 */
public class FacturaJpaController implements Serializable {

    public FacturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Factura factura) throws PreexistingEntityException, Exception {
        if (factura.getMiembroxfacturaList() == null) {
            factura.setMiembroxfacturaList(new ArrayList<Miembroxfactura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Miembroxfactura> attachedMiembroxfacturaList = new ArrayList<Miembroxfactura>();
            for (Miembroxfactura miembroxfacturaListMiembroxfacturaToAttach : factura.getMiembroxfacturaList()) {
                miembroxfacturaListMiembroxfacturaToAttach = em.getReference(miembroxfacturaListMiembroxfacturaToAttach.getClass(), miembroxfacturaListMiembroxfacturaToAttach.getMiembroxfacturaPK());
                attachedMiembroxfacturaList.add(miembroxfacturaListMiembroxfacturaToAttach);
            }
            factura.setMiembroxfacturaList(attachedMiembroxfacturaList);
            em.persist(factura);
            for (Miembroxfactura miembroxfacturaListMiembroxfactura : factura.getMiembroxfacturaList()) {
                Factura oldFacturaOfMiembroxfacturaListMiembroxfactura = miembroxfacturaListMiembroxfactura.getFactura();
                miembroxfacturaListMiembroxfactura.setFactura(factura);
                miembroxfacturaListMiembroxfactura = em.merge(miembroxfacturaListMiembroxfactura);
                if (oldFacturaOfMiembroxfacturaListMiembroxfactura != null) {
                    oldFacturaOfMiembroxfacturaListMiembroxfactura.getMiembroxfacturaList().remove(miembroxfacturaListMiembroxfactura);
                    oldFacturaOfMiembroxfacturaListMiembroxfactura = em.merge(oldFacturaOfMiembroxfacturaListMiembroxfactura);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFactura(factura.getFacturaId()) != null) {
                throw new PreexistingEntityException("Factura " + factura + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Factura factura) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura persistentFactura = em.find(Factura.class, factura.getFacturaId());
            List<Miembroxfactura> miembroxfacturaListOld = persistentFactura.getMiembroxfacturaList();
            List<Miembroxfactura> miembroxfacturaListNew = factura.getMiembroxfacturaList();
            List<String> illegalOrphanMessages = null;
            for (Miembroxfactura miembroxfacturaListOldMiembroxfactura : miembroxfacturaListOld) {
                if (!miembroxfacturaListNew.contains(miembroxfacturaListOldMiembroxfactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Miembroxfactura " + miembroxfacturaListOldMiembroxfactura + " since its factura field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Miembroxfactura> attachedMiembroxfacturaListNew = new ArrayList<Miembroxfactura>();
            for (Miembroxfactura miembroxfacturaListNewMiembroxfacturaToAttach : miembroxfacturaListNew) {
                miembroxfacturaListNewMiembroxfacturaToAttach = em.getReference(miembroxfacturaListNewMiembroxfacturaToAttach.getClass(), miembroxfacturaListNewMiembroxfacturaToAttach.getMiembroxfacturaPK());
                attachedMiembroxfacturaListNew.add(miembroxfacturaListNewMiembroxfacturaToAttach);
            }
            miembroxfacturaListNew = attachedMiembroxfacturaListNew;
            factura.setMiembroxfacturaList(miembroxfacturaListNew);
            factura = em.merge(factura);
            for (Miembroxfactura miembroxfacturaListNewMiembroxfactura : miembroxfacturaListNew) {
                if (!miembroxfacturaListOld.contains(miembroxfacturaListNewMiembroxfactura)) {
                    Factura oldFacturaOfMiembroxfacturaListNewMiembroxfactura = miembroxfacturaListNewMiembroxfactura.getFactura();
                    miembroxfacturaListNewMiembroxfactura.setFactura(factura);
                    miembroxfacturaListNewMiembroxfactura = em.merge(miembroxfacturaListNewMiembroxfactura);
                    if (oldFacturaOfMiembroxfacturaListNewMiembroxfactura != null && !oldFacturaOfMiembroxfacturaListNewMiembroxfactura.equals(factura)) {
                        oldFacturaOfMiembroxfacturaListNewMiembroxfactura.getMiembroxfacturaList().remove(miembroxfacturaListNewMiembroxfactura);
                        oldFacturaOfMiembroxfacturaListNewMiembroxfactura = em.merge(oldFacturaOfMiembroxfacturaListNewMiembroxfactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = factura.getFacturaId();
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura factura;
            try {
                factura = em.getReference(Factura.class, id);
                factura.getFacturaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The factura with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Miembroxfactura> miembroxfacturaListOrphanCheck = factura.getMiembroxfacturaList();
            for (Miembroxfactura miembroxfacturaListOrphanCheckMiembroxfactura : miembroxfacturaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Factura (" + factura + ") cannot be destroyed since the Miembroxfactura " + miembroxfacturaListOrphanCheckMiembroxfactura + " in its miembroxfacturaList field has a non-nullable factura field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
