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
@Table(name = "TUSUARIOROL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tusuariorol.findAll", query = "SELECT t FROM Tusuariorol t")
    , @NamedQuery(name = "Tusuariorol.findByPkUsuario", query = "SELECT t FROM Tusuariorol t WHERE t.tusuariorolPK.pkUsuario = :pkUsuario")
    , @NamedQuery(name = "Tusuariorol.findByPkRol", query = "SELECT t FROM Tusuariorol t WHERE t.tusuariorolPK.pkRol = :pkRol")})
public class Tusuariorol implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TusuariorolPK tusuariorolPK;
    @JoinColumn(name = "PK_ROL", referencedColumnName = "PK_ROL", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CusuarioRol cusuarioRol;

    public Tusuariorol() {
    }

    public Tusuariorol(TusuariorolPK tusuariorolPK) {
        this.tusuariorolPK = tusuariorolPK;
    }

    public Tusuariorol(BigInteger pkUsuario, BigInteger pkRol) {
        this.tusuariorolPK = new TusuariorolPK(pkUsuario, pkRol);
    }

    public TusuariorolPK getTusuariorolPK() {
        return tusuariorolPK;
    }

    public void setTusuariorolPK(TusuariorolPK tusuariorolPK) {
        this.tusuariorolPK = tusuariorolPK;
    }

    public CusuarioRol getCusuarioRol() {
        return cusuarioRol;
    }

    public void setCusuarioRol(CusuarioRol cusuarioRol) {
        this.cusuarioRol = cusuarioRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tusuariorolPK != null ? tusuariorolPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tusuariorol)) {
            return false;
        }
        Tusuariorol other = (Tusuariorol) object;
        if ((this.tusuariorolPK == null && other.tusuariorolPK != null) || (this.tusuariorolPK != null && !this.tusuariorolPK.equals(other.tusuariorolPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Tusuariorol[ tusuariorolPK=" + tusuariorolPK + " ]";
    }
    
}
