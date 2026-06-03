package com.example.rutaaviar.modelo.entidades;

import java.util.Date;

public class Avistamiento {
    private int id;
    private int usuarioId;
    private int pajaroId;
    private Date fecha;

    private double lat;
    private double lon;
    public Avistamiento(int id, int idp, int idu, Date date, double lat, double lon){
        this.id=id;
        this.pajaroId =idu;
        this.usuarioId =idp;
        this.fecha=date;
        this.lat =lat;
        this.lon =lon;
    }

    public Avistamiento(int idp, int idu, Date date, double lat, double lon){
        this.pajaroId =idu;
        this.usuarioId =idp;
        this.fecha=date;
        this.lat =lat;
        this.lon =lon;
    }
    public Avistamiento(int idp, int idu, double lon, double lat){
        this.pajaroId =idu;
        this.usuarioId =idp;
        this.lon =lon;
        this.lat =lat;
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

    public double getLat() {return lat;}

    public void setLat(double lat) {this.lat = lat;}

    public double getLon() {return lon;}

    public void setLon(double lon) {this.lon = lon;}
}
