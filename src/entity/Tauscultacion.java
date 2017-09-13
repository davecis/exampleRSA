/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author David
 */
@Entity
@Table(name = "TAUSCULTACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tauscultacion.findAll", query = "SELECT t FROM Tauscultacion t")
    , @NamedQuery(name = "Tauscultacion.findByPkAuscultacion", query = "SELECT t FROM Tauscultacion t WHERE t.pkAuscultacion = :pkAuscultacion")
    , @NamedQuery(name = "Tauscultacion.findByFechaInicio", query = "SELECT t FROM Tauscultacion t WHERE t.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Tauscultacion.findByFechaFinal", query = "SELECT t FROM Tauscultacion t WHERE t.fechaFinal = :fechaFinal")})
public class Tauscultacion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "PK_AUSCULTACION")
    private BigDecimal pkAuscultacion;
    @Basic(optional = false)
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "FECHA_FINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinal;
    @ManyToMany(mappedBy = "tauscultacionList")
    private List<Tusuario> tusuarioList;
    @OneToMany(mappedBy = "fkAuscultacion")
    private List<Tvotos> tvotosList;
    @JoinColumn(name = "FK_CAUSCULTACION", referencedColumnName = "PK_CAUSCULTACION")
    @ManyToOne
    private Causcultacion fkCauscultacion;
    @JoinColumn(name = "FK_CATALOGO", referencedColumnName = "PKCATALOGO")
    @ManyToOne
    private Cestado fkCatalogo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tauscultacion")
    private List<TauscultacionParticipante> tauscultacionParticipanteList;
    @OneToMany(mappedBy = "fkAuscultacion")
    private List<Topciones> topcionesList;

    public Tauscultacion() {
    }

    public Tauscultacion(BigDecimal pkAuscultacion) {
        this.pkAuscultacion = pkAuscultacion;
    }

    public Tauscultacion(BigDecimal pkAuscultacion, Date fechaInicio, Date fechaFinal) {
        this.pkAuscultacion = pkAuscultacion;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
    }

    public BigDecimal getPkAuscultacion() {
        return pkAuscultacion;
    }

    public void setPkAuscultacion(BigDecimal pkAuscultacion) {
        this.pkAuscultacion = pkAuscultacion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    @XmlTransient
    public List<Tusuario> getTusuarioList() {
        return tusuarioList;
    }

    public void setTusuarioList(List<Tusuario> tusuarioList) {
        this.tusuarioList = tusuarioList;
    }

    @XmlTransient
    public List<Tvotos> getTvotosList() {
        return tvotosList;
    }

    public void setTvotosList(List<Tvotos> tvotosList) {
        this.tvotosList = tvotosList;
    }

    public Causcultacion getFkCauscultacion() {
        return fkCauscultacion;
    }

    public void setFkCauscultacion(Causcultacion fkCauscultacion) {
        this.fkCauscultacion = fkCauscultacion;
    }

    public Cestado getFkCatalogo() {
        return fkCatalogo;
    }

    public void setFkCatalogo(Cestado fkCatalogo) {
        this.fkCatalogo = fkCatalogo;
    }

    @XmlTransient
    public List<TauscultacionParticipante> getTauscultacionParticipanteList() {
        return tauscultacionParticipanteList;
    }

    public void setTauscultacionParticipanteList(List<TauscultacionParticipante> tauscultacionParticipanteList) {
        this.tauscultacionParticipanteList = tauscultacionParticipanteList;
    }

    @XmlTransient
    public List<Topciones> getTopcionesList() {
        return topcionesList;
    }

    public void setTopcionesList(List<Topciones> topcionesList) {
        this.topcionesList = topcionesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkAuscultacion != null ? pkAuscultacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tauscultacion)) {
            return false;
        }
        Tauscultacion other = (Tauscultacion) object;
        if ((this.pkAuscultacion == null && other.pkAuscultacion != null) || (this.pkAuscultacion != null && !this.pkAuscultacion.equals(other.pkAuscultacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Tauscultacion[ pkAuscultacion=" + pkAuscultacion + " ]";
    }
    
}
