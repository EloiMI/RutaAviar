package com.example.rutaaviar.vista.actividades;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.rutaaviar.R;
import com.example.rutaaviar.modelo.entidades.Avistamiento;
import com.example.rutaaviar.rest.AccesoRest;
import com.example.rutaaviar.rest.AvistamientoCallback;
import com.example.rutaaviar.rest.AvistamientoCreadoCallback;

import org.osmdroid.config.Configuration;
import org.osmdroid.views.MapView;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;


public class AveDetalle extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ave_detalle);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        String n =intent.getExtras().getString("nombre");
        String r =intent.getExtras().getString("raza");
        int idP=intent.getExtras().getInt("id");
        TextView t1=findViewById(R.id.PajaroNombre);
        t1.setText(n);

        Configuration.getInstance().setUserAgentValue(getPackageName());
        Configuration.getInstance().load(
                getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(this)
        );

        List<GeoPoint> points = new ArrayList<>();
        MapView map = findViewById(R.id.mapView);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);

        IMapController controller = map.getController();
        controller.setZoom(6.5);
        controller.setCenter(new GeoPoint(40.4168, -3.7038));

        new AccesoRest().listadoAvistamientosPajaro(idP, new AvistamientoCallback() {
            @Override
            public void onSuccess(ArrayList<Avistamiento> aves) {
                runOnUiThread(() -> {
                    for (Avistamiento avs : aves) {

                        Marker marker = new Marker(map);
                        marker.setPosition(new GeoPoint(avs.getLat(), avs.getLon()));
                        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

                        //fecha
                        if(avs.getFecha()!=null){
                            marker.setTitle(avs.getFecha().toString());
                        }

                        marker.setIcon(getResources().getDrawable(android.R.drawable.btn_radio));
                        map.getOverlays().add(marker);
                    }
                });
            }
            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), "El mapa no se pudo cargar", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

}