package com.example.rutaaviar.vista.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rutaaviar.R;
import com.example.rutaaviar.modelo.entidades.Pajaro;

import java.util.ArrayList;


public class BirdAdapter extends ArrayAdapter<Pajaro> {
    private ArrayList<Pajaro> pajaros;
    public BirdAdapter(Context context, ArrayList<Pajaro> pajaros){
        super(context, R.layout.pajaro_element, pajaros);
        this.pajaros=pajaros;
    }

    public ArrayList<Pajaro> getPajaros() {
        return pajaros;
    }

    public void setPajaros(ArrayList<Pajaro> pajaros) {
        this.pajaros = pajaros;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater mostrado=LayoutInflater.from(getContext());
        View elemento=mostrado.inflate(R.layout.pajaro_element, parent, false);
        TextView text1=elemento.findViewById(R.id.PajaroNombre);
        text1.setText(pajaros.get(position).getNombre());
        TextView text5=elemento.findViewById(R.id.PajaroRaza);
        text5.setText(pajaros.get(position).getRaza());
        return elemento;
    }
}
