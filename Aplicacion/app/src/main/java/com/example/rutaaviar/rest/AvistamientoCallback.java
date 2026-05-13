package com.example.rutaaviar.rest;

import com.example.rutaaviar.modelo.entidades.Avistamiento;

import java.util.ArrayList;
import java.util.List;

public interface AvistamientoCallback {
    void onSuccess(ArrayList<Avistamiento> ave);
    void onError(String error);
}
