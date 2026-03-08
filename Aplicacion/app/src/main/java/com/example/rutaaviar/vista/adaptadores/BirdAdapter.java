package com.example.rutaaviar.vista.adaptadores;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.rutaaviar.modelo.entidades.Pajaro;


public class BirdAdapter extends ArrayAdapter<Pajaro> {
    public BirdAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
