package com.example.rutaaviar.rest;

import com.example.rutaaviar.modelo.entidades.Pajaro;

import java.util.List;

public interface AvesCallback {
    void onSuccess(List<Pajaro> ave);
    void onError(String error);
}
