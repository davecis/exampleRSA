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
public class TusuariorolPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "PK_USUARIO")
    private BigInteger pkUsuario;
    @Basic(optional = false)
    @Column(name = "PK_ROL")
    private BigInteger pkRol;

    public TusuariorolPK() {
    }

    public TusuariorolPK(BigInteger pkUsuario, BigInteger pkRol) {
        this.pkUsuario = pkUsuario;
        this.pkRol = pkRol;
    }

    public BigInteger getPkUsuario() {
        return pkUsuario;
    }

    public void setPkUsuario(BigInteger pkUsuario) {
        this.pkUsuario = pkUsuario;
    }

    public BigInteger getPkRol() {
        return pkRol;
    }

    public void setPkRol(BigInteger pkRol) {
        this.pkRol = pkRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkUsuario != null ? pkUsuario.hashCode() : 0);
        hash += (pkRol != null ? pkRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TusuariorolPK)) {
            return false;
        }
        TusuariorolPK other = (TusuariorolPK) object;
        if ((this.pkUsuario == null && other.pkUsuario != null) || (this.pkUsuario != null && !this.pkUsuario.equals(other.pkUsuario))) {
            return false;
        }
        if ((this.pkRol == null && other.pkRol != null) || (this.pkRol != null && !this.pkRol.equals(other.pkRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TusuariorolPK[ pkUsuario=" + pkUsuario + ", pkRol=" + pkRol + " ]";
    }
    
}
