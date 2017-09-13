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
import entity.Tvotos;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class TvotosJpaController implements Serializable {

    public TvotosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tvotos tvotos) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tauscultacion fkAuscultacion = tvotos.getFkAuscultacion();
            if (fkAuscultacion != null) {
                fkAuscultacion = em.getReference(fkAuscultacion.getClass(), fkAuscultacion.getPkAuscultacion());
                tvotos.setFkAuscultacion(fkAuscultacion);
            }
            em.persist(tvotos);
            if (fkAuscultacion != null) {
                fkAuscultacion.getTvotosList().add(tvotos);
                fkAuscultacion = em.merge(fkAuscultacion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTvotos(tvotos.getPkVoto()) != null) {
                throw new PreexistingEntityException("Tvotos " + tvotos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tvotos tvotos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tvotos persistentTvotos = em.find(Tvotos.class, tvotos.getPkVoto());
            Tauscultacion fkAuscultacionOld = persistentTvotos.getFkAuscultacion();
            Tauscultacion fkAuscultacionNew = tvotos.getFkAuscultacion();
            if (fkAuscultacionNew != null) {
                fkAuscultacionNew = em.getReference(fkAuscultacionNew.getClass(), fkAuscultacionNew.getPkAuscultacion());
                tvotos.setFkAuscultacion(fkAuscultacionNew);
            }
            tvotos = em.merge(tvotos);
            if (fkAuscultacionOld != null && !fkAuscultacionOld.equals(fkAuscultacionNew)) {
                fkAuscultacionOld.getTvotosList().remove(tvotos);
                fkAuscultacionOld = em.merge(fkAuscultacionOld);
            }
            if (fkAuscultacionNew != null && !fkAuscultacionNew.equals(fkAuscultacionOld)) {
                fkAuscultacionNew.getTvotosList().add(tvotos);
                fkAuscultacionNew = em.merge(fkAuscultacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = tvotos.getPkVoto();
                if (findTvotos(id) == null) {
                    throw new NonexistentEntityException("The tvotos with id " + id + " no longer exists.");
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
            Tvotos tvotos;
            try {
                tvotos = em.getReference(Tvotos.class, id);
                tvotos.getPkVoto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tvotos with id " + id + " no longer exists.", enfe);
            }
            Tauscultacion fkAuscultacion = tvotos.getFkAuscultacion();
            if (fkAuscultacion != null) {
                fkAuscultacion.getTvotosList().remove(tvotos);
                fkAuscultacion = em.merge(fkAuscultacion);
            }
            em.remove(tvotos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tvotos> findTvotosEntities() {
        return findTvotosEntities(true, -1, -1);
    }

    public List<Tvotos> findTvotosEntities(int maxResults, int firstResult) {
        return findTvotosEntities(false, maxResults, firstResult);
    }

    private List<Tvotos> findTvotosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tvotos.class));
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

    public Tvotos findTvotos(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tvotos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTvotosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tvotos> rt = cq.from(Tvotos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
