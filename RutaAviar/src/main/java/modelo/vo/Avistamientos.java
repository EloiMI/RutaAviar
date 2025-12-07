/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author catenaria
 */
@Entity
@Table(name = "avistamientos")
@NamedQueries({
    @NamedQuery(name = "Avistamientos.findAll", query = "SELECT a FROM Avistamientos a")})
public class Avistamientos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "lugar")
    private String lugar;
    @Basic(optional = false)
    @Column(name = "fecha_avistamiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAvistamiento;
    @JoinColumn(name = "pajaro_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Pajaros pajaroId;
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuarios usuarioId;

    public Avistamientos() {
    }

    public Avistamientos(Integer id) {
        this.id = id;
    }

    public Avistamientos(Integer id, String lugar, Date fechaAvistamiento) {
        this.id = id;
        this.lugar = lugar;
        this.fechaAvistamiento = fechaAvistamiento;
    }
    
    public Avistamientos(Pajaros p, Usuarios u, String lugar, Date fechaAvistamiento) {
        this.pajaroId=p;
        this.usuarioId=u;
        this.lugar = lugar;
        this.fechaAvistamiento = fechaAvistamiento;
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Date getFechaAvistamiento() {
        return fechaAvistamiento;
    }

    public void setFechaAvistamiento(Date fechaAvistamiento) {
        this.fechaAvistamiento = fechaAvistamiento;
    }

    public Pajaros getPajaroId() {
        return pajaroId;
    }

    public void setPajaroId(Pajaros pajaroId) {
        this.pajaroId = pajaroId;
    }

    public Usuarios getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuarios usuarioId) {
        this.usuarioId = usuarioId;
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
        if (!(object instanceof Avistamientos)) {
            return false;
        }
        Avistamientos other = (Avistamientos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.vo.Avistamientos[ id=" + id + " ]";
    }
    
}
