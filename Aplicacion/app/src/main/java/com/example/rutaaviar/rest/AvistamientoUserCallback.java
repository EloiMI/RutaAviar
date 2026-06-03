package com.example.rutaaviar.rest;

import com.example.rutaaviar.modelo.entidades.Avistamiento;
import com.example.rutaaviar.modelo.entidades.AvistamientoU;

import java.util.ArrayList;

public interface AvistamientoUserCallback {
    void onSuccess(ArrayList<AvistamientoU> ave);
    void onError(String error);
}
