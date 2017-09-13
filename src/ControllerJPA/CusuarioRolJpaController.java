/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerJPA;

import ControllerJPA.exceptions.IllegalOrphanException;
import ControllerJPA.exceptions.NonexistentEntityException;
import ControllerJPA.exceptions.PreexistingEntityException;
import entity.CusuarioRol;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Tusuariorol;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class CusuarioRolJpaController implements Serializable {

    public CusuarioRolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CusuarioRol cusuarioRol) throws PreexistingEntityException, Exception {
        if (cusuarioRol.getTusuariorolList() == null) {
            cusuarioRol.setTusuariorolList(new ArrayList<Tusuariorol>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Tusuariorol> attachedTusuariorolList = new ArrayList<Tusuariorol>();
            for (Tusuariorol tusuariorolListTusuariorolToAttach : cusuarioRol.getTusuariorolList()) {
                tusuariorolListTusuariorolToAttach = em.getReference(tusuariorolListTusuariorolToAttach.getClass(), tusuariorolListTusuariorolToAttach.getTusuariorolPK());
                attachedTusuariorolList.add(tusuariorolListTusuariorolToAttach);
            }
            cusuarioRol.setTusuariorolList(attachedTusuariorolList);
            em.persist(cusuarioRol);
            for (Tusuariorol tusuariorolListTusuariorol : cusuarioRol.getTusuariorolList()) {
                CusuarioRol oldCusuarioRolOfTusuariorolListTusuariorol = tusuariorolListTusuariorol.getCusuarioRol();
                tusuariorolListTusuariorol.setCusuarioRol(cusuarioRol);
                tusuariorolListTusuariorol = em.merge(tusuariorolListTusuariorol);
                if (oldCusuarioRolOfTusuariorolListTusuariorol != null) {
                    oldCusuarioRolOfTusuariorolListTusuariorol.getTusuariorolList().remove(tusuariorolListTusuariorol);
                    oldCusuarioRolOfTusuariorolListTusuariorol = em.merge(oldCusuarioRolOfTusuariorolListTusuariorol);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCusuarioRol(cusuarioRol.getPkRol()) != null) {
                throw new PreexistingEntityException("CusuarioRol " + cusuarioRol + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CusuarioRol cusuarioRol) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CusuarioRol persistentCusuarioRol = em.find(CusuarioRol.class, cusuarioRol.getPkRol());
            List<Tusuariorol> tusuariorolListOld = persistentCusuarioRol.getTusuariorolList();
            List<Tusuariorol> tusuariorolListNew = cusuarioRol.getTusuariorolList();
            List<String> illegalOrphanMessages = null;
            for (Tusuariorol tusuariorolListOldTusuariorol : tusuariorolListOld) {
                if (!tusuariorolListNew.contains(tusuariorolListOldTusuariorol)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tusuariorol " + tusuariorolListOldTusuariorol + " since its cusuarioRol field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Tusuariorol> attachedTusuariorolListNew = new ArrayList<Tusuariorol>();
            for (Tusuariorol tusuariorolListNewTusuariorolToAttach : tusuariorolListNew) {
                tusuariorolListNewTusuariorolToAttach = em.getReference(tusuariorolListNewTusuariorolToAttach.getClass(), tusuariorolListNewTusuariorolToAttach.getTusuariorolPK());
                attachedTusuariorolListNew.add(tusuariorolListNewTusuariorolToAttach);
            }
            tusuariorolListNew = attachedTusuariorolListNew;
            cusuarioRol.setTusuariorolList(tusuariorolListNew);
            cusuarioRol = em.merge(cusuarioRol);
            for (Tusuariorol tusuariorolListNewTusuariorol : tusuariorolListNew) {
                if (!tusuariorolListOld.contains(tusuariorolListNewTusuariorol)) {
                    CusuarioRol oldCusuarioRolOfTusuariorolListNewTusuariorol = tusuariorolListNewTusuariorol.getCusuarioRol();
                    tusuariorolListNewTusuariorol.setCusuarioRol(cusuarioRol);
                    tusuariorolListNewTusuariorol = em.merge(tusuariorolListNewTusuariorol);
                    if (oldCusuarioRolOfTusuariorolListNewTusuariorol != null && !oldCusuarioRolOfTusuariorolListNewTusuariorol.equals(cusuarioRol)) {
                        oldCusuarioRolOfTusuariorolListNewTusuariorol.getTusuariorolList().remove(tusuariorolListNewTusuariorol);
                        oldCusuarioRolOfTusuariorolListNewTusuariorol = em.merge(oldCusuarioRolOfTusuariorolListNewTusuariorol);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = cusuarioRol.getPkRol();
                if (findCusuarioRol(id) == null) {
                    throw new NonexistentEntityException("The cusuarioRol with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CusuarioRol cusuarioRol;
            try {
                cusuarioRol = em.getReference(CusuarioRol.class, id);
                cusuarioRol.getPkRol();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cusuarioRol with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Tusuariorol> tusuariorolListOrphanCheck = cusuarioRol.getTusuariorolList();
            for (Tusuariorol tusuariorolListOrphanCheckTusuariorol : tusuariorolListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CusuarioRol (" + cusuarioRol + ") cannot be destroyed since the Tusuariorol " + tusuariorolListOrphanCheckTusuariorol + " in its tusuariorolList field has a non-nullable cusuarioRol field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cusuarioRol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CusuarioRol> findCusuarioRolEntities() {
        return findCusuarioRolEntities(true, -1, -1);
    }

    public List<CusuarioRol> findCusuarioRolEntities(int maxResults, int firstResult) {
        return findCusuarioRolEntities(false, maxResults, firstResult);
    }

    private List<CusuarioRol> findCusuarioRolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CusuarioRol.class));
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

    public CusuarioRol findCusuarioRol(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CusuarioRol.class, id);
        } finally {
            em.close();
        }
    }

    public int getCusuarioRolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CusuarioRol> rt = cq.from(CusuarioRol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
