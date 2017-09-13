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
import entity.Causcultacion;
import entity.Cestado;
import entity.Tauscultacion;
import entity.Tusuario;
import java.util.ArrayList;
import java.util.List;
import entity.Tvotos;
import entity.TauscultacionParticipante;
import entity.Topciones;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class TauscultacionJpaController implements Serializable {

    public TauscultacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tauscultacion tauscultacion) throws PreexistingEntityException, Exception {
        if (tauscultacion.getTusuarioList() == null) {
            tauscultacion.setTusuarioList(new ArrayList<Tusuario>());
        }
        if (tauscultacion.getTvotosList() == null) {
            tauscultacion.setTvotosList(new ArrayList<Tvotos>());
        }
        if (tauscultacion.getTauscultacionParticipanteList() == null) {
            tauscultacion.setTauscultacionParticipanteList(new ArrayList<TauscultacionParticipante>());
        }
        if (tauscultacion.getTopcionesList() == null) {
            tauscultacion.setTopcionesList(new ArrayList<Topciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Causcultacion fkCauscultacion = tauscultacion.getFkCauscultacion();
            if (fkCauscultacion != null) {
                fkCauscultacion = em.getReference(fkCauscultacion.getClass(), fkCauscultacion.getPkCauscultacion());
                tauscultacion.setFkCauscultacion(fkCauscultacion);
            }
            Cestado fkCatalogo = tauscultacion.getFkCatalogo();
            if (fkCatalogo != null) {
                fkCatalogo = em.getReference(fkCatalogo.getClass(), fkCatalogo.getPkcatalogo());
                tauscultacion.setFkCatalogo(fkCatalogo);
            }
            List<Tusuario> attachedTusuarioList = new ArrayList<Tusuario>();
            for (Tusuario tusuarioListTusuarioToAttach : tauscultacion.getTusuarioList()) {
                tusuarioListTusuarioToAttach = em.getReference(tusuarioListTusuarioToAttach.getClass(), tusuarioListTusuarioToAttach.getPkUsuario());
                attachedTusuarioList.add(tusuarioListTusuarioToAttach);
            }
            tauscultacion.setTusuarioList(attachedTusuarioList);
            List<Tvotos> attachedTvotosList = new ArrayList<Tvotos>();
            for (Tvotos tvotosListTvotosToAttach : tauscultacion.getTvotosList()) {
                tvotosListTvotosToAttach = em.getReference(tvotosListTvotosToAttach.getClass(), tvotosListTvotosToAttach.getPkVoto());
                attachedTvotosList.add(tvotosListTvotosToAttach);
            }
            tauscultacion.setTvotosList(attachedTvotosList);
            List<TauscultacionParticipante> attachedTauscultacionParticipanteList = new ArrayList<TauscultacionParticipante>();
            for (TauscultacionParticipante tauscultacionParticipanteListTauscultacionParticipanteToAttach : tauscultacion.getTauscultacionParticipanteList()) {
                tauscultacionParticipanteListTauscultacionParticipanteToAttach = em.getReference(tauscultacionParticipanteListTauscultacionParticipanteToAttach.getClass(), tauscultacionParticipanteListTauscultacionParticipanteToAttach.getTauscultacionParticipantePK());
                attachedTauscultacionParticipanteList.add(tauscultacionParticipanteListTauscultacionParticipanteToAttach);
            }
            tauscultacion.setTauscultacionParticipanteList(attachedTauscultacionParticipanteList);
            List<Topciones> attachedTopcionesList = new ArrayList<Topciones>();
            for (Topciones topcionesListTopcionesToAttach : tauscultacion.getTopcionesList()) {
                topcionesListTopcionesToAttach = em.getReference(topcionesListTopcionesToAttach.getClass(), topcionesListTopcionesToAttach.getPkOpciones());
                attachedTopcionesList.add(topcionesListTopcionesToAttach);
            }
            tauscultacion.setTopcionesList(attachedTopcionesList);
            em.persist(tauscultacion);
            if (fkCauscultacion != null) {
                fkCauscultacion.getTauscultacionList().add(tauscultacion);
                fkCauscultacion = em.merge(fkCauscultacion);
            }
            if (fkCatalogo != null) {
                fkCatalogo.getTauscultacionList().add(tauscultacion);
                fkCatalogo = em.merge(fkCatalogo);
            }
            for (Tusuario tusuarioListTusuario : tauscultacion.getTusuarioList()) {
                tusuarioListTusuario.getTauscultacionList().add(tauscultacion);
                tusuarioListTusuario = em.merge(tusuarioListTusuario);
            }
            for (Tvotos tvotosListTvotos : tauscultacion.getTvotosList()) {
                Tauscultacion oldFkAuscultacionOfTvotosListTvotos = tvotosListTvotos.getFkAuscultacion();
                tvotosListTvotos.setFkAuscultacion(tauscultacion);
                tvotosListTvotos = em.merge(tvotosListTvotos);
                if (oldFkAuscultacionOfTvotosListTvotos != null) {
                    oldFkAuscultacionOfTvotosListTvotos.getTvotosList().remove(tvotosListTvotos);
                    oldFkAuscultacionOfTvotosListTvotos = em.merge(oldFkAuscultacionOfTvotosListTvotos);
                }
            }
            for (TauscultacionParticipante tauscultacionParticipanteListTauscultacionParticipante : tauscultacion.getTauscultacionParticipanteList()) {
                Tauscultacion oldTauscultacionOfTauscultacionParticipanteListTauscultacionParticipante = tauscultacionParticipanteListTauscultacionParticipante.getTauscultacion();
                tauscultacionParticipanteListTauscultacionParticipante.setTauscultacion(tauscultacion);
                tauscultacionParticipanteListTauscultacionParticipante = em.merge(tauscultacionParticipanteListTauscultacionParticipante);
                if (oldTauscultacionOfTauscultacionParticipanteListTauscultacionParticipante != null) {
                    oldTauscultacionOfTauscultacionParticipanteListTauscultacionParticipante.getTauscultacionParticipanteList().remove(tauscultacionParticipanteListTauscultacionParticipante);
                    oldTauscultacionOfTauscultacionParticipanteListTauscultacionParticipante = em.merge(oldTauscultacionOfTauscultacionParticipanteListTauscultacionParticipante);
                }
            }
            for (Topciones topcionesListTopciones : tauscultacion.getTopcionesList()) {
                Tauscultacion oldFkAuscultacionOfTopcionesListTopciones = topcionesListTopciones.getFkAuscultacion();
                topcionesListTopciones.setFkAuscultacion(tauscultacion);
                topcionesListTopciones = em.merge(topcionesListTopciones);
                if (oldFkAuscultacionOfTopcionesListTopciones != null) {
                    oldFkAuscultacionOfTopcionesListTopciones.getTopcionesList().remove(topcionesListTopciones);
                    oldFkAuscultacionOfTopcionesListTopciones = em.merge(oldFkAuscultacionOfTopcionesListTopciones);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTauscultacion(tauscultacion.getPkAuscultacion()) != null) {
                throw new PreexistingEntityException("Tauscultacion " + tauscultacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tauscultacion tauscultacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tauscultacion persistentTauscultacion = em.find(Tauscultacion.class, tauscultacion.getPkAuscultacion());
            Causcultacion fkCauscultacionOld = persistentTauscultacion.getFkCauscultacion();
            Causcultacion fkCauscultacionNew = tauscultacion.getFkCauscultacion();
            Cestado fkCatalogoOld = persistentTauscultacion.getFkCatalogo();
            Cestado fkCatalogoNew = tauscultacion.getFkCatalogo();
            List<Tusuario> tusuarioListOld = persistentTauscultacion.getTusuarioList();
            List<Tusuario> tusuarioListNew = tauscultacion.getTusuarioList();
            List<Tvotos> tvotosListOld = persistentTauscultacion.getTvotosList();
            List<Tvotos> tvotosListNew = tauscultacion.getTvotosList();
            List<TauscultacionParticipante> tauscultacionParticipanteListOld = persistentTauscultacion.getTauscultacionParticipanteList();
            List<TauscultacionParticipante> tauscultacionParticipanteListNew = tauscultacion.getTauscultacionParticipanteList();
            List<Topciones> topcionesListOld = persistentTauscultacion.getTopcionesList();
            List<Topciones> topcionesListNew = tauscultacion.getTopcionesList();
            List<String> illegalOrphanMessages = null;
            for (TauscultacionParticipante tauscultacionParticipanteListOldTauscultacionParticipante : tauscultacionParticipanteListOld) {
                if (!tauscultacionParticipanteListNew.contains(tauscultacionParticipanteListOldTauscultacionParticipante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TauscultacionParticipante " + tauscultacionParticipanteListOldTauscultacionParticipante + " since its tauscultacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkCauscultacionNew != null) {
                fkCauscultacionNew = em.getReference(fkCauscultacionNew.getClass(), fkCauscultacionNew.getPkCauscultacion());
                tauscultacion.setFkCauscultacion(fkCauscultacionNew);
            }
            if (fkCatalogoNew != null) {
                fkCatalogoNew = em.getReference(fkCatalogoNew.getClass(), fkCatalogoNew.getPkcatalogo());
                tauscultacion.setFkCatalogo(fkCatalogoNew);
            }
            List<Tusuario> attachedTusuarioListNew = new ArrayList<Tusuario>();
            for (Tusuario tusuarioListNewTusuarioToAttach : tusuarioListNew) {
                tusuarioListNewTusuarioToAttach = em.getReference(tusuarioListNewTusuarioToAttach.getClass(), tusuarioListNewTusuarioToAttach.getPkUsuario());
                attachedTusuarioListNew.add(tusuarioListNewTusuarioToAttach);
            }
            tusuarioListNew = attachedTusuarioListNew;
            tauscultacion.setTusuarioList(tusuarioListNew);
            List<Tvotos> attachedTvotosListNew = new ArrayList<Tvotos>();
            for (Tvotos tvotosListNewTvotosToAttach : tvotosListNew) {
                tvotosListNewTvotosToAttach = em.getReference(tvotosListNewTvotosToAttach.getClass(), tvotosListNewTvotosToAttach.getPkVoto());
                attachedTvotosListNew.add(tvotosListNewTvotosToAttach);
            }
            tvotosListNew = attachedTvotosListNew;
            tauscultacion.setTvotosList(tvotosListNew);
            List<TauscultacionParticipante> attachedTauscultacionParticipanteListNew = new ArrayList<TauscultacionParticipante>();
            for (TauscultacionParticipante tauscultacionParticipanteListNewTauscultacionParticipanteToAttach : tauscultacionParticipanteListNew) {
                tauscultacionParticipanteListNewTauscultacionParticipanteToAttach = em.getReference(tauscultacionParticipanteListNewTauscultacionParticipanteToAttach.getClass(), tauscultacionParticipanteListNewTauscultacionParticipanteToAttach.getTauscultacionParticipantePK());
                attachedTauscultacionParticipanteListNew.add(tauscultacionParticipanteListNewTauscultacionParticipanteToAttach);
            }
            tauscultacionParticipanteListNew = attachedTauscultacionParticipanteListNew;
            tauscultacion.setTauscultacionParticipanteList(tauscultacionParticipanteListNew);
            List<Topciones> attachedTopcionesListNew = new ArrayList<Topciones>();
            for (Topciones topcionesListNewTopcionesToAttach : topcionesListNew) {
                topcionesListNewTopcionesToAttach = em.getReference(topcionesListNewTopcionesToAttach.getClass(), topcionesListNewTopcionesToAttach.getPkOpciones());
                attachedTopcionesListNew.add(topcionesListNewTopcionesToAttach);
            }
            topcionesListNew = attachedTopcionesListNew;
            tauscultacion.setTopcionesList(topcionesListNew);
            tauscultacion = em.merge(tauscultacion);
            if (fkCauscultacionOld != null && !fkCauscultacionOld.equals(fkCauscultacionNew)) {
                fkCauscultacionOld.getTauscultacionList().remove(tauscultacion);
                fkCauscultacionOld = em.merge(fkCauscultacionOld);
            }
            if (fkCauscultacionNew != null && !fkCauscultacionNew.equals(fkCauscultacionOld)) {
                fkCauscultacionNew.getTauscultacionList().add(tauscultacion);
                fkCauscultacionNew = em.merge(fkCauscultacionNew);
            }
            if (fkCatalogoOld != null && !fkCatalogoOld.equals(fkCatalogoNew)) {
                fkCatalogoOld.getTauscultacionList().remove(tauscultacion);
                fkCatalogoOld = em.merge(fkCatalogoOld);
            }
            if (fkCatalogoNew != null && !fkCatalogoNew.equals(fkCatalogoOld)) {
                fkCatalogoNew.getTauscultacionList().add(tauscultacion);
                fkCatalogoNew = em.merge(fkCatalogoNew);
            }
            for (Tusuario tusuarioListOldTusuario : tusuarioListOld) {
                if (!tusuarioListNew.contains(tusuarioListOldTusuario)) {
                    tusuarioListOldTusuario.getTauscultacionList().remove(tauscultacion);
                    tusuarioListOldTusuario = em.merge(tusuarioListOldTusuario);
                }
            }
            for (Tusuario tusuarioListNewTusuario : tusuarioListNew) {
                if (!tusuarioListOld.contains(tusuarioListNewTusuario)) {
                    tusuarioListNewTusuario.getTauscultacionList().add(tauscultacion);
                    tusuarioListNewTusuario = em.merge(tusuarioListNewTusuario);
                }
            }
            for (Tvotos tvotosListOldTvotos : tvotosListOld) {
                if (!tvotosListNew.contains(tvotosListOldTvotos)) {
                    tvotosListOldTvotos.setFkAuscultacion(null);
                    tvotosListOldTvotos = em.merge(tvotosListOldTvotos);
                }
            }
            for (Tvotos tvotosListNewTvotos : tvotosListNew) {
                if (!tvotosListOld.contains(tvotosListNewTvotos)) {
                    Tauscultacion oldFkAuscultacionOfTvotosListNewTvotos = tvotosListNewTvotos.getFkAuscultacion();
                    tvotosListNewTvotos.setFkAuscultacion(tauscultacion);
                    tvotosListNewTvotos = em.merge(tvotosListNewTvotos);
                    if (oldFkAuscultacionOfTvotosListNewTvotos != null && !oldFkAuscultacionOfTvotosListNewTvotos.equals(tauscultacion)) {
                        oldFkAuscultacionOfTvotosListNewTvotos.getTvotosList().remove(tvotosListNewTvotos);
                        oldFkAuscultacionOfTvotosListNewTvotos = em.merge(oldFkAuscultacionOfTvotosListNewTvotos);
                    }
                }
            }
            for (TauscultacionParticipante tauscultacionParticipanteListNewTauscultacionParticipante : tauscultacionParticipanteListNew) {
                if (!tauscultacionParticipanteListOld.contains(tauscultacionParticipanteListNewTauscultacionParticipante)) {
                    Tauscultacion oldTauscultacionOfTauscultacionParticipanteListNewTauscultacionParticipante = tauscultacionParticipanteListNewTauscultacionParticipante.getTauscultacion();
                    tauscultacionParticipanteListNewTauscultacionParticipante.setTauscultacion(tauscultacion);
                    tauscultacionParticipanteListNewTauscultacionParticipante = em.merge(tauscultacionParticipanteListNewTauscultacionParticipante);
                    if (oldTauscultacionOfTauscultacionParticipanteListNewTauscultacionParticipante != null && !oldTauscultacionOfTauscultacionParticipanteListNewTauscultacionParticipante.equals(tauscultacion)) {
                        oldTauscultacionOfTauscultacionParticipanteListNewTauscultacionParticipante.getTauscultacionParticipanteList().remove(tauscultacionParticipanteListNewTauscultacionParticipante);
                        oldTauscultacionOfTauscultacionParticipanteListNewTauscultacionParticipante = em.merge(oldTauscultacionOfTauscultacionParticipanteListNewTauscultacionParticipante);
                    }
                }
            }
            for (Topciones topcionesListOldTopciones : topcionesListOld) {
                if (!topcionesListNew.contains(topcionesListOldTopciones)) {
                    topcionesListOldTopciones.setFkAuscultacion(null);
                    topcionesListOldTopciones = em.merge(topcionesListOldTopciones);
                }
            }
            for (Topciones topcionesListNewTopciones : topcionesListNew) {
                if (!topcionesListOld.contains(topcionesListNewTopciones)) {
                    Tauscultacion oldFkAuscultacionOfTopcionesListNewTopciones = topcionesListNewTopciones.getFkAuscultacion();
                    topcionesListNewTopciones.setFkAuscultacion(tauscultacion);
                    topcionesListNewTopciones = em.merge(topcionesListNewTopciones);
                    if (oldFkAuscultacionOfTopcionesListNewTopciones != null && !oldFkAuscultacionOfTopcionesListNewTopciones.equals(tauscultacion)) {
                        oldFkAuscultacionOfTopcionesListNewTopciones.getTopcionesList().remove(topcionesListNewTopciones);
                        oldFkAuscultacionOfTopcionesListNewTopciones = em.merge(oldFkAuscultacionOfTopcionesListNewTopciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = tauscultacion.getPkAuscultacion();
                if (findTauscultacion(id) == null) {
                    throw new NonexistentEntityException("The tauscultacion with id " + id + " no longer exists.");
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
            Tauscultacion tauscultacion;
            try {
                tauscultacion = em.getReference(Tauscultacion.class, id);
                tauscultacion.getPkAuscultacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tauscultacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TauscultacionParticipante> tauscultacionParticipanteListOrphanCheck = tauscultacion.getTauscultacionParticipanteList();
            for (TauscultacionParticipante tauscultacionParticipanteListOrphanCheckTauscultacionParticipante : tauscultacionParticipanteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tauscultacion (" + tauscultacion + ") cannot be destroyed since the TauscultacionParticipante " + tauscultacionParticipanteListOrphanCheckTauscultacionParticipante + " in its tauscultacionParticipanteList field has a non-nullable tauscultacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Causcultacion fkCauscultacion = tauscultacion.getFkCauscultacion();
            if (fkCauscultacion != null) {
                fkCauscultacion.getTauscultacionList().remove(tauscultacion);
                fkCauscultacion = em.merge(fkCauscultacion);
            }
            Cestado fkCatalogo = tauscultacion.getFkCatalogo();
            if (fkCatalogo != null) {
                fkCatalogo.getTauscultacionList().remove(tauscultacion);
                fkCatalogo = em.merge(fkCatalogo);
            }
            List<Tusuario> tusuarioList = tauscultacion.getTusuarioList();
            for (Tusuario tusuarioListTusuario : tusuarioList) {
                tusuarioListTusuario.getTauscultacionList().remove(tauscultacion);
                tusuarioListTusuario = em.merge(tusuarioListTusuario);
            }
            List<Tvotos> tvotosList = tauscultacion.getTvotosList();
            for (Tvotos tvotosListTvotos : tvotosList) {
                tvotosListTvotos.setFkAuscultacion(null);
                tvotosListTvotos = em.merge(tvotosListTvotos);
            }
            List<Topciones> topcionesList = tauscultacion.getTopcionesList();
            for (Topciones topcionesListTopciones : topcionesList) {
                topcionesListTopciones.setFkAuscultacion(null);
                topcionesListTopciones = em.merge(topcionesListTopciones);
            }
            em.remove(tauscultacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tauscultacion> findTauscultacionEntities() {
        return findTauscultacionEntities(true, -1, -1);
    }

    public List<Tauscultacion> findTauscultacionEntities(int maxResults, int firstResult) {
        return findTauscultacionEntities(false, maxResults, firstResult);
    }

    private List<Tauscultacion> findTauscultacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tauscultacion.class));
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

    public Tauscultacion findTauscultacion(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tauscultacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTauscultacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tauscultacion> rt = cq.from(Tauscultacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
