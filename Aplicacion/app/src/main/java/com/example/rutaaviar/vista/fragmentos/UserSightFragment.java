package com.example.rutaaviar.vista.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.rutaaviar.R;
import com.example.rutaaviar.modelo.entidades.Avistamiento;
import com.example.rutaaviar.modelo.entidades.Pajaro;
import com.example.rutaaviar.rest.AccesoRest;
import com.example.rutaaviar.rest.AvesCallback;
import com.example.rutaaviar.rest.AvistamientoCallback;
import com.example.rutaaviar.vista.adaptadores.BirdAdapter;
import com.example.rutaaviar.vista.adaptadores.UserSightAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserSightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserSightFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private UserSightAdapter adapter;

    public UserSightFragment() {}
    public static UserSightFragment newInstance() {
        return new UserSightFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_bird_list, container, false);
        ListView lista = vista.findViewById(R.id.listAFragment);

        new AccesoRest().listadoAvistamientosUsuario(1, new AvistamientoCallback() {
            @Override
            public void onSuccess(ArrayList<Avistamiento> avistamientos) {
                requireActivity().runOnUiThread(() -> {
                    //adapter = new UserSightAdapter(getContext(), avistamientos);
                    adapter = new UserSightAdapter(getContext(), avistamientos);
                    lista.setAdapter(adapter);
                });
            }

            @Override
            public void onError(String error) {
                requireActivity().runOnUiThread(() -> {});
            }
        });

        return vista;
    }

}