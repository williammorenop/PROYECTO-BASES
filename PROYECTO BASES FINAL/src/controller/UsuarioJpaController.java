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
import entities.Miembro;
import entities.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author JAIRINHO
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getMiembroList() == null) {
            usuario.setMiembroList(new ArrayList<Miembro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Miembro> attachedMiembroList = new ArrayList<Miembro>();
            for (Miembro miembroListMiembroToAttach : usuario.getMiembroList()) {
                miembroListMiembroToAttach = em.getReference(miembroListMiembroToAttach.getClass(), miembroListMiembroToAttach.getMiembroId());
                attachedMiembroList.add(miembroListMiembroToAttach);
            }
            usuario.setMiembroList(attachedMiembroList);
            em.persist(usuario);
            for (Miembro miembroListMiembro : usuario.getMiembroList()) {
                Usuario oldUsuarioNickNameOfMiembroListMiembro = miembroListMiembro.getUsuarioNickName();
                miembroListMiembro.setUsuarioNickName(usuario);
                miembroListMiembro = em.merge(miembroListMiembro);
                if (oldUsuarioNickNameOfMiembroListMiembro != null) {
                    oldUsuarioNickNameOfMiembroListMiembro.getMiembroList().remove(miembroListMiembro);
                    oldUsuarioNickNameOfMiembroListMiembro = em.merge(oldUsuarioNickNameOfMiembroListMiembro);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getNickName()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getNickName());
            List<Miembro> miembroListOld = persistentUsuario.getMiembroList();
            List<Miembro> miembroListNew = usuario.getMiembroList();
            List<String> illegalOrphanMessages = null;
            for (Miembro miembroListOldMiembro : miembroListOld) {
                if (!miembroListNew.contains(miembroListOldMiembro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Miembro " + miembroListOldMiembro + " since its usuarioNickName field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Miembro> attachedMiembroListNew = new ArrayList<Miembro>();
            for (Miembro miembroListNewMiembroToAttach : miembroListNew) {
                miembroListNewMiembroToAttach = em.getReference(miembroListNewMiembroToAttach.getClass(), miembroListNewMiembroToAttach.getMiembroId());
                attachedMiembroListNew.add(miembroListNewMiembroToAttach);
            }
            miembroListNew = attachedMiembroListNew;
            usuario.setMiembroList(miembroListNew);
            usuario = em.merge(usuario);
            for (Miembro miembroListNewMiembro : miembroListNew) {
                if (!miembroListOld.contains(miembroListNewMiembro)) {
                    Usuario oldUsuarioNickNameOfMiembroListNewMiembro = miembroListNewMiembro.getUsuarioNickName();
                    miembroListNewMiembro.setUsuarioNickName(usuario);
                    miembroListNewMiembro = em.merge(miembroListNewMiembro);
                    if (oldUsuarioNickNameOfMiembroListNewMiembro != null && !oldUsuarioNickNameOfMiembroListNewMiembro.equals(usuario)) {
                        oldUsuarioNickNameOfMiembroListNewMiembro.getMiembroList().remove(miembroListNewMiembro);
                        oldUsuarioNickNameOfMiembroListNewMiembro = em.merge(oldUsuarioNickNameOfMiembroListNewMiembro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getNickName();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getNickName();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Miembro> miembroListOrphanCheck = usuario.getMiembroList();
            for (Miembro miembroListOrphanCheckMiembro : miembroListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Miembro " + miembroListOrphanCheckMiembro + " in its miembroList field has a non-nullable usuarioNickName field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
