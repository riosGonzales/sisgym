package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author wtke9
 */
@Entity
@Table(name = "rolusuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rolusuario.findAll", query = "SELECT r FROM Rolusuario r"),
    @NamedQuery(name = "Rolusuario.findByCodRol", query = "SELECT r FROM Rolusuario r WHERE r.codRol = :codRol"),
    @NamedQuery(name = "Rolusuario.findByCodiUsua", query = "SELECT r FROM Rolusuario r WHERE r.codiUsua = :codiUsua"),
    @NamedQuery(name = "Rolusuario.findByEstado", query = "SELECT r FROM Rolusuario r WHERE r.actvRol = :actvRol"),
    @NamedQuery(name = "Rolusuario.findByActvRol", query = "SELECT r FROM Rolusuario r WHERE r.actvRol = :actvRol")})
public class Rolusuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codRol")
    private Integer codRol;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiUsua")
    private int codiUsua;
    @Basic(optional = false)
    @NotNull
    @Column(name = "actvRol")
    private boolean actvRol;

    public Rolusuario() {
    }

    public Rolusuario(Integer codRol) {
        this.codRol = codRol;
    }

    public Rolusuario(Integer codRol, int codiUsua, boolean actvRol) {
        this.codRol = codRol;
        this.codiUsua = codiUsua;
        this.actvRol = actvRol;
    }

    public Integer getCodRol() {
        return codRol;
    }

    public void setCodRol(Integer codRol) {
        this.codRol = codRol;
    }

    public int getCodiUsua() {
        return codiUsua;
    }

    public void setCodiUsua(int codiUsua) {
        this.codiUsua = codiUsua;
    }

    public boolean getActvRol() {
        return actvRol;
    }

    public void setActvRol(boolean actvRol) {
        this.actvRol = actvRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codRol != null ? codRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rolusuario)) {
            return false;
        }
        Rolusuario other = (Rolusuario) object;
        if ((this.codRol == null && other.codRol != null) || (this.codRol != null && !this.codRol.equals(other.codRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Rolusuario[ codRol=" + codRol + " ]";
    }

}
