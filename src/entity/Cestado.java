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
@Table(name = "CESTADO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cestado.findAll", query = "SELECT c FROM Cestado c")
    , @NamedQuery(name = "Cestado.findByPkcatalogo", query = "SELECT c FROM Cestado c WHERE c.pkcatalogo = :pkcatalogo")
    , @NamedQuery(name = "Cestado.findByEstado", query = "SELECT c FROM Cestado c WHERE c.estado = :estado")
    , @NamedQuery(name = "Cestado.findByDetalle", query = "SELECT c FROM Cestado c WHERE c.detalle = :detalle")})
public class Cestado implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "PKCATALOGO")
    private BigDecimal pkcatalogo;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "DETALLE")
    private String detalle;
    @OneToMany(mappedBy = "fkCatalogo")
    private List<Tauscultacion> tauscultacionList;
    @OneToMany(mappedBy = "fkestado")
    private List<TauscultacionParticipante> tauscultacionParticipanteList;

    public Cestado() {
    }

    public Cestado(BigDecimal pkcatalogo) {
        this.pkcatalogo = pkcatalogo;
    }

    public Cestado(BigDecimal pkcatalogo, String estado) {
        this.pkcatalogo = pkcatalogo;
        this.estado = estado;
    }

    public BigDecimal getPkcatalogo() {
        return pkcatalogo;
    }

    public void setPkcatalogo(BigDecimal pkcatalogo) {
        this.pkcatalogo = pkcatalogo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    @XmlTransient
    public List<Tauscultacion> getTauscultacionList() {
        return tauscultacionList;
    }

    public void setTauscultacionList(List<Tauscultacion> tauscultacionList) {
        this.tauscultacionList = tauscultacionList;
    }

    @XmlTransient
    public List<TauscultacionParticipante> getTauscultacionParticipanteList() {
        return tauscultacionParticipanteList;
    }

    public void setTauscultacionParticipanteList(List<TauscultacionParticipante> tauscultacionParticipanteList) {
        this.tauscultacionParticipanteList = tauscultacionParticipanteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkcatalogo != null ? pkcatalogo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cestado)) {
            return false;
        }
        Cestado other = (Cestado) object;
        if ((this.pkcatalogo == null && other.pkcatalogo != null) || (this.pkcatalogo != null && !this.pkcatalogo.equals(other.pkcatalogo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Cestado[ pkcatalogo=" + pkcatalogo + " ]";
    }
    
}
