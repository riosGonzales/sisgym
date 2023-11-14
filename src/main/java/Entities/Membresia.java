/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "membresia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Membresia.findAll", query = "SELECT m FROM Membresia m"),
    @NamedQuery(name = "Membresia.findByIdMembresia", query = "SELECT m FROM Membresia m WHERE m.idMembresia = :idMembresia"),
    @NamedQuery(name = "Membresia.findByTipoMembresia", query = "SELECT m FROM Membresia m WHERE m.tipoMembresia = :tipoMembresia"),
    @NamedQuery(name = "Membresia.findByDuracionMembresia", query = "SELECT m FROM Membresia m WHERE m.duracionMembresia = :duracionMembresia")})
public class Membresia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMembresia")
    private Integer idMembresia;
    @Size(max = 45)
    @Column(name = "TipoMembresia")
    private String tipoMembresia;
    @Size(max = 45)
    @Column(name = "DuracionMembresia")
    private String duracionMembresia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMembresia")
    private List<Matricula> matriculaList;

    public Membresia() {
    }

    public Membresia(Integer idMembresia) {
        this.idMembresia = idMembresia;
    }

    public Integer getIdMembresia() {
        return idMembresia;
    }

    public void setIdMembresia(Integer idMembresia) {
        this.idMembresia = idMembresia;
    }

    public String getTipoMembresia() {
        return tipoMembresia;
    }

    public void setTipoMembresia(String tipoMembresia) {
        this.tipoMembresia = tipoMembresia;
    }

    public String getDuracionMembresia() {
        return duracionMembresia;
    }

    public void setDuracionMembresia(String duracionMembresia) {
        this.duracionMembresia = duracionMembresia;
    }

    @XmlTransient
    public List<Matricula> getMatriculaList() {
        return matriculaList;
    }

    public void setMatriculaList(List<Matricula> matriculaList) {
        this.matriculaList = matriculaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMembresia != null ? idMembresia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Membresia)) {
            return false;
        }
        Membresia other = (Membresia) object;
        if ((this.idMembresia == null && other.idMembresia != null) || (this.idMembresia != null && !this.idMembresia.equals(other.idMembresia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Membresia[ idMembresia=" + idMembresia + " ]";
    }
    
}
