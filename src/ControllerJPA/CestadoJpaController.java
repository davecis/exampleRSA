/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerJPA;

import ControllerJPA.exceptions.NonexistentEntityException;
import ControllerJPA.exceptions.PreexistingEntityException;
import entity.Cestado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Tauscultacion;
import java.util.ArrayList;
import java.util.List;
import entity.TauscultacionParticipante;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class CestadoJpaController implements Serializable {

    public CestadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cestado cestado) throws PreexistingEntityException, Exception {
        if (cestado.getTauscultacionList() == null) {
            cestado.setTauscultacionList(new ArrayList<Tauscultacion>());
        }
        if (cestado.getTauscultacionParticipanteList() == null) {
            cestado.setTauscultacionParticipanteList(new ArrayList<TauscultacionParticipante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Tauscultacion> attachedTauscultacionList = new ArrayList<Tauscultacion>();
            for (Tauscultacion tauscultacionListTauscultacionToAttach : cestado.getTauscultacionList()) {
                tauscultacionListTauscultacionToAttach = em.getReference(tauscultacionListTauscultacionToAttach.getClass(), tauscultacionListTauscultacionToAttach.getPkAuscultacion());
                attachedTauscultacionList.add(tauscultacionListTauscultacionToAttach);
            }
            cestado.setTauscultacionList(attachedTauscultacionList);
            List<TauscultacionParticipante> attachedTauscultacionParticipanteList = new ArrayList<TauscultacionParticipante>();
            for (TauscultacionParticipante tauscultacionParticipanteListTauscultacionParticipanteToAttach : cestado.getTauscultacionParticipanteList()) {
                tauscultacionParticipanteListTauscultacionParticipanteToAttach = em.getReference(tauscultacionParticipanteListTauscultacionParticipanteToAttach.getClass(), tauscultacionParticipanteListTauscultacionParticipanteToAttach.getTauscultacionParticipantePK());
                attachedTauscultacionParticipanteList.add(tauscultacionParticipanteListTauscultacionParticipanteToAttach);
            }
            cestado.setTauscultacionParticipanteList(attachedTauscultacionParticipanteList);
            em.persist(cestado);
            for (Tauscultacion tauscultacionListTauscultacion : cestado.getTauscultacionList()) {
                Cestado oldFkCatalogoOfTauscultacionListTauscultacion = tauscultacionListTauscultacion.getFkCatalogo();
                tauscultacionListTauscultacion.setFkCatalogo(cestado);
                tauscultacionListTauscultacion = em.merge(tauscultacionListTauscultacion);
                if (oldFkCatalogoOfTauscultacionListTauscultacion != null) {
                    oldFkCatalogoOfTauscultacionListTauscultacion.getTauscultacionList().remove(tauscultacionListTauscultacion);
                    oldFkCatalogoOfTauscultacionListTauscultacion = em.merge(oldFkCatalogoOfTauscultacionListTauscultacion);
                }
            }
            for (TauscultacionParticipante tauscultacionParticipanteListTauscultacionParticipante : cestado.getTauscultacionParticipanteList()) {
                Cestado oldFkestadoOfTauscultacionParticipanteListTauscultacionParticipante = tauscultacionParticipanteListTauscultacionParticipante.getFkestado();
                tauscultacionParticipanteListTauscultacionParticipante.setFkestado(cestado);
                tauscultacionParticipanteListTauscultacionParticipante = em.merge(tauscultacionParticipanteListTauscultacionParticipante);
                if (oldFkestadoOfTauscultacionParticipanteListTauscultacionParticipante != null) {
                    oldFkestadoOfTauscultacionParticipanteListTauscultacionParticipante.getTauscultacionParticipanteList().remove(tauscultacionParticipanteListTauscultacionParticipante);
                    oldFkestadoOfTauscultacionParticipanteListTauscultacionParticipante = em.merge(oldFkestadoOfTauscultacionParticipanteListTauscultacionParticipante);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCestado(cestado.getPkcatalogo()) != null) {
                throw new PreexistingEntityException("Cestado " + cestado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cestado cestado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cestado persistentCestado = em.find(Cestado.class, cestado.getPkcatalogo());
            List<Tauscultacion> tauscultacionListOld = persistentCestado.getTauscultacionList();
            List<Tauscultacion> tauscultacionListNew = cestado.getTauscultacionList();
            List<TauscultacionParticipante> tauscultacionParticipanteListOld = persistentCestado.getTauscultacionParticipanteList();
            List<TauscultacionParticipante> tauscultacionParticipanteListNew = cestado.getTauscultacionParticipanteList();
            List<Tauscultacion> attachedTauscultacionListNew = new ArrayList<Tauscultacion>();
            for (Tauscultacion tauscultacionListNewTauscultacionToAttach : tauscultacionListNew) {
                tauscultacionListNewTauscultacionToAttach = em.getReference(tauscultacionListNewTauscultacionToAttach.getClass(), tauscultacionListNewTauscultacionToAttach.getPkAuscultacion());
                attachedTauscultacionListNew.add(tauscultacionListNewTauscultacionToAttach);
            }
            tauscultacionListNew = attachedTauscultacionListNew;
            cestado.setTauscultacionList(tauscultacionListNew);
            List<TauscultacionParticipante> attachedTauscultacionParticipanteListNew = new ArrayList<TauscultacionParticipante>();
            for (TauscultacionParticipante tauscultacionParticipanteListNewTauscultacionParticipanteToAttach : tauscultacionParticipanteListNew) {
                tauscultacionParticipanteListNewTauscultacionParticipanteToAttach = em.getReference(tauscultacionParticipanteListNewTauscultacionParticipanteToAttach.getClass(), tauscultacionParticipanteListNewTauscultacionParticipanteToAttach.getTauscultacionParticipantePK());
                attachedTauscultacionParticipanteListNew.add(tauscultacionParticipanteListNewTauscultacionParticipanteToAttach);
            }
            tauscultacionParticipanteListNew = attachedTauscultacionParticipanteListNew;
            cestado.setTauscultacionParticipanteList(tauscultacionParticipanteListNew);
            cestado = em.merge(cestado);
            for (Tauscultacion tauscultacionListOldTauscultacion : tauscultacionListOld) {
                if (!tauscultacionListNew.contains(tauscultacionListOldTauscultacion)) {
                    tauscultacionListOldTauscultacion.setFkCatalogo(null);
                    tauscultacionListOldTauscultacion = em.merge(tauscultacionListOldTauscultacion);
                }
            }
            for (Tauscultacion tauscultacionListNewTauscultacion : tauscultacionListNew) {
                if (!tauscultacionListOld.contains(tauscultacionListNewTauscultacion)) {
                    Cestado oldFkCatalogoOfTauscultacionListNewTauscultacion = tauscultacionListNewTauscultacion.getFkCatalogo();
                    tauscultacionListNewTauscultacion.setFkCatalogo(cestado);
                    tauscultacionListNewTauscultacion = em.merge(tauscultacionListNewTauscultacion);
                    if (oldFkCatalogoOfTauscultacionListNewTauscultacion != null && !oldFkCatalogoOfTauscultacionListNewTauscultacion.equals(cestado)) {
                        oldFkCatalogoOfTauscultacionListNewTauscultacion.getTauscultacionList().remove(tauscultacionListNewTauscultacion);
                        oldFkCatalogoOfTauscultacionListNewTauscultacion = em.merge(oldFkCatalogoOfTauscultacionListNewTauscultacion);
                    }
                }
            }
            for (TauscultacionParticipante tauscultacionParticipanteListOldTauscultacionParticipante : tauscultacionParticipanteListOld) {
                if (!tauscultacionParticipanteListNew.contains(tauscultacionParticipanteListOldTauscultacionParticipante)) {
                    tauscultacionParticipanteListOldTauscultacionParticipante.setFkestado(null);
                    tauscultacionParticipanteListOldTauscultacionParticipante = em.merge(tauscultacionParticipanteListOldTauscultacionParticipante);
                }
            }
            for (TauscultacionParticipante tauscultacionParticipanteListNewTauscultacionParticipante : tauscultacionParticipanteListNew) {
                if (!tauscultacionParticipanteListOld.contains(tauscultacionParticipanteListNewTauscultacionParticipante)) {
                    Cestado oldFkestadoOfTauscultacionParticipanteListNewTauscultacionParticipante = tauscultacionParticipanteListNewTauscultacionParticipante.getFkestado();
                    tauscultacionParticipanteListNewTauscultacionParticipante.setFkestado(cestado);
                    tauscultacionParticipanteListNewTauscultacionParticipante = em.merge(tauscultacionParticipanteListNewTauscultacionParticipante);
                    if (oldFkestadoOfTauscultacionParticipanteListNewTauscultacionParticipante != null && !oldFkestadoOfTauscultacionParticipanteListNewTauscultacionParticipante.equals(cestado)) {
                        oldFkestadoOfTauscultacionParticipanteListNewTauscultacionParticipante.getTauscultacionParticipanteList().remove(tauscultacionParticipanteListNewTauscultacionParticipante);
                        oldFkestadoOfTauscultacionParticipanteListNewTauscultacionParticipante = em.merge(oldFkestadoOfTauscultacionParticipanteListNewTauscultacionParticipante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = cestado.getPkcatalogo();
                if (findCestado(id) == null) {
                    throw new NonexistentEntityException("The cestado with id " + id + " no longer exists.");
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
            Cestado cestado;
            try {
                cestado = em.getReference(Cestado.class, id);
                cestado.getPkcatalogo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cestado with id " + id + " no longer exists.", enfe);
            }
            List<Tauscultacion> tauscultacionList = cestado.getTauscultacionList();
            for (Tauscultacion tauscultacionListTauscultacion : tauscultacionList) {
                tauscultacionListTauscultacion.setFkCatalogo(null);
                tauscultacionListTauscultacion = em.merge(tauscultacionListTauscultacion);
            }
            List<TauscultacionParticipante> tauscultacionParticipanteList = cestado.getTauscultacionParticipanteList();
            for (TauscultacionParticipante tauscultacionParticipanteListTauscultacionParticipante : tauscultacionParticipanteList) {
                tauscultacionParticipanteListTauscultacionParticipante.setFkestado(null);
                tauscultacionParticipanteListTauscultacionParticipante = em.merge(tauscultacionParticipanteListTauscultacionParticipante);
            }
            em.remove(cestado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cestado> findCestadoEntities() {
        return findCestadoEntities(true, -1, -1);
    }

    public List<Cestado> findCestadoEntities(int maxResults, int firstResult) {
        return findCestadoEntities(false, maxResults, firstResult);
    }

    private List<Cestado> findCestadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cestado.class));
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

    public Cestado findCestado(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cestado.class, id);
        } finally {
            em.close();
        }
    }

    public int getCestadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cestado> rt = cq.from(Cestado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
