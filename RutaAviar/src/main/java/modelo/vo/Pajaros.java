/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

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

/**
 *
 * @author catenaria
 */
@Entity
@Table(name = "pajaros")
@NamedQueries({
    @NamedQuery(name = "Pajaros.findAll", query = "SELECT p FROM Pajaros p")})
public class Pajaros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "raza")
    private String raza;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pajaroId")
    private List<Avistamientos> avistamientosList;

    public Pajaros() {
    }

    public Pajaros(Integer id) {
        this.id = id;
    }

    public Pajaros(Integer id, String nombre, String raza) {
        this.id = id;
        this.nombre = nombre;
        this.raza = raza;
    }
    
    public Pajaros(String nombre, String raza) {
        this.id = id;
        this.nombre = nombre;
        this.raza = raza;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public List<Avistamientos> getAvistamientosList() {
        return avistamientosList;
    }

    public void setAvistamientosList(List<Avistamientos> avistamientosList) {
        this.avistamientosList = avistamientosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pajaros)) {
            return false;
        }
        Pajaros other = (Pajaros) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.vo.Pajaros[ id=" + id + " ]";
    }
    
}
