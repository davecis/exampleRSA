/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerJPA;

import ControllerJPA.exceptions.NonexistentEntityException;
import ControllerJPA.exceptions.PreexistingEntityException;
import entity.Causcultacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Tauscultacion;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class CauscultacionJpaController implements Serializable {

    public CauscultacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Causcultacion causcultacion) throws PreexistingEntityException, Exception {
        if (causcultacion.getTauscultacionList() == null) {
            causcultacion.setTauscultacionList(new ArrayList<Tauscultacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Tauscultacion> attachedTauscultacionList = new ArrayList<Tauscultacion>();
            for (Tauscultacion tauscultacionListTauscultacionToAttach : causcultacion.getTauscultacionList()) {
                tauscultacionListTauscultacionToAttach = em.getReference(tauscultacionListTauscultacionToAttach.getClass(), tauscultacionListTauscultacionToAttach.getPkAuscultacion());
                attachedTauscultacionList.add(tauscultacionListTauscultacionToAttach);
            }
            causcultacion.setTauscultacionList(attachedTauscultacionList);
            em.persist(causcultacion);
            for (Tauscultacion tauscultacionListTauscultacion : causcultacion.getTauscultacionList()) {
                Causcultacion oldFkCauscultacionOfTauscultacionListTauscultacion = tauscultacionListTauscultacion.getFkCauscultacion();
                tauscultacionListTauscultacion.setFkCauscultacion(causcultacion);
                tauscultacionListTauscultacion = em.merge(tauscultacionListTauscultacion);
                if (oldFkCauscultacionOfTauscultacionListTauscultacion != null) {
                    oldFkCauscultacionOfTauscultacionListTauscultacion.getTauscultacionList().remove(tauscultacionListTauscultacion);
                    oldFkCauscultacionOfTauscultacionListTauscultacion = em.merge(oldFkCauscultacionOfTauscultacionListTauscultacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCauscultacion(causcultacion.getPkCauscultacion()) != null) {
                throw new PreexistingEntityException("Causcultacion " + causcultacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Causcultacion causcultacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Causcultacion persistentCauscultacion = em.find(Causcultacion.class, causcultacion.getPkCauscultacion());
            List<Tauscultacion> tauscultacionListOld = persistentCauscultacion.getTauscultacionList();
            List<Tauscultacion> tauscultacionListNew = causcultacion.getTauscultacionList();
            List<Tauscultacion> attachedTauscultacionListNew = new ArrayList<Tauscultacion>();
            for (Tauscultacion tauscultacionListNewTauscultacionToAttach : tauscultacionListNew) {
                tauscultacionListNewTauscultacionToAttach = em.getReference(tauscultacionListNewTauscultacionToAttach.getClass(), tauscultacionListNewTauscultacionToAttach.getPkAuscultacion());
                attachedTauscultacionListNew.add(tauscultacionListNewTauscultacionToAttach);
            }
            tauscultacionListNew = attachedTauscultacionListNew;
            causcultacion.setTauscultacionList(tauscultacionListNew);
            causcultacion = em.merge(causcultacion);
            for (Tauscultacion tauscultacionListOldTauscultacion : tauscultacionListOld) {
                if (!tauscultacionListNew.contains(tauscultacionListOldTauscultacion)) {
                    tauscultacionListOldTauscultacion.setFkCauscultacion(null);
                    tauscultacionListOldTauscultacion = em.merge(tauscultacionListOldTauscultacion);
                }
            }
            for (Tauscultacion tauscultacionListNewTauscultacion : tauscultacionListNew) {
                if (!tauscultacionListOld.contains(tauscultacionListNewTauscultacion)) {
                    Causcultacion oldFkCauscultacionOfTauscultacionListNewTauscultacion = tauscultacionListNewTauscultacion.getFkCauscultacion();
                    tauscultacionListNewTauscultacion.setFkCauscultacion(causcultacion);
                    tauscultacionListNewTauscultacion = em.merge(tauscultacionListNewTauscultacion);
                    if (oldFkCauscultacionOfTauscultacionListNewTauscultacion != null && !oldFkCauscultacionOfTauscultacionListNewTauscultacion.equals(causcultacion)) {
                        oldFkCauscultacionOfTauscultacionListNewTauscultacion.getTauscultacionList().remove(tauscultacionListNewTauscultacion);
                        oldFkCauscultacionOfTauscultacionListNewTauscultacion = em.merge(oldFkCauscultacionOfTauscultacionListNewTauscultacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = causcultacion.getPkCauscultacion();
                if (findCauscultacion(id) == null) {
                    throw new NonexistentEntityException("The causcultacion with id " + id + " no longer exists.");
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
            Causcultacion causcultacion;
            try {
                causcultacion = em.getReference(Causcultacion.class, id);
                causcultacion.getPkCauscultacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The causcultacion with id " + id + " no longer exists.", enfe);
            }
            List<Tauscultacion> tauscultacionList = causcultacion.getTauscultacionList();
            for (Tauscultacion tauscultacionListTauscultacion : tauscultacionList) {
                tauscultacionListTauscultacion.setFkCauscultacion(null);
                tauscultacionListTauscultacion = em.merge(tauscultacionListTauscultacion);
            }
            em.remove(causcultacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Causcultacion> findCauscultacionEntities() {
        return findCauscultacionEntities(true, -1, -1);
    }

    public List<Causcultacion> findCauscultacionEntities(int maxResults, int firstResult) {
        return findCauscultacionEntities(false, maxResults, firstResult);
    }

    private List<Causcultacion> findCauscultacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Causcultacion.class));
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

    public Causcultacion findCauscultacion(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Causcultacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getCauscultacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Causcultacion> rt = cq.from(Causcultacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
