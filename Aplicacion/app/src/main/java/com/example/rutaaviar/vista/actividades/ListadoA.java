package com.example.rutaaviar.vista.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rutaaviar.R;
import com.example.rutaaviar.modelo.entidades.Pajaro;
import com.example.rutaaviar.modelo.entidades.Usuario;
import com.example.rutaaviar.rest.GuardarUser;
import com.example.rutaaviar.vista.fragmentos.BirdListFragment;

public class ListadoA extends AppCompatActivity implements BirdListFragment.OnPajaroSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BirdListFragment listaFrag = BirdListFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragAv, listaFrag)
                .commit();

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbarAv);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_listado_aves, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId() == R.id.OptionLogout) {
            GuardarUser.Logout(getApplicationContext());

            Intent i= new Intent(ListadoA.this, Login.class);
            startActivity(i);
            finish();
        }
        /*else if (item.getItemId() == R.id.OptionLogout) {
            finish();
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPajaroSelected(Pajaro ave) {
        //Toast.makeText(this, "Seleccionaste: " + ave.getNombre(), Toast.LENGTH_SHORT).show();
        //saltar a tab información detallada
        Intent i= new Intent(ListadoA.this, AveDetalle.class);
        i.putExtra("id", ave.getNombre());
        startActivity(i);
        //finish();
    }
}