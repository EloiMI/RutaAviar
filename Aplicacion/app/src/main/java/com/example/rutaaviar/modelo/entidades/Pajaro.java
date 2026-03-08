package com.example.rutaaviar.modelo.entidades;

public class Pajaro {

    private String nombre;
    private String raza;

    private int id;

    public Pajaro(String nombre, String raza, int id) {
        this.nombre = nombre;
        this.raza = raza;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
