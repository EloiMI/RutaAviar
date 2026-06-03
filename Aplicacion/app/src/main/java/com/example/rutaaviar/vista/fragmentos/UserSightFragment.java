package com.example.rutaaviar.vista.fragmentos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.rutaaviar.R;
import com.example.rutaaviar.modelo.entidades.Avistamiento;
import com.example.rutaaviar.modelo.entidades.AvistamientoU;
import com.example.rutaaviar.modelo.entidades.Pajaro;
import com.example.rutaaviar.rest.AccesoRest;
import com.example.rutaaviar.rest.AvesCallback;
import com.example.rutaaviar.rest.AvistamientoCallback;
import com.example.rutaaviar.rest.AvistamientoUserCallback;
import com.example.rutaaviar.rest.GuardarUser;
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
    private ListView lista;
    private OnSightSelectedListener listener;

    private ArrayList<AvistamientoU> listaOriginal = new ArrayList<>();
    private ArrayList<AvistamientoU> listaFiltrada = new ArrayList<>();

    private static ArrayList<AvistamientoU> listaAvistamientos = new ArrayList<>();

    public UserSightFragment() {}
    public static UserSightFragment newInstance() {
        return new UserSightFragment();
    }

    public static UserSightFragment newInstance(ArrayList<AvistamientoU> avistamientos) {
        UserSightFragment fragment = new UserSightFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", avistamientos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_user_sight, container, false);
        lista = vista.findViewById(R.id.listSFragment);

        ArrayList<AvistamientoU> data = (ArrayList<AvistamientoU>) getArguments().getSerializable("data");

        if(data!=null){
            listaOriginal.clear();
            listaOriginal.addAll((ArrayList<AvistamientoU>) getArguments().getSerializable("data"));
            listaFiltrada.clear();
            listaFiltrada.addAll((ArrayList<AvistamientoU>) getArguments().getSerializable("data"));
        }
        adapter = new UserSightAdapter(requireContext(), listaFiltrada,avis -> {
            if (listener != null) {
                listener.OnSightSelected(avis);
            }
        }
        );
      //  lista.setLayoutManager(new LinearLayoutManager(requireContext()));
        lista.setAdapter(adapter);
        return vista;
    }

    public void filter(String text) {
        if (adapter == null) return;
        listaFiltrada.clear();
        if (text == null || text.isEmpty()) {
            listaFiltrada.addAll(listaOriginal);
        } else {
            text = text.toLowerCase();
            for (AvistamientoU p : listaOriginal) {
                if (p.getPajaro().toLowerCase().contains(text)|| p.getpRaza().toLowerCase().contains(text)) {
                    listaFiltrada.add(p);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    public interface OnSightSelectedListener {
        void OnSightSelected(AvistamientoU ave);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof UserSightFragment.OnSightSelectedListener) {
            listener = (UserSightFragment.OnSightSelectedListener) context;
        } else {
            throw new ClassCastException(
                    context.toString()
                            + " debe implementar los listeners requeridos"
            );
        }
    }

}