/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "TOPCIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Topciones.findAll", query = "SELECT t FROM Topciones t")
    , @NamedQuery(name = "Topciones.findByPkOpciones", query = "SELECT t FROM Topciones t WHERE t.pkOpciones = :pkOpciones")
    , @NamedQuery(name = "Topciones.findByOpcion", query = "SELECT t FROM Topciones t WHERE t.opcion = :opcion")
    , @NamedQuery(name = "Topciones.findByDescripcion", query = "SELECT t FROM Topciones t WHERE t.descripcion = :descripcion")})
public class Topciones implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "PK_OPCIONES")
    private BigDecimal pkOpciones;
    @Basic(optional = false)
    @Column(name = "OPCION")
    private String opcion;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "FK_AUSCULTACION", referencedColumnName = "PK_AUSCULTACION")
    @ManyToOne
    private Tauscultacion fkAuscultacion;

    public Topciones() {
    }

    public Topciones(BigDecimal pkOpciones) {
        this.pkOpciones = pkOpciones;
    }

    public Topciones(BigDecimal pkOpciones, String opcion) {
        this.pkOpciones = pkOpciones;
        this.opcion = opcion;
    }

    public BigDecimal getPkOpciones() {
        return pkOpciones;
    }

    public void setPkOpciones(BigDecimal pkOpciones) {
        this.pkOpciones = pkOpciones;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Tauscultacion getFkAuscultacion() {
        return fkAuscultacion;
    }

    public void setFkAuscultacion(Tauscultacion fkAuscultacion) {
        this.fkAuscultacion = fkAuscultacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkOpciones != null ? pkOpciones.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Topciones)) {
            return false;
        }
        Topciones other = (Topciones) object;
        if ((this.pkOpciones == null && other.pkOpciones != null) || (this.pkOpciones != null && !this.pkOpciones.equals(other.pkOpciones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Topciones[ pkOpciones=" + pkOpciones + " ]";
    }
    
}
