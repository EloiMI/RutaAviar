/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Date;

/**
 *
 * @author catenaria
 */
public class AvistamientosUser {
    public int id;
     @JsonProperty("pajaro")
    public String pNombre;
    public String pRaza;  
    public java.sql.Date fecha;
    public double lat;
    public double lon;

    public AvistamientosUser(String pNom, String pRaz, java.sql.Date fecha, double lon, double lat) {
        this.pNombre= pNom;
        this.pRaza = pRaz;
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

    public String getpNombre() {
        return pNombre;
    }

    public void setpNombre(String pNombre) {
        this.pNombre = pNombre;
    }

    public String getpRaza() {
        return pRaza;
    }

    public void setpRaza(String pRaza) {
        this.pRaza = pRaza;
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