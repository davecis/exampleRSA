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
@Table(name = "TSYSUSER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tsysuser.findAll", query = "SELECT t FROM Tsysuser t")
    , @NamedQuery(name = "Tsysuser.findByPkUsername", query = "SELECT t FROM Tsysuser t WHERE t.pkUsername = :pkUsername")
    , @NamedQuery(name = "Tsysuser.findByUsername", query = "SELECT t FROM Tsysuser t WHERE t.username = :username")})
public class Tsysuser implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "PK_USERNAME")
    private BigDecimal pkUsername;
    @Basic(optional = false)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @Lob
    @Column(name = "PASSWORD")
    private Object password;
    @JoinColumn(name = "FK_USUARIO", referencedColumnName = "PK_USUARIO")
    @ManyToOne
    private Tusuario fkUsuario;

    public Tsysuser() {
    }

    public Tsysuser(BigDecimal pkUsername) {
        this.pkUsername = pkUsername;
    }

    public Tsysuser(BigDecimal pkUsername, String username, Object password) {
        this.pkUsername = pkUsername;
        this.username = username;
        this.password = password;
    }

    public BigDecimal getPkUsername() {
        return pkUsername;
    }

    public void setPkUsername(BigDecimal pkUsername) {
        this.pkUsername = pkUsername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public Tusuario getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(Tusuario fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkUsername != null ? pkUsername.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tsysuser)) {
            return false;
        }
        Tsysuser other = (Tsysuser) object;
        if ((this.pkUsername == null && other.pkUsername != null) || (this.pkUsername != null && !this.pkUsername.equals(other.pkUsername))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Tsysuser[ pkUsername=" + pkUsername + " ]";
    }
    
}
