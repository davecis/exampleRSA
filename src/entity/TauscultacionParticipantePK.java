/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author David
 */
@Embeddable
public class TauscultacionParticipantePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "PK_USUARIO")
    private BigInteger pkUsuario;
    @Basic(optional = false)
    @Column(name = "PK_AUSCULTACION")
    private BigInteger pkAuscultacion;

    public TauscultacionParticipantePK() {
    }

    public TauscultacionParticipantePK(BigInteger pkUsuario, BigInteger pkAuscultacion) {
        this.pkUsuario = pkUsuario;
        this.pkAuscultacion = pkAuscultacion;
    }

    public BigInteger getPkUsuario() {
        return pkUsuario;
    }

    public void setPkUsuario(BigInteger pkUsuario) {
        this.pkUsuario = pkUsuario;
    }

    public BigInteger getPkAuscultacion() {
        return pkAuscultacion;
    }

    public void setPkAuscultacion(BigInteger pkAuscultacion) {
        this.pkAuscultacion = pkAuscultacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkUsuario != null ? pkUsuario.hashCode() : 0);
        hash += (pkAuscultacion != null ? pkAuscultacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TauscultacionParticipantePK)) {
            return false;
        }
        TauscultacionParticipantePK other = (TauscultacionParticipantePK) object;
        if ((this.pkUsuario == null && other.pkUsuario != null) || (this.pkUsuario != null && !this.pkUsuario.equals(other.pkUsuario))) {
            return false;
        }
        if ((this.pkAuscultacion == null && other.pkAuscultacion != null) || (this.pkAuscultacion != null && !this.pkAuscultacion.equals(other.pkAuscultacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TauscultacionParticipantePK[ pkUsuario=" + pkUsuario + ", pkAuscultacion=" + pkAuscultacion + " ]";
    }
    
}
