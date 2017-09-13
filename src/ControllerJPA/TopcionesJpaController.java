/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerJPA;

import ControllerJPA.exceptions.NonexistentEntityException;
import ControllerJPA.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Tauscultacion;
import entity.Topciones;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class TopcionesJpaController implements Serializable {

    public TopcionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Topciones topciones) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tauscultacion fkAuscultacion = topciones.getFkAuscultacion();
            if (fkAuscultacion != null) {
                fkAuscultacion = em.getReference(fkAuscultacion.getClass(), fkAuscultacion.getPkAuscultacion());
                topciones.setFkAuscultacion(fkAuscultacion);
            }
            em.persist(topciones);
            if (fkAuscultacion != null) {
                fkAuscultacion.getTopcionesList().add(topciones);
                fkAuscultacion = em.merge(fkAuscultacion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTopciones(topciones.getPkOpciones()) != null) {
                throw new PreexistingEntityException("Topciones " + topciones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Topciones topciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Topciones persistentTopciones = em.find(Topciones.class, topciones.getPkOpciones());
            Tauscultacion fkAuscultacionOld = persistentTopciones.getFkAuscultacion();
            Tauscultacion fkAuscultacionNew = topciones.getFkAuscultacion();
            if (fkAuscultacionNew != null) {
                fkAuscultacionNew = em.getReference(fkAuscultacionNew.getClass(), fkAuscultacionNew.getPkAuscultacion());
                topciones.setFkAuscultacion(fkAuscultacionNew);
            }
            topciones = em.merge(topciones);
            if (fkAuscultacionOld != null && !fkAuscultacionOld.equals(fkAuscultacionNew)) {
                fkAuscultacionOld.getTopcionesList().remove(topciones);
                fkAuscultacionOld = em.merge(fkAuscultacionOld);
            }
            if (fkAuscultacionNew != null && !fkAuscultacionNew.equals(fkAuscultacionOld)) {
                fkAuscultacionNew.getTopcionesList().add(topciones);
                fkAuscultacionNew = em.merge(fkAuscultacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = topciones.getPkOpciones();
                if (findTopciones(id) == null) {
                    throw new NonexistentEntityException("The topciones with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Topciones topciones;
            try {
                topciones = em.getReference(Topciones.class, id);
                topciones.getPkOpciones();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The topciones with id " + id + " no longer exists.", enfe);
            }
            Tauscultacion fkAuscultacion = topciones.getFkAuscultacion();
            if (fkAuscultacion != null) {
                fkAuscultacion.getTopcionesList().remove(topciones);
                fkAuscultacion = em.merge(fkAuscultacion);
            }
            em.remove(topciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Topciones> findTopcionesEntities() {
        return findTopcionesEntities(true, -1, -1);
    }

    public List<Topciones> findTopcionesEntities(int maxResults, int firstResult) {
        return findTopcionesEntities(false, maxResults, firstResult);
    }

    private List<Topciones> findTopcionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Topciones.class));
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

    public Topciones findTopciones(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Topciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getTopcionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Topciones> rt = cq.from(Topciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
