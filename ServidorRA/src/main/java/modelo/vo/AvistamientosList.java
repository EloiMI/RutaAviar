/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.sql.Date;

/**
 *
 * @author catenaria
 */
public class AvistamientosList {
    public int id;
    public int usuarioId;
    public int pajaroId;
    public Date fecha;
    public double lat;
    public double lon;

    public AvistamientosList(int pajaroId, int usuarioId, Date fecha, double lon, double lat) {
        this.usuarioId = usuarioId;
        this.pajaroId = pajaroId;
        this.fecha = fecha;
        this.lat = lat;
        this.lon = lon;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getPajaroId() {
        return pajaroId;
    }

    public void setPajaroId(int pajaroId) {
        this.pajaroId = pajaroId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
    
    
}
