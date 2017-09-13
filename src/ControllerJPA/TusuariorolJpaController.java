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
import entity.CusuarioRol;
import entity.Tusuariorol;
import entity.TusuariorolPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class TusuariorolJpaController implements Serializable {

    public TusuariorolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tusuariorol tusuariorol) throws PreexistingEntityException, Exception {
        if (tusuariorol.getTusuariorolPK() == null) {
            tusuariorol.setTusuariorolPK(new TusuariorolPK());
        }
//        tusuariorol.getTusuariorolPK().setPkRol(tusuariorol.getCusuarioRol().getPkRol());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CusuarioRol cusuarioRol = tusuariorol.getCusuarioRol();
            if (cusuarioRol != null) {
                cusuarioRol = em.getReference(cusuarioRol.getClass(), cusuarioRol.getPkRol());
                tusuariorol.setCusuarioRol(cusuarioRol);
            }
            em.persist(tusuariorol);
            if (cusuarioRol != null) {
                cusuarioRol.getTusuariorolList().add(tusuariorol);
                cusuarioRol = em.merge(cusuarioRol);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTusuariorol(tusuariorol.getTusuariorolPK()) != null) {
                throw new PreexistingEntityException("Tusuariorol " + tusuariorol + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tusuariorol tusuariorol) throws NonexistentEntityException, Exception {
//        tusuariorol.getTusuariorolPK().setPkRol(tusuariorol.getCusuarioRol().getPkRol());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tusuariorol persistentTusuariorol = em.find(Tusuariorol.class, tusuariorol.getTusuariorolPK());
            CusuarioRol cusuarioRolOld = persistentTusuariorol.getCusuarioRol();
            CusuarioRol cusuarioRolNew = tusuariorol.getCusuarioRol();
            if (cusuarioRolNew != null) {
                cusuarioRolNew = em.getReference(cusuarioRolNew.getClass(), cusuarioRolNew.getPkRol());
                tusuariorol.setCusuarioRol(cusuarioRolNew);
            }
            tusuariorol = em.merge(tusuariorol);
            if (cusuarioRolOld != null && !cusuarioRolOld.equals(cusuarioRolNew)) {
                cusuarioRolOld.getTusuariorolList().remove(tusuariorol);
                cusuarioRolOld = em.merge(cusuarioRolOld);
            }
            if (cusuarioRolNew != null && !cusuarioRolNew.equals(cusuarioRolOld)) {
                cusuarioRolNew.getTusuariorolList().add(tusuariorol);
                cusuarioRolNew = em.merge(cusuarioRolNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TusuariorolPK id = tusuariorol.getTusuariorolPK();
                if (findTusuariorol(id) == null) {
                    throw new NonexistentEntityException("The tusuariorol with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TusuariorolPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tusuariorol tusuariorol;
            try {
                tusuariorol = em.getReference(Tusuariorol.class, id);
                tusuariorol.getTusuariorolPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tusuariorol with id " + id + " no longer exists.", enfe);
            }
            CusuarioRol cusuarioRol = tusuariorol.getCusuarioRol();
            if (cusuarioRol != null) {
                cusuarioRol.getTusuariorolList().remove(tusuariorol);
                cusuarioRol = em.merge(cusuarioRol);
            }
            em.remove(tusuariorol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tusuariorol> findTusuariorolEntities() {
        return findTusuariorolEntities(true, -1, -1);
    }

    public List<Tusuariorol> findTusuariorolEntities(int maxResults, int firstResult) {
        return findTusuariorolEntities(false, maxResults, firstResult);
    }

    private List<Tusuariorol> findTusuariorolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tusuariorol.class));
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

    public Tusuariorol findTusuariorol(TusuariorolPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tusuariorol.class, id);
        } finally {
            em.close();
        }
    }

    public int getTusuariorolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tusuariorol> rt = cq.from(Tusuariorol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
