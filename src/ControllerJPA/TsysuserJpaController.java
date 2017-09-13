/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerJPA;

import ControllerJPA.exceptions.NonexistentEntityException;
import ControllerJPA.exceptions.PreexistingEntityException;
import entity.Tsysuser;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Tusuario;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class TsysuserJpaController implements Serializable {

    public TsysuserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tsysuser tsysuser) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tusuario fkUsuario = tsysuser.getFkUsuario();
            if (fkUsuario != null) {
                fkUsuario = em.getReference(fkUsuario.getClass(), fkUsuario.getPkUsuario());
                tsysuser.setFkUsuario(fkUsuario);
            }
            em.persist(tsysuser);
            if (fkUsuario != null) {
                fkUsuario.getTsysuserList().add(tsysuser);
                fkUsuario = em.merge(fkUsuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTsysuser(tsysuser.getPkUsername()) != null) {
                throw new PreexistingEntityException("Tsysuser " + tsysuser + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tsysuser tsysuser) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tsysuser persistentTsysuser = em.find(Tsysuser.class, tsysuser.getPkUsername());
            Tusuario fkUsuarioOld = persistentTsysuser.getFkUsuario();
            Tusuario fkUsuarioNew = tsysuser.getFkUsuario();
            if (fkUsuarioNew != null) {
                fkUsuarioNew = em.getReference(fkUsuarioNew.getClass(), fkUsuarioNew.getPkUsuario());
                tsysuser.setFkUsuario(fkUsuarioNew);
            }
            tsysuser = em.merge(tsysuser);
            if (fkUsuarioOld != null && !fkUsuarioOld.equals(fkUsuarioNew)) {
                fkUsuarioOld.getTsysuserList().remove(tsysuser);
                fkUsuarioOld = em.merge(fkUsuarioOld);
            }
            if (fkUsuarioNew != null && !fkUsuarioNew.equals(fkUsuarioOld)) {
                fkUsuarioNew.getTsysuserList().add(tsysuser);
                fkUsuarioNew = em.merge(fkUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = tsysuser.getPkUsername();
                if (findTsysuser(id) == null) {
                    throw new NonexistentEntityException("The tsysuser with id " + id + " no longer exists.");
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
            Tsysuser tsysuser;
            try {
                tsysuser = em.getReference(Tsysuser.class, id);
                tsysuser.getPkUsername();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tsysuser with id " + id + " no longer exists.", enfe);
            }
            Tusuario fkUsuario = tsysuser.getFkUsuario();
            if (fkUsuario != null) {
                fkUsuario.getTsysuserList().remove(tsysuser);
                fkUsuario = em.merge(fkUsuario);
            }
            em.remove(tsysuser);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tsysuser> findTsysuserEntities() {
        return findTsysuserEntities(true, -1, -1);
    }

    public List<Tsysuser> findTsysuserEntities(int maxResults, int firstResult) {
        return findTsysuserEntities(false, maxResults, firstResult);
    }

    private List<Tsysuser> findTsysuserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tsysuser.class));
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

    public Tsysuser findTsysuser(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tsysuser.class, id);
        } finally {
            em.close();
        }
    }

    public int getTsysuserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tsysuser> rt = cq.from(Tsysuser.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
