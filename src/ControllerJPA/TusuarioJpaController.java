/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerJPA;

import ControllerJPA.exceptions.IllegalOrphanException;
import ControllerJPA.exceptions.NonexistentEntityException;
import ControllerJPA.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Tauscultacion;
import java.util.ArrayList;
import java.util.List;
import entity.Tsysuser;
import entity.TauscultacionParticipante;
import entity.Tusuario;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class TusuarioJpaController implements Serializable {

    public TusuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tusuario tusuario) throws PreexistingEntityException, Exception {
        if (tusuario.getTauscultacionList() == null) {
            tusuario.setTauscultacionList(new ArrayList<Tauscultacion>());
        }
        if (tusuario.getTsysuserList() == null) {
            tusuario.setTsysuserList(new ArrayList<Tsysuser>());
        }
        if (tusuario.getTauscultacionParticipanteList() == null) {
            tusuario.setTauscultacionParticipanteList(new ArrayList<TauscultacionParticipante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Tauscultacion> attachedTauscultacionList = new ArrayList<Tauscultacion>();
            for (Tauscultacion tauscultacionListTauscultacionToAttach : tusuario.getTauscultacionList()) {
                tauscultacionListTauscultacionToAttach = em.getReference(tauscultacionListTauscultacionToAttach.getClass(), tauscultacionListTauscultacionToAttach.getPkAuscultacion());
                attachedTauscultacionList.add(tauscultacionListTauscultacionToAttach);
            }
            tusuario.setTauscultacionList(attachedTauscultacionList);
            List<Tsysuser> attachedTsysuserList = new ArrayList<Tsysuser>();
            for (Tsysuser tsysuserListTsysuserToAttach : tusuario.getTsysuserList()) {
                tsysuserListTsysuserToAttach = em.getReference(tsysuserListTsysuserToAttach.getClass(), tsysuserListTsysuserToAttach.getPkUsername());
                attachedTsysuserList.add(tsysuserListTsysuserToAttach);
            }
            tusuario.setTsysuserList(attachedTsysuserList);
            List<TauscultacionParticipante> attachedTauscultacionParticipanteList = new ArrayList<TauscultacionParticipante>();
            for (TauscultacionParticipante tauscultacionParticipanteListTauscultacionParticipanteToAttach : tusuario.getTauscultacionParticipanteList()) {
                tauscultacionParticipanteListTauscultacionParticipanteToAttach = em.getReference(tauscultacionParticipanteListTauscultacionParticipanteToAttach.getClass(), tauscultacionParticipanteListTauscultacionParticipanteToAttach.getTauscultacionParticipantePK());
                attachedTauscultacionParticipanteList.add(tauscultacionParticipanteListTauscultacionParticipanteToAttach);
            }
            tusuario.setTauscultacionParticipanteList(attachedTauscultacionParticipanteList);
            em.persist(tusuario);
            for (Tauscultacion tauscultacionListTauscultacion : tusuario.getTauscultacionList()) {
                tauscultacionListTauscultacion.getTusuarioList().add(tusuario);
                tauscultacionListTauscultacion = em.merge(tauscultacionListTauscultacion);
            }
            for (Tsysuser tsysuserListTsysuser : tusuario.getTsysuserList()) {
                Tusuario oldFkUsuarioOfTsysuserListTsysuser = tsysuserListTsysuser.getFkUsuario();
                tsysuserListTsysuser.setFkUsuario(tusuario);
                tsysuserListTsysuser = em.merge(tsysuserListTsysuser);
                if (oldFkUsuarioOfTsysuserListTsysuser != null) {
                    oldFkUsuarioOfTsysuserListTsysuser.getTsysuserList().remove(tsysuserListTsysuser);
                    oldFkUsuarioOfTsysuserListTsysuser = em.merge(oldFkUsuarioOfTsysuserListTsysuser);
                }
            }
            for (TauscultacionParticipante tauscultacionParticipanteListTauscultacionParticipante : tusuario.getTauscultacionParticipanteList()) {
                Tusuario oldTusuarioOfTauscultacionParticipanteListTauscultacionParticipante = tauscultacionParticipanteListTauscultacionParticipante.getTusuario();
                tauscultacionParticipanteListTauscultacionParticipante.setTusuario(tusuario);
                tauscultacionParticipanteListTauscultacionParticipante = em.merge(tauscultacionParticipanteListTauscultacionParticipante);
                if (oldTusuarioOfTauscultacionParticipanteListTauscultacionParticipante != null) {
                    oldTusuarioOfTauscultacionParticipanteListTauscultacionParticipante.getTauscultacionParticipanteList().remove(tauscultacionParticipanteListTauscultacionParticipante);
                    oldTusuarioOfTauscultacionParticipanteListTauscultacionParticipante = em.merge(oldTusuarioOfTauscultacionParticipanteListTauscultacionParticipante);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTusuario(tusuario.getPkUsuario()) != null) {
                throw new PreexistingEntityException("Tusuario " + tusuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tusuario tusuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tusuario persistentTusuario = em.find(Tusuario.class, tusuario.getPkUsuario());
            List<Tauscultacion> tauscultacionListOld = persistentTusuario.getTauscultacionList();
            List<Tauscultacion> tauscultacionListNew = tusuario.getTauscultacionList();
            List<Tsysuser> tsysuserListOld = persistentTusuario.getTsysuserList();
            List<Tsysuser> tsysuserListNew = tusuario.getTsysuserList();
            List<TauscultacionParticipante> tauscultacionParticipanteListOld = persistentTusuario.getTauscultacionParticipanteList();
            List<TauscultacionParticipante> tauscultacionParticipanteListNew = tusuario.getTauscultacionParticipanteList();
            List<String> illegalOrphanMessages = null;
            for (TauscultacionParticipante tauscultacionParticipanteListOldTauscultacionParticipante : tauscultacionParticipanteListOld) {
                if (!tauscultacionParticipanteListNew.contains(tauscultacionParticipanteListOldTauscultacionParticipante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TauscultacionParticipante " + tauscultacionParticipanteListOldTauscultacionParticipante + " since its tusuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Tauscultacion> attachedTauscultacionListNew = new ArrayList<Tauscultacion>();
            for (Tauscultacion tauscultacionListNewTauscultacionToAttach : tauscultacionListNew) {
                tauscultacionListNewTauscultacionToAttach = em.getReference(tauscultacionListNewTauscultacionToAttach.getClass(), tauscultacionListNewTauscultacionToAttach.getPkAuscultacion());
                attachedTauscultacionListNew.add(tauscultacionListNewTauscultacionToAttach);
            }
            tauscultacionListNew = attachedTauscultacionListNew;
            tusuario.setTauscultacionList(tauscultacionListNew);
            List<Tsysuser> attachedTsysuserListNew = new ArrayList<Tsysuser>();
            for (Tsysuser tsysuserListNewTsysuserToAttach : tsysuserListNew) {
                tsysuserListNewTsysuserToAttach = em.getReference(tsysuserListNewTsysuserToAttach.getClass(), tsysuserListNewTsysuserToAttach.getPkUsername());
                attachedTsysuserListNew.add(tsysuserListNewTsysuserToAttach);
            }
            tsysuserListNew = attachedTsysuserListNew;
            tusuario.setTsysuserList(tsysuserListNew);
            List<TauscultacionParticipante> attachedTauscultacionParticipanteListNew = new ArrayList<TauscultacionParticipante>();
            for (TauscultacionParticipante tauscultacionParticipanteListNewTauscultacionParticipanteToAttach : tauscultacionParticipanteListNew) {
                tauscultacionParticipanteListNewTauscultacionParticipanteToAttach = em.getReference(tauscultacionParticipanteListNewTauscultacionParticipanteToAttach.getClass(), tauscultacionParticipanteListNewTauscultacionParticipanteToAttach.getTauscultacionParticipantePK());
                attachedTauscultacionParticipanteListNew.add(tauscultacionParticipanteListNewTauscultacionParticipanteToAttach);
            }
            tauscultacionParticipanteListNew = attachedTauscultacionParticipanteListNew;
            tusuario.setTauscultacionParticipanteList(tauscultacionParticipanteListNew);
            tusuario = em.merge(tusuario);
            for (Tauscultacion tauscultacionListOldTauscultacion : tauscultacionListOld) {
                if (!tauscultacionListNew.contains(tauscultacionListOldTauscultacion)) {
                    tauscultacionListOldTauscultacion.getTusuarioList().remove(tusuario);
                    tauscultacionListOldTauscultacion = em.merge(tauscultacionListOldTauscultacion);
                }
            }
            for (Tauscultacion tauscultacionListNewTauscultacion : tauscultacionListNew) {
                if (!tauscultacionListOld.contains(tauscultacionListNewTauscultacion)) {
                    tauscultacionListNewTauscultacion.getTusuarioList().add(tusuario);
                    tauscultacionListNewTauscultacion = em.merge(tauscultacionListNewTauscultacion);
                }
            }
            for (Tsysuser tsysuserListOldTsysuser : tsysuserListOld) {
                if (!tsysuserListNew.contains(tsysuserListOldTsysuser)) {
                    tsysuserListOldTsysuser.setFkUsuario(null);
                    tsysuserListOldTsysuser = em.merge(tsysuserListOldTsysuser);
                }
            }
            for (Tsysuser tsysuserListNewTsysuser : tsysuserListNew) {
                if (!tsysuserListOld.contains(tsysuserListNewTsysuser)) {
                    Tusuario oldFkUsuarioOfTsysuserListNewTsysuser = tsysuserListNewTsysuser.getFkUsuario();
                    tsysuserListNewTsysuser.setFkUsuario(tusuario);
                    tsysuserListNewTsysuser = em.merge(tsysuserListNewTsysuser);
                    if (oldFkUsuarioOfTsysuserListNewTsysuser != null && !oldFkUsuarioOfTsysuserListNewTsysuser.equals(tusuario)) {
                        oldFkUsuarioOfTsysuserListNewTsysuser.getTsysuserList().remove(tsysuserListNewTsysuser);
                        oldFkUsuarioOfTsysuserListNewTsysuser = em.merge(oldFkUsuarioOfTsysuserListNewTsysuser);
                    }
                }
            }
            for (TauscultacionParticipante tauscultacionParticipanteListNewTauscultacionParticipante : tauscultacionParticipanteListNew) {
                if (!tauscultacionParticipanteListOld.contains(tauscultacionParticipanteListNewTauscultacionParticipante)) {
                    Tusuario oldTusuarioOfTauscultacionParticipanteListNewTauscultacionParticipante = tauscultacionParticipanteListNewTauscultacionParticipante.getTusuario();
                    tauscultacionParticipanteListNewTauscultacionParticipante.setTusuario(tusuario);
                    tauscultacionParticipanteListNewTauscultacionParticipante = em.merge(tauscultacionParticipanteListNewTauscultacionParticipante);
                    if (oldTusuarioOfTauscultacionParticipanteListNewTauscultacionParticipante != null && !oldTusuarioOfTauscultacionParticipanteListNewTauscultacionParticipante.equals(tusuario)) {
                        oldTusuarioOfTauscultacionParticipanteListNewTauscultacionParticipante.getTauscultacionParticipanteList().remove(tauscultacionParticipanteListNewTauscultacionParticipante);
                        oldTusuarioOfTauscultacionParticipanteListNewTauscultacionParticipante = em.merge(oldTusuarioOfTauscultacionParticipanteListNewTauscultacionParticipante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = tusuario.getPkUsuario();
                if (findTusuario(id) == null) {
                    throw new NonexistentEntityException("The tusuario with id " + id + " no longer exists.");
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
            Tusuario tusuario;
            try {
                tusuario = em.getReference(Tusuario.class, id);
                tusuario.getPkUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tusuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TauscultacionParticipante> tauscultacionParticipanteListOrphanCheck = tusuario.getTauscultacionParticipanteList();
            for (TauscultacionParticipante tauscultacionParticipanteListOrphanCheckTauscultacionParticipante : tauscultacionParticipanteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tusuario (" + tusuario + ") cannot be destroyed since the TauscultacionParticipante " + tauscultacionParticipanteListOrphanCheckTauscultacionParticipante + " in its tauscultacionParticipanteList field has a non-nullable tusuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Tauscultacion> tauscultacionList = tusuario.getTauscultacionList();
            for (Tauscultacion tauscultacionListTauscultacion : tauscultacionList) {
                tauscultacionListTauscultacion.getTusuarioList().remove(tusuario);
                tauscultacionListTauscultacion = em.merge(tauscultacionListTauscultacion);
            }
            List<Tsysuser> tsysuserList = tusuario.getTsysuserList();
            for (Tsysuser tsysuserListTsysuser : tsysuserList) {
                tsysuserListTsysuser.setFkUsuario(null);
                tsysuserListTsysuser = em.merge(tsysuserListTsysuser);
            }
            em.remove(tusuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tusuario> findTusuarioEntities() {
        return findTusuarioEntities(true, -1, -1);
    }

    public List<Tusuario> findTusuarioEntities(int maxResults, int firstResult) {
        return findTusuarioEntities(false, maxResults, firstResult);
    }

    private List<Tusuario> findTusuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tusuario.class));
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

    public Tusuario findTusuario(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tusuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getTusuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tusuario> rt = cq.from(Tusuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
