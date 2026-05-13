package com.example.rutaaviar.rest;

import com.example.rutaaviar.modelo.entidades.Avistamiento;

import java.util.ArrayList;

public interface AvistamientoCreadoCallback {
    void onSuccess(String success);
    void onError(String error);
}
