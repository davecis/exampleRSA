/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author David
 */
@Entity
@Table(name = "CUSUARIO_ROL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CusuarioRol.findAll", query = "SELECT c FROM CusuarioRol c")
    , @NamedQuery(name = "CusuarioRol.findByPkRol", query = "SELECT c FROM CusuarioRol c WHERE c.pkRol = :pkRol")
    , @NamedQuery(name = "CusuarioRol.findByRol", query = "SELECT c FROM CusuarioRol c WHERE c.rol = :rol")
    , @NamedQuery(name = "CusuarioRol.findByDescripcion", query = "SELECT c FROM CusuarioRol c WHERE c.descripcion = :descripcion")})
public class CusuarioRol implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "PK_ROL")
    private BigDecimal pkRol;
    @Basic(optional = false)
    @Column(name = "ROL")
    private String rol;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cusuarioRol")
    private List<Tusuariorol> tusuariorolList;

    public CusuarioRol() {
    }

    public CusuarioRol(BigDecimal pkRol) {
        this.pkRol = pkRol;
    }

    public CusuarioRol(BigDecimal pkRol, String rol) {
        this.pkRol = pkRol;
        this.rol = rol;
    }

    public BigDecimal getPkRol() {
        return pkRol;
    }

    public void setPkRol(BigDecimal pkRol) {
        this.pkRol = pkRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Tusuariorol> getTusuariorolList() {
        return tusuariorolList;
    }

    public void setTusuariorolList(List<Tusuariorol> tusuariorolList) {
        this.tusuariorolList = tusuariorolList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkRol != null ? pkRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CusuarioRol)) {
            return false;
        }
        CusuarioRol other = (CusuarioRol) object;
        if ((this.pkRol == null && other.pkRol != null) || (this.pkRol != null && !this.pkRol.equals(other.pkRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CusuarioRol[ pkRol=" + pkRol + " ]";
    }
    
}
