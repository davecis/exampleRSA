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
@Table(name = "CAUSCULTACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Causcultacion.findAll", query = "SELECT c FROM Causcultacion c")
    , @NamedQuery(name = "Causcultacion.findByPkCauscultacion", query = "SELECT c FROM Causcultacion c WHERE c.pkCauscultacion = :pkCauscultacion")
    , @NamedQuery(name = "Causcultacion.findByNombre", query = "SELECT c FROM Causcultacion c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Causcultacion.findByDescripcion", query = "SELECT c FROM Causcultacion c WHERE c.descripcion = :descripcion")})
public class Causcultacion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "PK_CAUSCULTACION")
    private BigDecimal pkCauscultacion;
    
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @OneToMany(mappedBy = "fkCauscultacion")
    private List<Tauscultacion> tauscultacionList;

    public Causcultacion() {
    }

    public Causcultacion(BigDecimal pkCauscultacion) {
        this.pkCauscultacion = pkCauscultacion;
    }

    public Causcultacion(BigDecimal pkCauscultacion, String nombre) {
        this.pkCauscultacion = pkCauscultacion;
        this.nombre = nombre;
    }

    public BigDecimal getPkCauscultacion() {
        return pkCauscultacion;
    }

    public void setPkCauscultacion(BigDecimal pkCauscultacion) {
        this.pkCauscultacion = pkCauscultacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Tauscultacion> getTauscultacionList() {
        return tauscultacionList;
    }

    public void setTauscultacionList(List<Tauscultacion> tauscultacionList) {
        this.tauscultacionList = tauscultacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkCauscultacion != null ? pkCauscultacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Causcultacion)) {
            return false;
        }
        Causcultacion other = (Causcultacion) object;
        if ((this.pkCauscultacion == null && other.pkCauscultacion != null) || (this.pkCauscultacion != null && !this.pkCauscultacion.equals(other.pkCauscultacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Causcultacion[ pkCauscultacion=" + pkCauscultacion + " ]";
    }
    
}
