package com.example.rutaaviar.modelo.entidades;

import java.util.Date;

public class Avistamiento {
    private int id;
    private int pajaro_id;
    private int usuario_id;
    private Date fecha;
    private Lugar lugar;
    public Avistamiento(int id, int idp, int idu, Date date, Lugar lug){
        this.id=id;
        this.usuario_id=idu;
        this.pajaro_id=idp;
        this.fecha=date;
        this.lugar=lug;
    }

    public Avistamiento(int idp, int idu, Date date, Lugar lug){
        this.usuario_id=idu;
        this.pajaro_id=idp;
        this.fecha=date;
        this.lugar=lug;
    }
    public Avistamiento(int idp, int idu, Lugar lug){
        this.usuario_id=idu;
        this.pajaro_id=idp;
        this.lugar=lug;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPajaro_id() {
        return pajaro_id;
    }

    public void setPajaro_id(int pajaro_id) {
        this.pajaro_id = pajaro_id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Lugar getLugar() {
        return lugar;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }
}
