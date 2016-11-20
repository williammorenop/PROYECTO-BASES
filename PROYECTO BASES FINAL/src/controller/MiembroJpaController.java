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
import entities.Usuario;
import entities.Miembroxtransaccion;
import java.util.ArrayList;
import java.util.List;
import entities.Miembroxfactura;
import entities.Historial;
import entities.Miembro;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JAIRINHO
 */
public class MiembroJpaController implements Serializable {

    public MiembroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Miembro miembro) throws PreexistingEntityException, Exception {
        if (miembro.getMiembroxtransaccionList() == null) {
            miembro.setMiembroxtransaccionList(new ArrayList<Miembroxtransaccion>());
        }
        if (miembro.getMiembroxfacturaList() == null) {
            miembro.setMiembroxfacturaList(new ArrayList<Miembroxfactura>());
        }
        if (miembro.getHistorialList() == null) {
            miembro.setHistorialList(new ArrayList<Historial>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuarioNickName = miembro.getUsuarioNickName();
            if (usuarioNickName != null) {
                usuarioNickName = em.getReference(usuarioNickName.getClass(), usuarioNickName.getNickName());
                miembro.setUsuarioNickName(usuarioNickName);
            }
            List<Miembroxtransaccion> attachedMiembroxtransaccionList = new ArrayList<Miembroxtransaccion>();
            for (Miembroxtransaccion miembroxtransaccionListMiembroxtransaccionToAttach : miembro.getMiembroxtransaccionList()) {
                miembroxtransaccionListMiembroxtransaccionToAttach = em.getReference(miembroxtransaccionListMiembroxtransaccionToAttach.getClass(), miembroxtransaccionListMiembroxtransaccionToAttach.getMiembroxtransaccionPK());
                attachedMiembroxtransaccionList.add(miembroxtransaccionListMiembroxtransaccionToAttach);
            }
            miembro.setMiembroxtransaccionList(attachedMiembroxtransaccionList);
            List<Miembroxfactura> attachedMiembroxfacturaList = new ArrayList<Miembroxfactura>();
            for (Miembroxfactura miembroxfacturaListMiembroxfacturaToAttach : miembro.getMiembroxfacturaList()) {
                miembroxfacturaListMiembroxfacturaToAttach = em.getReference(miembroxfacturaListMiembroxfacturaToAttach.getClass(), miembroxfacturaListMiembroxfacturaToAttach.getMiembroxfacturaPK());
                attachedMiembroxfacturaList.add(miembroxfacturaListMiembroxfacturaToAttach);
            }
            miembro.setMiembroxfacturaList(attachedMiembroxfacturaList);
            List<Historial> attachedHistorialList = new ArrayList<Historial>();
            for (Historial historialListHistorialToAttach : miembro.getHistorialList()) {
                historialListHistorialToAttach = em.getReference(historialListHistorialToAttach.getClass(), historialListHistorialToAttach.getHistorialPK());
                attachedHistorialList.add(historialListHistorialToAttach);
            }
            miembro.setHistorialList(attachedHistorialList);
            em.persist(miembro);
            if (usuarioNickName != null) {
                usuarioNickName.getMiembroList().add(miembro);
                usuarioNickName = em.merge(usuarioNickName);
            }
            for (Miembroxtransaccion miembroxtransaccionListMiembroxtransaccion : miembro.getMiembroxtransaccionList()) {
                Miembro oldMiembroOfMiembroxtransaccionListMiembroxtransaccion = miembroxtransaccionListMiembroxtransaccion.getMiembro();
                miembroxtransaccionListMiembroxtransaccion.setMiembro(miembro);
                miembroxtransaccionListMiembroxtransaccion = em.merge(miembroxtransaccionListMiembroxtransaccion);
                if (oldMiembroOfMiembroxtransaccionListMiembroxtransaccion != null) {
                    oldMiembroOfMiembroxtransaccionListMiembroxtransaccion.getMiembroxtransaccionList().remove(miembroxtransaccionListMiembroxtransaccion);
                    oldMiembroOfMiembroxtransaccionListMiembroxtransaccion = em.merge(oldMiembroOfMiembroxtransaccionListMiembroxtransaccion);
                }
            }
            for (Miembroxfactura miembroxfacturaListMiembroxfactura : miembro.getMiembroxfacturaList()) {
                Miembro oldMiembroOfMiembroxfacturaListMiembroxfactura = miembroxfacturaListMiembroxfactura.getMiembro();
                miembroxfacturaListMiembroxfactura.setMiembro(miembro);
                miembroxfacturaListMiembroxfactura = em.merge(miembroxfacturaListMiembroxfactura);
                if (oldMiembroOfMiembroxfacturaListMiembroxfactura != null) {
                    oldMiembroOfMiembroxfacturaListMiembroxfactura.getMiembroxfacturaList().remove(miembroxfacturaListMiembroxfactura);
                    oldMiembroOfMiembroxfacturaListMiembroxfactura = em.merge(oldMiembroOfMiembroxfacturaListMiembroxfactura);
                }
            }
            for (Historial historialListHistorial : miembro.getHistorialList()) {
                Miembro oldMiembroOfHistorialListHistorial = historialListHistorial.getMiembro();
                historialListHistorial.setMiembro(miembro);
                historialListHistorial = em.merge(historialListHistorial);
                if (oldMiembroOfHistorialListHistorial != null) {
                    oldMiembroOfHistorialListHistorial.getHistorialList().remove(historialListHistorial);
                    oldMiembroOfHistorialListHistorial = em.merge(oldMiembroOfHistorialListHistorial);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMiembro(miembro.getMiembroId()) != null) {
                throw new PreexistingEntityException("Miembro " + miembro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Miembro miembro) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Miembro persistentMiembro = em.find(Miembro.class, miembro.getMiembroId());
            Usuario usuarioNickNameOld = persistentMiembro.getUsuarioNickName();
            Usuario usuarioNickNameNew = miembro.getUsuarioNickName();
            List<Miembroxtransaccion> miembroxtransaccionListOld = persistentMiembro.getMiembroxtransaccionList();
            List<Miembroxtransaccion> miembroxtransaccionListNew = miembro.getMiembroxtransaccionList();
            List<Miembroxfactura> miembroxfacturaListOld = persistentMiembro.getMiembroxfacturaList();
            List<Miembroxfactura> miembroxfacturaListNew = miembro.getMiembroxfacturaList();
            List<Historial> historialListOld = persistentMiembro.getHistorialList();
            List<Historial> historialListNew = miembro.getHistorialList();
            List<String> illegalOrphanMessages = null;
            for (Miembroxtransaccion miembroxtransaccionListOldMiembroxtransaccion : miembroxtransaccionListOld) {
                if (!miembroxtransaccionListNew.contains(miembroxtransaccionListOldMiembroxtransaccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Miembroxtransaccion " + miembroxtransaccionListOldMiembroxtransaccion + " since its miembro field is not nullable.");
                }
            }
            for (Miembroxfactura miembroxfacturaListOldMiembroxfactura : miembroxfacturaListOld) {
                if (!miembroxfacturaListNew.contains(miembroxfacturaListOldMiembroxfactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Miembroxfactura " + miembroxfacturaListOldMiembroxfactura + " since its miembro field is not nullable.");
                }
            }
            for (Historial historialListOldHistorial : historialListOld) {
                if (!historialListNew.contains(historialListOldHistorial)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Historial " + historialListOldHistorial + " since its miembro field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNickNameNew != null) {
                usuarioNickNameNew = em.getReference(usuarioNickNameNew.getClass(), usuarioNickNameNew.getNickName());
                miembro.setUsuarioNickName(usuarioNickNameNew);
            }
            List<Miembroxtransaccion> attachedMiembroxtransaccionListNew = new ArrayList<Miembroxtransaccion>();
            for (Miembroxtransaccion miembroxtransaccionListNewMiembroxtransaccionToAttach : miembroxtransaccionListNew) {
                miembroxtransaccionListNewMiembroxtransaccionToAttach = em.getReference(miembroxtransaccionListNewMiembroxtransaccionToAttach.getClass(), miembroxtransaccionListNewMiembroxtransaccionToAttach.getMiembroxtransaccionPK());
                attachedMiembroxtransaccionListNew.add(miembroxtransaccionListNewMiembroxtransaccionToAttach);
            }
            miembroxtransaccionListNew = attachedMiembroxtransaccionListNew;
            miembro.setMiembroxtransaccionList(miembroxtransaccionListNew);
            List<Miembroxfactura> attachedMiembroxfacturaListNew = new ArrayList<Miembroxfactura>();
            for (Miembroxfactura miembroxfacturaListNewMiembroxfacturaToAttach : miembroxfacturaListNew) {
                miembroxfacturaListNewMiembroxfacturaToAttach = em.getReference(miembroxfacturaListNewMiembroxfacturaToAttach.getClass(), miembroxfacturaListNewMiembroxfacturaToAttach.getMiembroxfacturaPK());
                attachedMiembroxfacturaListNew.add(miembroxfacturaListNewMiembroxfacturaToAttach);
            }
            miembroxfacturaListNew = attachedMiembroxfacturaListNew;
            miembro.setMiembroxfacturaList(miembroxfacturaListNew);
            List<Historial> attachedHistorialListNew = new ArrayList<Historial>();
            for (Historial historialListNewHistorialToAttach : historialListNew) {
                historialListNewHistorialToAttach = em.getReference(historialListNewHistorialToAttach.getClass(), historialListNewHistorialToAttach.getHistorialPK());
                attachedHistorialListNew.add(historialListNewHistorialToAttach);
            }
            historialListNew = attachedHistorialListNew;
            miembro.setHistorialList(historialListNew);
            miembro = em.merge(miembro);
            if (usuarioNickNameOld != null && !usuarioNickNameOld.equals(usuarioNickNameNew)) {
                usuarioNickNameOld.getMiembroList().remove(miembro);
                usuarioNickNameOld = em.merge(usuarioNickNameOld);
            }
            if (usuarioNickNameNew != null && !usuarioNickNameNew.equals(usuarioNickNameOld)) {
                usuarioNickNameNew.getMiembroList().add(miembro);
                usuarioNickNameNew = em.merge(usuarioNickNameNew);
            }
            for (Miembroxtransaccion miembroxtransaccionListNewMiembroxtransaccion : miembroxtransaccionListNew) {
                if (!miembroxtransaccionListOld.contains(miembroxtransaccionListNewMiembroxtransaccion)) {
                    Miembro oldMiembroOfMiembroxtransaccionListNewMiembroxtransaccion = miembroxtransaccionListNewMiembroxtransaccion.getMiembro();
                    miembroxtransaccionListNewMiembroxtransaccion.setMiembro(miembro);
                    miembroxtransaccionListNewMiembroxtransaccion = em.merge(miembroxtransaccionListNewMiembroxtransaccion);
                    if (oldMiembroOfMiembroxtransaccionListNewMiembroxtransaccion != null && !oldMiembroOfMiembroxtransaccionListNewMiembroxtransaccion.equals(miembro)) {
                        oldMiembroOfMiembroxtransaccionListNewMiembroxtransaccion.getMiembroxtransaccionList().remove(miembroxtransaccionListNewMiembroxtransaccion);
                        oldMiembroOfMiembroxtransaccionListNewMiembroxtransaccion = em.merge(oldMiembroOfMiembroxtransaccionListNewMiembroxtransaccion);
                    }
                }
            }
            for (Miembroxfactura miembroxfacturaListNewMiembroxfactura : miembroxfacturaListNew) {
                if (!miembroxfacturaListOld.contains(miembroxfacturaListNewMiembroxfactura)) {
                    Miembro oldMiembroOfMiembroxfacturaListNewMiembroxfactura = miembroxfacturaListNewMiembroxfactura.getMiembro();
                    miembroxfacturaListNewMiembroxfactura.setMiembro(miembro);
                    miembroxfacturaListNewMiembroxfactura = em.merge(miembroxfacturaListNewMiembroxfactura);
                    if (oldMiembroOfMiembroxfacturaListNewMiembroxfactura != null && !oldMiembroOfMiembroxfacturaListNewMiembroxfactura.equals(miembro)) {
                        oldMiembroOfMiembroxfacturaListNewMiembroxfactura.getMiembroxfacturaList().remove(miembroxfacturaListNewMiembroxfactura);
                        oldMiembroOfMiembroxfacturaListNewMiembroxfactura = em.merge(oldMiembroOfMiembroxfacturaListNewMiembroxfactura);
                    }
                }
            }
            for (Historial historialListNewHistorial : historialListNew) {
                if (!historialListOld.contains(historialListNewHistorial)) {
                    Miembro oldMiembroOfHistorialListNewHistorial = historialListNewHistorial.getMiembro();
                    historialListNewHistorial.setMiembro(miembro);
                    historialListNewHistorial = em.merge(historialListNewHistorial);
                    if (oldMiembroOfHistorialListNewHistorial != null && !oldMiembroOfHistorialListNewHistorial.equals(miembro)) {
                        oldMiembroOfHistorialListNewHistorial.getHistorialList().remove(historialListNewHistorial);
                        oldMiembroOfHistorialListNewHistorial = em.merge(oldMiembroOfHistorialListNewHistorial);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = miembro.getMiembroId();
                if (findMiembro(id) == null) {
                    throw new NonexistentEntityException("The miembro with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Short id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Miembro miembro;
            try {
                miembro = em.getReference(Miembro.class, id);
                miembro.getMiembroId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The miembro with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Miembroxtransaccion> miembroxtransaccionListOrphanCheck = miembro.getMiembroxtransaccionList();
            for (Miembroxtransaccion miembroxtransaccionListOrphanCheckMiembroxtransaccion : miembroxtransaccionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Miembro (" + miembro + ") cannot be destroyed since the Miembroxtransaccion " + miembroxtransaccionListOrphanCheckMiembroxtransaccion + " in its miembroxtransaccionList field has a non-nullable miembro field.");
            }
            List<Miembroxfactura> miembroxfacturaListOrphanCheck = miembro.getMiembroxfacturaList();
            for (Miembroxfactura miembroxfacturaListOrphanCheckMiembroxfactura : miembroxfacturaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Miembro (" + miembro + ") cannot be destroyed since the Miembroxfactura " + miembroxfacturaListOrphanCheckMiembroxfactura + " in its miembroxfacturaList field has a non-nullable miembro field.");
            }
            List<Historial> historialListOrphanCheck = miembro.getHistorialList();
            for (Historial historialListOrphanCheckHistorial : historialListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Miembro (" + miembro + ") cannot be destroyed since the Historial " + historialListOrphanCheckHistorial + " in its historialList field has a non-nullable miembro field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuarioNickName = miembro.getUsuarioNickName();
            if (usuarioNickName != null) {
                usuarioNickName.getMiembroList().remove(miembro);
                usuarioNickName = em.merge(usuarioNickName);
            }
            em.remove(miembro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Miembro> findMiembroEntities() {
        return findMiembroEntities(true, -1, -1);
    }

    public List<Miembro> findMiembroEntities(int maxResults, int firstResult) {
        return findMiembroEntities(false, maxResults, firstResult);
    }

    private List<Miembro> findMiembroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Miembro.class));
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

    public Miembro findMiembro(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Miembro.class, id);
        } finally {
            em.close();
        }
    }

    public int getMiembroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Miembro> rt = cq.from(Miembro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
