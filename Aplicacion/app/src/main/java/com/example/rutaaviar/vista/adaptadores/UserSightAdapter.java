package com.example.rutaaviar.vista.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rutaaviar.R;
import com.example.rutaaviar.modelo.entidades.Avistamiento;
import com.example.rutaaviar.modelo.entidades.AvistamientoU;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class UserSightAdapter extends ArrayAdapter<AvistamientoU> {
    private ArrayList<AvistamientoU> avistamientos;
    private Context context;
    private OnSightClickListener listener;

    public interface OnSightClickListener {
        void onSightClick(AvistamientoU item);
    }

    public UserSightAdapter(Context context, ArrayList<AvistamientoU> avistamientos, OnSightClickListener listener) {
        super(context, 0, avistamientos);
        this.context = context;
        this.avistamientos = avistamientos;
        this.listener=listener;
    }

    @Override
    public int getCount() {
        return avistamientos.size();
    }

    @Override
    public AvistamientoU getItem(int position) {
        return avistamientos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.user_sight_element, parent, false);
        }
        AvistamientoU current = avistamientos.get(position);
        TextView text1 = convertView.findViewById(R.id.sightBird);
        text1.setText(current.getPajaro()+"-"+current.getpRaza());
        /*
        TextView text4 = convertView.findViewById(R.id.sightRaza);
        text4.setText(current.getpRaza());
        */
        TextView text2 = convertView.findViewById(R.id.sightFecha);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fechaFormateada = sdf.format(current.getFecha());
        text2.setText("Fecha " + fechaFormateada);
        TextView text3 = convertView.findViewById(R.id.sightLugar);
        text3.setText("lon: " + current.getLat()+" lat: " + current.getLon());
        convertView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSightClick(current);
            }
        });
        return convertView;
    }
}
