/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entities.Contactos;
import entities.ContactosPK;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author JAIRINHO
 */
public class ContactosJpaController implements Serializable {

    public ContactosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contactos contactos) throws PreexistingEntityException, Exception {
        if (contactos.getContactosPK() == null) {
            contactos.setContactosPK(new ContactosPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(contactos);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findContactos(contactos.getContactosPK()) != null) {
                throw new PreexistingEntityException("Contactos " + contactos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contactos contactos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            contactos = em.merge(contactos);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ContactosPK id = contactos.getContactosPK();
                if (findContactos(id) == null) {
                    throw new NonexistentEntityException("The contactos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ContactosPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contactos contactos;
            try {
                contactos = em.getReference(Contactos.class, id);
                contactos.getContactosPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contactos with id " + id + " no longer exists.", enfe);
            }
            em.remove(contactos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contactos> findContactosEntities() {
        return findContactosEntities(true, -1, -1);
    }

    public List<Contactos> findContactosEntities(int maxResults, int firstResult) {
        return findContactosEntities(false, maxResults, firstResult);
    }

    private List<Contactos> findContactosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contactos.class));
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

    public Contactos findContactos(ContactosPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contactos.class, id);
        } finally {
            em.close();
        }
    }

    public int getContactosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contactos> rt = cq.from(Contactos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List< Objects > getContactos( String user )
    {
        EntityManager em = getEntityManager();
        Query query;
        query = em.createNamedQuery("SELECT * FROM CONTACTOS INNER JOIN USUARIO "
                + " ON CONTACTOS.Usuario_Nick_name1 = USUARIO.NICK_NAME"
                + " WHERE CONTACTOS.Usuario_Nick_name = ? ");
        query.setParameter( 1 , user );
        return query.getResultList();
    }
    
}
