/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David
 */
@Entity
@Table(name = "TAUSCULTACION_PARTICIPANTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TauscultacionParticipante.findAll", query = "SELECT t FROM TauscultacionParticipante t")
    , @NamedQuery(name = "TauscultacionParticipante.findByPkUsuario", query = "SELECT t FROM TauscultacionParticipante t WHERE t.tauscultacionParticipantePK.pkUsuario = :pkUsuario")
    , @NamedQuery(name = "TauscultacionParticipante.findByPkAuscultacion", query = "SELECT t FROM TauscultacionParticipante t WHERE t.tauscultacionParticipantePK.pkAuscultacion = :pkAuscultacion")})
public class TauscultacionParticipante implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TauscultacionParticipantePK tauscultacionParticipantePK;
    @JoinColumn(name = "FKESTADO", referencedColumnName = "PKCATALOGO")
    @ManyToOne
    private Cestado fkestado;
    @JoinColumn(name = "PK_AUSCULTACION", referencedColumnName = "PK_AUSCULTACION", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tauscultacion tauscultacion;
    @JoinColumn(name = "PK_USUARIO", referencedColumnName = "PK_USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tusuario tusuario;

    public TauscultacionParticipante() {
    }

    public TauscultacionParticipante(TauscultacionParticipantePK tauscultacionParticipantePK) {
        this.tauscultacionParticipantePK = tauscultacionParticipantePK;
    }

    public TauscultacionParticipante(BigInteger pkUsuario, BigInteger pkAuscultacion) {
        this.tauscultacionParticipantePK = new TauscultacionParticipantePK(pkUsuario, pkAuscultacion);
    }

    public TauscultacionParticipantePK getTauscultacionParticipantePK() {
        return tauscultacionParticipantePK;
    }

    public void setTauscultacionParticipantePK(TauscultacionParticipantePK tauscultacionParticipantePK) {
        this.tauscultacionParticipantePK = tauscultacionParticipantePK;
    }

    public Cestado getFkestado() {
        return fkestado;
    }

    public void setFkestado(Cestado fkestado) {
        this.fkestado = fkestado;
    }

    public Tauscultacion getTauscultacion() {
        return tauscultacion;
    }

    public void setTauscultacion(Tauscultacion tauscultacion) {
        this.tauscultacion = tauscultacion;
    }

    public Tusuario getTusuario() {
        return tusuario;
    }

    public void setTusuario(Tusuario tusuario) {
        this.tusuario = tusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tauscultacionParticipantePK != null ? tauscultacionParticipantePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TauscultacionParticipante)) {
            return false;
        }
        TauscultacionParticipante other = (TauscultacionParticipante) object;
        if ((this.tauscultacionParticipantePK == null && other.tauscultacionParticipantePK != null) || (this.tauscultacionParticipantePK != null && !this.tauscultacionParticipantePK.equals(other.tauscultacionParticipantePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TauscultacionParticipante[ tauscultacionParticipantePK=" + tauscultacionParticipantePK + " ]";
    }
    
}
