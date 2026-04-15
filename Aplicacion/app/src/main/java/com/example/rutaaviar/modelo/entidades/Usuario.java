package com.example.rutaaviar.modelo.entidades;

public class Usuario {

        private int id;
        private String nombre;
        private String password;

        private boolean admin;

        public Usuario(int id, String nombre, String password, boolean admin) {
            this.id=id;
            this.nombre = nombre;
            this.password = password;
            this.admin = admin;
        }

    public Usuario(String nombre, String password, boolean admin) {
        this.nombre = nombre;
        this.password = password;
        this.admin = admin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
