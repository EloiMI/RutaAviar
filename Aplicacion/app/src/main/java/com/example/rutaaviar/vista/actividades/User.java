package com.example.rutaaviar.vista.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rutaaviar.R;
import com.example.rutaaviar.modelo.entidades.AvistamientoU;
import com.example.rutaaviar.modelo.entidades.Pajaro;
import com.example.rutaaviar.rest.AccesoRest;
import com.example.rutaaviar.rest.AvistamientoCreadoCallback;
import com.example.rutaaviar.rest.AvistamientoUserCallback;
import com.example.rutaaviar.rest.GuardarUser;
import com.example.rutaaviar.vista.fragmentos.BirdListFragment;
import com.example.rutaaviar.vista.fragmentos.UserSightFragment;

import java.util.ArrayList;

public class User extends AppCompatActivity implements UserSightFragment.OnSightSelectedListener{

    private UserSightFragment listaFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        int iduser=GuardarUser.UsuarioId(getApplicationContext());
        String nameuser=GuardarUser.UsuarioNombre(getApplicationContext());
        TextView t1=findViewById(R.id.UsuarioNombre);
        t1.setText(nameuser);



        new AccesoRest().listadoAvistamientosUsuario(iduser, new AvistamientoUserCallback() {
            @Override
            public void onSuccess(ArrayList<AvistamientoU> avistamientos) {
                runOnUiThread(() -> {
                    listaFrag = UserSightFragment.newInstance(avistamientos);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragUser, listaFrag).commit();
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), "Error al cargar datos", Toast.LENGTH_SHORT).show();
                });
            }
        });
/*
        SearchView searchView = findViewById(R.id.searchSight);
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String busca) {
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String text) {
                        if (listaFrag != null) {
                            listaFrag.filter(text);
                        }
                        return true;
                    }
                });

 */
    }

    @Override
    public void OnSightSelected(AvistamientoU avi) {
//        Toast.makeText(getApplicationContext(), avi.getPajaro(), Toast.LENGTH_SHORT).show();
        //lanzar nueva actividad con mapa


        Intent i = new Intent(User.this, UserSightMap.class);
        i.putExtra("lon", avi.getLon());
        i.putExtra("lat", avi.getLat());
        startActivity(i);


    }
}