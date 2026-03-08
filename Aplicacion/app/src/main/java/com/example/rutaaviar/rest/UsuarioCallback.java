package com.example.rutaaviar.rest;

import com.example.rutaaviar.modelo.entidades.Usuario;

public interface UsuarioCallback {
    void onSuccess(Usuario usuario);
    void onError(String error);
}
