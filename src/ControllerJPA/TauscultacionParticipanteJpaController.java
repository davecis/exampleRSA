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
import entity.Cestado;
import entity.Tauscultacion;
import entity.TauscultacionParticipante;
import entity.TauscultacionParticipantePK;
import entity.Tusuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class TauscultacionParticipanteJpaController implements Serializable {

    public TauscultacionParticipanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TauscultacionParticipante tauscultacionParticipante) throws PreexistingEntityException, Exception {
        if (tauscultacionParticipante.getTauscultacionParticipantePK() == null) {
            tauscultacionParticipante.setTauscultacionParticipantePK(new TauscultacionParticipantePK());
        }
//        tauscultacionParticipante.getTauscultacionParticipantePK().setPkAuscultacion(tauscultacionParticipante.getTauscultacion().getPkAuscultacion());
//        tauscultacionParticipante.getTauscultacionParticipantePK().setPkUsuario(tauscultacionParticipante.getTusuario().getPkUsuario());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cestado fkestado = tauscultacionParticipante.getFkestado();
            if (fkestado != null) {
                fkestado = em.getReference(fkestado.getClass(), fkestado.getPkcatalogo());
                tauscultacionParticipante.setFkestado(fkestado);
            }
            Tauscultacion tauscultacion = tauscultacionParticipante.getTauscultacion();
            if (tauscultacion != null) {
                tauscultacion = em.getReference(tauscultacion.getClass(), tauscultacion.getPkAuscultacion());
                tauscultacionParticipante.setTauscultacion(tauscultacion);
            }
            Tusuario tusuario = tauscultacionParticipante.getTusuario();
            if (tusuario != null) {
                tusuario = em.getReference(tusuario.getClass(), tusuario.getPkUsuario());
                tauscultacionParticipante.setTusuario(tusuario);
            }
            em.persist(tauscultacionParticipante);
            if (fkestado != null) {
                fkestado.getTauscultacionParticipanteList().add(tauscultacionParticipante);
                fkestado = em.merge(fkestado);
            }
            if (tauscultacion != null) {
                tauscultacion.getTauscultacionParticipanteList().add(tauscultacionParticipante);
                tauscultacion = em.merge(tauscultacion);
            }
            if (tusuario != null) {
                tusuario.getTauscultacionParticipanteList().add(tauscultacionParticipante);
                tusuario = em.merge(tusuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTauscultacionParticipante(tauscultacionParticipante.getTauscultacionParticipantePK()) != null) {
                throw new PreexistingEntityException("TauscultacionParticipante " + tauscultacionParticipante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TauscultacionParticipante tauscultacionParticipante) throws NonexistentEntityException, Exception {
//        tauscultacionParticipante.getTauscultacionParticipantePK().setPkAuscultacion(tauscultacionParticipante.getTauscultacion().getPkAuscultacion());
//        tauscultacionParticipante.getTauscultacionParticipantePK().setPkUsuario(tauscultacionParticipante.getTusuario().getPkUsuario());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TauscultacionParticipante persistentTauscultacionParticipante = em.find(TauscultacionParticipante.class, tauscultacionParticipante.getTauscultacionParticipantePK());
            Cestado fkestadoOld = persistentTauscultacionParticipante.getFkestado();
            Cestado fkestadoNew = tauscultacionParticipante.getFkestado();
            Tauscultacion tauscultacionOld = persistentTauscultacionParticipante.getTauscultacion();
            Tauscultacion tauscultacionNew = tauscultacionParticipante.getTauscultacion();
            Tusuario tusuarioOld = persistentTauscultacionParticipante.getTusuario();
            Tusuario tusuarioNew = tauscultacionParticipante.getTusuario();
            if (fkestadoNew != null) {
                fkestadoNew = em.getReference(fkestadoNew.getClass(), fkestadoNew.getPkcatalogo());
                tauscultacionParticipante.setFkestado(fkestadoNew);
            }
            if (tauscultacionNew != null) {
                tauscultacionNew = em.getReference(tauscultacionNew.getClass(), tauscultacionNew.getPkAuscultacion());
                tauscultacionParticipante.setTauscultacion(tauscultacionNew);
            }
            if (tusuarioNew != null) {
                tusuarioNew = em.getReference(tusuarioNew.getClass(), tusuarioNew.getPkUsuario());
                tauscultacionParticipante.setTusuario(tusuarioNew);
            }
            tauscultacionParticipante = em.merge(tauscultacionParticipante);
            if (fkestadoOld != null && !fkestadoOld.equals(fkestadoNew)) {
                fkestadoOld.getTauscultacionParticipanteList().remove(tauscultacionParticipante);
                fkestadoOld = em.merge(fkestadoOld);
            }
            if (fkestadoNew != null && !fkestadoNew.equals(fkestadoOld)) {
                fkestadoNew.getTauscultacionParticipanteList().add(tauscultacionParticipante);
                fkestadoNew = em.merge(fkestadoNew);
            }
            if (tauscultacionOld != null && !tauscultacionOld.equals(tauscultacionNew)) {
                tauscultacionOld.getTauscultacionParticipanteList().remove(tauscultacionParticipante);
                tauscultacionOld = em.merge(tauscultacionOld);
            }
            if (tauscultacionNew != null && !tauscultacionNew.equals(tauscultacionOld)) {
                tauscultacionNew.getTauscultacionParticipanteList().add(tauscultacionParticipante);
                tauscultacionNew = em.merge(tauscultacionNew);
            }
            if (tusuarioOld != null && !tusuarioOld.equals(tusuarioNew)) {
                tusuarioOld.getTauscultacionParticipanteList().remove(tauscultacionParticipante);
                tusuarioOld = em.merge(tusuarioOld);
            }
            if (tusuarioNew != null && !tusuarioNew.equals(tusuarioOld)) {
                tusuarioNew.getTauscultacionParticipanteList().add(tauscultacionParticipante);
                tusuarioNew = em.merge(tusuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TauscultacionParticipantePK id = tauscultacionParticipante.getTauscultacionParticipantePK();
                if (findTauscultacionParticipante(id) == null) {
                    throw new NonexistentEntityException("The tauscultacionParticipante with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TauscultacionParticipantePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TauscultacionParticipante tauscultacionParticipante;
            try {
                tauscultacionParticipante = em.getReference(TauscultacionParticipante.class, id);
                tauscultacionParticipante.getTauscultacionParticipantePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tauscultacionParticipante with id " + id + " no longer exists.", enfe);
            }
            Cestado fkestado = tauscultacionParticipante.getFkestado();
            if (fkestado != null) {
                fkestado.getTauscultacionParticipanteList().remove(tauscultacionParticipante);
                fkestado = em.merge(fkestado);
            }
            Tauscultacion tauscultacion = tauscultacionParticipante.getTauscultacion();
            if (tauscultacion != null) {
                tauscultacion.getTauscultacionParticipanteList().remove(tauscultacionParticipante);
                tauscultacion = em.merge(tauscultacion);
            }
            Tusuario tusuario = tauscultacionParticipante.getTusuario();
            if (tusuario != null) {
                tusuario.getTauscultacionParticipanteList().remove(tauscultacionParticipante);
                tusuario = em.merge(tusuario);
            }
            em.remove(tauscultacionParticipante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TauscultacionParticipante> findTauscultacionParticipanteEntities() {
        return findTauscultacionParticipanteEntities(true, -1, -1);
    }

    public List<TauscultacionParticipante> findTauscultacionParticipanteEntities(int maxResults, int firstResult) {
        return findTauscultacionParticipanteEntities(false, maxResults, firstResult);
    }

    private List<TauscultacionParticipante> findTauscultacionParticipanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TauscultacionParticipante.class));
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

    public TauscultacionParticipante findTauscultacionParticipante(TauscultacionParticipantePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TauscultacionParticipante.class, id);
        } finally {
            em.close();
        }
    }

    public int getTauscultacionParticipanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TauscultacionParticipante> rt = cq.from(TauscultacionParticipante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
