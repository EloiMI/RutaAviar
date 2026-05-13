package com.example.rutaaviar.vista.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rutaaviar.R;
import com.example.rutaaviar.modelo.entidades.Avistamiento;
import com.example.rutaaviar.modelo.entidades.Pajaro;
import com.example.rutaaviar.rest.AccesoRest;
import com.example.rutaaviar.rest.AvistamientoCallback;
import com.example.rutaaviar.rest.AvistamientoCreadoCallback;
import com.example.rutaaviar.rest.GuardarUser;
import com.example.rutaaviar.rest.UsuarioCallback;
import com.example.rutaaviar.vista.adaptadores.UserSightAdapter;
import com.example.rutaaviar.vista.fragmentos.BirdListFragment;

public class ListadoA extends AppCompatActivity
        implements BirdListFragment.OnPajaroSelectedListener,
        BirdListFragment.OnQuickSightListener,
        BirdListFragment.OnBirdInfoListener{

    private BirdListFragment listaFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars =insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left,systemBars.top,systemBars.right,systemBars.bottom);
            return insets;
        });

        listaFrag = BirdListFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragAv, listaFrag).commit();

        Toolbar toolbar = findViewById(R.id.toolbarAv);
        setSupportActionBar(toolbar);
        SearchView searchView = findViewById(R.id.searchBird);
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (listaFrag != null) {
                            listaFrag.filter(newText);
                        }
                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listado_aves, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId() == R.id.OptionLogout) {
            GuardarUser.Logout(getApplicationContext());
            Intent i = new Intent(ListadoA.this, Login.class);
            startActivity(i);
            finish();
        } else if (item.getItemId() == R.id.OptionUser) {
            Intent i = new Intent(ListadoA.this, User.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPajaroSelected(Pajaro ave) {
        Intent i = new Intent(ListadoA.this, AveDetalle.class);
        i.putExtra("id", ave.getNombre());
        startActivity(i);
    }

    @Override
    public void onQuickSight(Pajaro ave) {

        /// Confirmar avistamiento desplegable



      //  AccesoRest.avistamiento(pajaro);

        int iduser=GuardarUser.UsuarioId(getApplicationContext());


        new AccesoRest().crearAvistamiento(iduser, ave.getId(), new AvistamientoCreadoCallback() {
            @Override
            public void onSuccess(String success) {
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), "Avistamiento registrado", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }


    @Override
    public void onBirdInfo(Pajaro ave) {
        Intent i = new Intent(ListadoA.this, AveDetalle.class);
        i.putExtra("nombre", ave.getId());
        i.putExtra("nombre", ave.getNombre());
        i.putExtra("raza", ave.getRaza());
        startActivity(i);
    }
}