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
import entities.Grupo;
import entities.Historial;
import entities.HistorialPK;
import entities.Miembro;
import entities.Rol;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JAIRINHO
 */
public class HistorialJpaController implements Serializable {

    public HistorialJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Historial historial) throws PreexistingEntityException, Exception {
        if (historial.getHistorialPK() == null) {
            historial.setHistorialPK(new HistorialPK());
        }
        historial.getHistorialPK().setMiembroMiembroId(historial.getMiembro().getMiembroId());
        historial.getHistorialPK().setRolRolId(historial.getRol().getRolId());
        historial.getHistorialPK().setGrupoGrupoId(historial.getGrupo().getGrupoId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo grupo = historial.getGrupo();
            if (grupo != null) {
                grupo = em.getReference(grupo.getClass(), grupo.getGrupoId());
                historial.setGrupo(grupo);
            }
            Miembro miembro = historial.getMiembro();
            if (miembro != null) {
                miembro = em.getReference(miembro.getClass(), miembro.getMiembroId());
                historial.setMiembro(miembro);
            }
            Rol rol = historial.getRol();
            if (rol != null) {
                rol = em.getReference(rol.getClass(), rol.getRolId());
                historial.setRol(rol);
            }
            em.persist(historial);
            if (grupo != null) {
                grupo.getHistorialList().add(historial);
                grupo = em.merge(grupo);
            }
            if (miembro != null) {
                miembro.getHistorialList().add(historial);
                miembro = em.merge(miembro);
            }
            if (rol != null) {
                rol.getHistorialList().add(historial);
                rol = em.merge(rol);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHistorial(historial.getHistorialPK()) != null) {
                throw new PreexistingEntityException("Historial " + historial + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Historial historial) throws NonexistentEntityException, Exception {
        historial.getHistorialPK().setMiembroMiembroId(historial.getMiembro().getMiembroId());
        historial.getHistorialPK().setRolRolId(historial.getRol().getRolId());
        historial.getHistorialPK().setGrupoGrupoId(historial.getGrupo().getGrupoId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Historial persistentHistorial = em.find(Historial.class, historial.getHistorialPK());
            Grupo grupoOld = persistentHistorial.getGrupo();
            Grupo grupoNew = historial.getGrupo();
            Miembro miembroOld = persistentHistorial.getMiembro();
            Miembro miembroNew = historial.getMiembro();
            Rol rolOld = persistentHistorial.getRol();
            Rol rolNew = historial.getRol();
            if (grupoNew != null) {
                grupoNew = em.getReference(grupoNew.getClass(), grupoNew.getGrupoId());
                historial.setGrupo(grupoNew);
            }
            if (miembroNew != null) {
                miembroNew = em.getReference(miembroNew.getClass(), miembroNew.getMiembroId());
                historial.setMiembro(miembroNew);
            }
            if (rolNew != null) {
                rolNew = em.getReference(rolNew.getClass(), rolNew.getRolId());
                historial.setRol(rolNew);
            }
            historial = em.merge(historial);
            if (grupoOld != null && !grupoOld.equals(grupoNew)) {
                grupoOld.getHistorialList().remove(historial);
                grupoOld = em.merge(grupoOld);
            }
            if (grupoNew != null && !grupoNew.equals(grupoOld)) {
                grupoNew.getHistorialList().add(historial);
                grupoNew = em.merge(grupoNew);
            }
            if (miembroOld != null && !miembroOld.equals(miembroNew)) {
                miembroOld.getHistorialList().remove(historial);
                miembroOld = em.merge(miembroOld);
            }
            if (miembroNew != null && !miembroNew.equals(miembroOld)) {
                miembroNew.getHistorialList().add(historial);
                miembroNew = em.merge(miembroNew);
            }
            if (rolOld != null && !rolOld.equals(rolNew)) {
                rolOld.getHistorialList().remove(historial);
                rolOld = em.merge(rolOld);
            }
            if (rolNew != null && !rolNew.equals(rolOld)) {
                rolNew.getHistorialList().add(historial);
                rolNew = em.merge(rolNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                HistorialPK id = historial.getHistorialPK();
                if (findHistorial(id) == null) {
                    throw new NonexistentEntityException("The historial with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(HistorialPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Historial historial;
            try {
                historial = em.getReference(Historial.class, id);
                historial.getHistorialPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historial with id " + id + " no longer exists.", enfe);
            }
            Grupo grupo = historial.getGrupo();
            if (grupo != null) {
                grupo.getHistorialList().remove(historial);
                grupo = em.merge(grupo);
            }
            Miembro miembro = historial.getMiembro();
            if (miembro != null) {
                miembro.getHistorialList().remove(historial);
                miembro = em.merge(miembro);
            }
            Rol rol = historial.getRol();
            if (rol != null) {
                rol.getHistorialList().remove(historial);
                rol = em.merge(rol);
            }
            em.remove(historial);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Historial> findHistorialEntities() {
        return findHistorialEntities(true, -1, -1);
    }

    public List<Historial> findHistorialEntities(int maxResults, int firstResult) {
        return findHistorialEntities(false, maxResults, firstResult);
    }

    private List<Historial> findHistorialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Historial.class));
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

    public Historial findHistorial(HistorialPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Historial.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistorialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Historial> rt = cq.from(Historial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
