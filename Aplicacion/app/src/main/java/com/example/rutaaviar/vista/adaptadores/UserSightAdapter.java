package com.example.rutaaviar.vista.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rutaaviar.R;
import com.example.rutaaviar.modelo.entidades.Avistamiento;
import com.example.rutaaviar.modelo.entidades.Pajaro;

import java.util.ArrayList;

public class UserSightAdapter extends ArrayAdapter<Avistamiento> {
    private ArrayList<Avistamiento> avistamientos;

    public UserSightAdapter(Context context, ArrayList<Avistamiento> avisamientos){
        //super(context, R.layout.user_sight_element, avistamientos);
        super(context, R.layout.user_sight_element);

        this.avistamientos=avisamientos;
    }

    public ArrayList<Avistamiento> getAvistamientos() {
        return avistamientos;
    }

    public void setAvistamientos(ArrayList<Avistamiento> avistamientos) {
        this.avistamientos = avistamientos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater mostrado=LayoutInflater.from(getContext());

        View elemento=mostrado.inflate(R.layout.user_sight_element, parent, false);
        Avistamiento current = avistamientos.get(position);
        TextView text1=elemento.findViewById(R.id.sightBird);
//        text1.setText(avistamientos.get(position).getPajaro_id());

        TextView text2=elemento.findViewById(R.id.sightFecha);
        text2.setText(avistamientos.get(position).getFecha().toString());
        TextView text3=elemento.findViewById(R.id.sightLugar);
        text3.setText(avistamientos.get(position).getLugar().toString());

        return elemento;
    }
}
