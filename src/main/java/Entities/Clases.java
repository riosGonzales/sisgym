package Entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author wtke9
 */
@Entity
@Table(name = "clases")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clases.findAll", query = "SELECT c FROM Clases c"),
    @NamedQuery(name = "Clases.findByIdClases", query = "SELECT c FROM Clases c WHERE c.idClases = :idClases"),
    @NamedQuery(name = "Clases.findByInstructor", query = "SELECT c FROM Clases c WHERE c.instructor = :instructor"),
    @NamedQuery(name = "Clases.findByFecha", query = "SELECT c FROM Clases c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "Clases.findByHorario", query = "SELECT c FROM Clases c WHERE c.horario = :horario"),
    @NamedQuery(name = "Clases.findByDescripcion", query = "SELECT c FROM Clases c WHERE c.descripcion = :descripcion")})
public class Clases implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idClases")
    private Integer idClases;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "instructor")
    private String instructor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "horario")
    private String horario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descripcion")
    private String descripcion;

    public Clases() {
    }

    public Clases(Integer idClases) {
        this.idClases = idClases;
    }

    public Clases(Integer idClases, String instructor, Date fecha, String horario, String descripcion) {
        this.idClases = idClases;
        this.instructor = instructor;
        this.fecha = fecha;
        this.horario = horario;
        this.descripcion = descripcion;
    }

    public Integer getIdClases() {
        return idClases;
    }

    public void setIdClases(Integer idClases) {
        this.idClases = idClases;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClases != null ? idClases.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clases)) {
            return false;
        }
        Clases other = (Clases) object;
        if ((this.idClases == null && other.idClases != null) || (this.idClases != null && !this.idClases.equals(other.idClases))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Clases[ idClases=" + idClases + " ]";
    }

}
