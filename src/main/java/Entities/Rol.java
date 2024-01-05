package Entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "rol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r"),
    @NamedQuery(name = "Rol.findByCodRol", query = "SELECT r FROM Rol r WHERE r.codRol = :codRol"),
    @NamedQuery(name = "Rol.findByNombRol", query = "SELECT r FROM Rol r WHERE r.nombRol = :nombRol"),
    @NamedQuery(name = "Rol.findByFechRol", query = "SELECT r FROM Rol r WHERE r.fechRol = :fechRol")})
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codRol")
    private Integer codRol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombRol")
    private String nombRol;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechRol")
    @Temporal(TemporalType.DATE)
    private Date fechRol;

    public Rol() {
    }

    public Rol(Integer codRol) {
        this.codRol = codRol;
    }

    public Rol(Integer codRol, String nombRol, Date fechRol) {
        this.codRol = codRol;
        this.nombRol = nombRol;
        this.fechRol = fechRol;
    }

    public Integer getCodRol() {
        return codRol;
    }

    public void setCodRol(Integer codRol) {
        this.codRol = codRol;
    }

    public String getNombRol() {
        return nombRol;
    }

    public void setNombRol(String nombRol) {
        this.nombRol = nombRol;
    }

    public Date getFechRol() {
        return fechRol;
    }

    public void setFechRol(Date fechRol) {
        this.fechRol = fechRol;
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
        if (!(object instanceof Rol)) {
            return false;
        }
        Rol other = (Rol) object;
        if ((this.codRol == null && other.codRol != null) || (this.codRol != null && !this.codRol.equals(other.codRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Rol[ codRol=" + codRol + " ]";
    }

}
