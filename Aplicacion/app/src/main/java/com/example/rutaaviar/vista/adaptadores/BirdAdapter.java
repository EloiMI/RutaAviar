package com.example.rutaaviar.vista.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rutaaviar.R;
import com.example.rutaaviar.modelo.entidades.Pajaro;

import java.util.ArrayList;


public class BirdAdapter extends ArrayAdapter<Pajaro> {

    public interface OnQuickSightClickListener {
        void onSight(Pajaro pajaro);

    }

    public interface OnBirdInfoClickListener {
        void onInfo(Pajaro pajaro);

    }
    private ArrayList<Pajaro> pajaros;
    private OnQuickSightClickListener listener;
    private OnBirdInfoClickListener listenerI;

    public BirdAdapter(Context context, ArrayList<Pajaro> pajaros, OnQuickSightClickListener listener, OnBirdInfoClickListener listenerI){
        super(context, R.layout.pajaro_element, pajaros);
        this.pajaros=pajaros;
        this.listener = listener;
        this.listenerI = listenerI;
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
        Pajaro current = pajaros.get(position);
        ImageButton quickSight = elemento.findViewById(R.id.quickSight);
        ImageButton birdInfo = elemento.findViewById(R.id.birdInfo);
        TextView text1=elemento.findViewById(R.id.PajaroNombre);
        text1.setText(pajaros.get(position).getNombre());
        TextView text5=elemento.findViewById(R.id.PajaroRaza);
        text5.setText(pajaros.get(position).getRaza());
        quickSight.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSight(current);
            }
        });
        birdInfo.setOnClickListener(v -> {
            if (listenerI != null) {
                listenerI.onInfo(current);
            }
        });
        quickSight.setClickable(true);
        birdInfo.setClickable(true);
        return elemento;
    }
}
