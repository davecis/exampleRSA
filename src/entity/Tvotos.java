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
import javax.persistence.Lob;
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
@Table(name = "TVOTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tvotos.findAll", query = "SELECT t FROM Tvotos t")
    , @NamedQuery(name = "Tvotos.findByPkVoto", query = "SELECT t FROM Tvotos t WHERE t.pkVoto = :pkVoto")})
public class Tvotos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "PK_VOTO")
    private BigDecimal pkVoto;
    @Basic(optional = false)
    @Lob
    @Column(name = "VALOR")
    private Object valor;
    @JoinColumn(name = "FK_AUSCULTACION", referencedColumnName = "PK_AUSCULTACION")
    @ManyToOne
    private Tauscultacion fkAuscultacion;

    public Tvotos() {
    }

    public Tvotos(BigDecimal pkVoto) {
        this.pkVoto = pkVoto;
    }

    public Tvotos(BigDecimal pkVoto, Object valor) {
        this.pkVoto = pkVoto;
        this.valor = valor;
    }

    public BigDecimal getPkVoto() {
        return pkVoto;
    }

    public void setPkVoto(BigDecimal pkVoto) {
        this.pkVoto = pkVoto;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
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
        hash += (pkVoto != null ? pkVoto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tvotos)) {
            return false;
        }
        Tvotos other = (Tvotos) object;
        if ((this.pkVoto == null && other.pkVoto != null) || (this.pkVoto != null && !this.pkVoto.equals(other.pkVoto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Tvotos[ pkVoto=" + pkVoto + " ]";
    }
    
}
