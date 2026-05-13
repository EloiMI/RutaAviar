package com.example.rutaaviar.vista.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.rutaaviar.R;
import com.example.rutaaviar.modelo.entidades.Pajaro;
import com.example.rutaaviar.rest.AccesoRest;
import com.example.rutaaviar.rest.AvesCallback;
import com.example.rutaaviar.vista.adaptadores.BirdAdapter;

import java.util.ArrayList;
import java.util.List;

public class BirdListFragment extends Fragment {
    private OnPajaroSelectedListener listener;
    private OnQuickSightListener quickSightListener;
    private OnBirdInfoListener birdInfoListener;
    private ArrayList<Pajaro> listaOriginal = new ArrayList<>();
    private ArrayList<Pajaro> listaFiltrada = new ArrayList<>();

    private BirdAdapter adapter;

    public BirdListFragment() {}

    public static BirdListFragment newInstance() {
        return new BirdListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_bird_list, container, false);
        ListView lista = vista.findViewById(R.id.listAFragment);

        new AccesoRest().listadoAves(new AvesCallback() {
            @Override
            public void onSuccess(List<Pajaro> pajaros) {
                requireActivity().runOnUiThread(() -> {
                    listaOriginal = new ArrayList<>(pajaros);
                    listaFiltrada = new ArrayList<>(listaOriginal);

                    adapter = new BirdAdapter(
                            getContext(),
                            listaFiltrada,
                            pajaro -> {
                                if (quickSightListener != null) {
                                    quickSightListener.onQuickSight(pajaro);
                                }
                            },
                            pajaro -> {
                                if (birdInfoListener != null) {
                                    birdInfoListener.onBirdInfo(pajaro);
                                }
                            }
                    );
                    lista.setAdapter(adapter);
                });
            }

            @Override
            public void onError(String error) {
                requireActivity().runOnUiThread(() -> {});
            }
        });

        lista.setOnItemClickListener((adapterView, view, i, l) -> {
            Pajaro pel = (Pajaro) adapterView.getItemAtPosition(i);
            if (listener != null) {
                listener.onPajaroSelected(pel);
            }
        });
        return vista;
    }

    public void filter(String text) {
        if (adapter == null) return;
        listaFiltrada.clear();
        if (text == null || text.isEmpty()) {
            listaFiltrada.addAll(listaOriginal);
        } else {
            text = text.toLowerCase();
            for (Pajaro p : listaOriginal) {
                if (p.getNombre().toLowerCase().contains(text)|| p.getRaza().toLowerCase().contains(text)) {
                    listaFiltrada.add(p);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    public interface OnPajaroSelectedListener {
        void onPajaroSelected(Pajaro ave);
    }

    public interface OnQuickSightListener {
        void onQuickSight(Pajaro ave);
    }

    public interface OnBirdInfoListener {
        void onBirdInfo(Pajaro ave);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnPajaroSelectedListener
                && context instanceof OnQuickSightListener
                && context instanceof OnBirdInfoListener) {

            listener = (OnPajaroSelectedListener) context;
            quickSightListener = (OnQuickSightListener) context;
            birdInfoListener = (OnBirdInfoListener) context;

        } else {
            throw new ClassCastException(
                    context.toString()
                            + " debe implementar los listeners requeridos"
            );
        }
    }
}