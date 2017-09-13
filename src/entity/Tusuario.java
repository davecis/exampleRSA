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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "TUSUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tusuario.findAll", query = "SELECT t FROM Tusuario t")
    , @NamedQuery(name = "Tusuario.findByPkUsuario", query = "SELECT t FROM Tusuario t WHERE t.pkUsuario = :pkUsuario")
    , @NamedQuery(name = "Tusuario.findByNombre", query = "SELECT t FROM Tusuario t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "Tusuario.findByApPaterno", query = "SELECT t FROM Tusuario t WHERE t.apPaterno = :apPaterno")
    , @NamedQuery(name = "Tusuario.findByApMaterno", query = "SELECT t FROM Tusuario t WHERE t.apMaterno = :apMaterno")
    , @NamedQuery(name = "Tusuario.findByBoleta", query = "SELECT t FROM Tusuario t WHERE t.boleta = :boleta")})
public class Tusuario implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "PK_USUARIO")
    private BigDecimal pkUsuario;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "AP_PATERNO")
    private String apPaterno;
    @Basic(optional = false)
    @Column(name = "AP_MATERNO")
    private String apMaterno;
    @Basic(optional = false)
    @Column(name = "BOLETA")
    private String boleta;
    @JoinTable(name = "TUSUARIOADMINISTRA", joinColumns = {
        @JoinColumn(name = "PK_USUARIO", referencedColumnName = "PK_USUARIO")}, inverseJoinColumns = {
        @JoinColumn(name = "PK_AUSCULTACION", referencedColumnName = "PK_AUSCULTACION")})
    @ManyToMany
    private List<Tauscultacion> tauscultacionList;
    @OneToMany(mappedBy = "fkUsuario")
    private List<Tsysuser> tsysuserList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tusuario")
    private List<TauscultacionParticipante> tauscultacionParticipanteList;

    public Tusuario() {
    }

    public Tusuario(BigDecimal pkUsuario) {
        this.pkUsuario = pkUsuario;
    }

    public Tusuario(BigDecimal pkUsuario, String nombre, String apPaterno, String apMaterno, String boleta) {
        this.pkUsuario = pkUsuario;
        this.nombre = nombre;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.boleta = boleta;
    }

    public BigDecimal getPkUsuario() {
        return pkUsuario;
    }

    public void setPkUsuario(BigDecimal pkUsuario) {
        this.pkUsuario = pkUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public String getBoleta() {
        return boleta;
    }

    public void setBoleta(String boleta) {
        this.boleta = boleta;
    }

    @XmlTransient
    public List<Tauscultacion> getTauscultacionList() {
        return tauscultacionList;
    }

    public void setTauscultacionList(List<Tauscultacion> tauscultacionList) {
        this.tauscultacionList = tauscultacionList;
    }

    @XmlTransient
    public List<Tsysuser> getTsysuserList() {
        return tsysuserList;
    }

    public void setTsysuserList(List<Tsysuser> tsysuserList) {
        this.tsysuserList = tsysuserList;
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
        hash += (pkUsuario != null ? pkUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tusuario)) {
            return false;
        }
        Tusuario other = (Tusuario) object;
        if ((this.pkUsuario == null && other.pkUsuario != null) || (this.pkUsuario != null && !this.pkUsuario.equals(other.pkUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Tusuario[ pkUsuario=" + pkUsuario + " ]";
    }
    
}
