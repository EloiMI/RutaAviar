package com.example.rutaaviar.rest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.rutaaviar.modelo.entidades.Avistamiento;
import com.example.rutaaviar.modelo.entidades.AvistamientoU;
import com.example.rutaaviar.modelo.entidades.Lugar;
import com.example.rutaaviar.modelo.entidades.Pajaro;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.gson.Gson;
import com.example.rutaaviar.modelo.entidades.Usuario;
import com.google.gson.reflect.TypeToken;



import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AccesoRest {
    public AccesoRest() {
    }
    private static String url="http://192.168.1.134:8080/db"; //ip ifconfig

/*    public void accederUsuario(String nombre, String contra, UsuarioCallback cal){
        OkHttpClient client = new OkHttpClient();
        //String hashed = BCrypt.hashpw(contra, BCrypt.gensalt());

        //contra = BCrypt.hashpw(contra, BCrypt.gensalt());

        String ur = "http://192.168.1.138:8080/login?nombre=" + nombre + "&contra=" + contra;

        Request request = new Request.Builder().url(ur).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                if ( response.isSuccessful() && response.body() != null) {
                    String responseData = response.body().string();
                    Gson gson = new Gson();
                    Usuario u = gson.fromJson(responseData,Usuario.class);
                    cal.onSuccess(u);
                } else {
                    cal.onError(String.valueOf(response.code()));
                }
            }
            @Override public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                Log.e("Error", "Error al realizar la solicitud", e);
            }
        });
    }*/

    public void accederUsuario(Usuario log, UsuarioCallback cal){
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        String json = gson.toJson(log);
        RequestBody body = RequestBody.create(json,MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder().url(url + "/login").post(body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String data = response.body().string();
                    Usuario u = gson.fromJson(data, Usuario.class);
                    if(u==null){
                        cal.onError("Contraseña inválida");
                    }
                    cal.onSuccess(u);
                } else {
                    cal.onError("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                cal.onError(e.getMessage());
            }
        });
    }


    public void crearUsuario(Usuario usuario, UsuarioCallback cal) {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        String json = gson.toJson(usuario);
        RequestBody body = RequestBody.create(json,MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder().url(url+"/signup").post(body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                cal.onError(e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200 && response.body() != null) {
                    String data = response.body().string();
                    Usuario u = gson.fromJson(data, Usuario.class);
                    cal.onSuccess(u);
                } else if (response.code() == 401) {
                    cal.onError("Usuario o contraseña incorrectos");
                } else {
                    cal.onError("Error servidor: " + response.code());
                }
            }
        });
    }

    public void listadoAves(AvesCallback cal){
        OkHttpClient client = new OkHttpClient();

        String ur = url+"/aves";
        ArrayList<Pajaro> listaPajaros=new ArrayList<>();

        Request request = new Request.Builder().url(ur).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                if ( response.isSuccessful() && response.body() != null) {
                    listaPajaros.clear();
                    String responseData = response.body().string();
                    ArrayList<Pajaro> pajarosDevueltos;
                    Gson gson = new Gson();
                    pajarosDevueltos = gson.fromJson(responseData,new TypeToken<ArrayList<Pajaro>>(){}.getType());
                    listaPajaros.addAll(pajarosDevueltos);
                    cal.onSuccess(listaPajaros);
                } else {
                    cal.onError(String.valueOf(response.code()));
                }
            }
            @Override public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                Log.e("Error", "Error al realizar la solicitud", e);
            }
        });
    }
    public void listadoAvistamientosPajaro(int idBird, AvistamientoCallback cal){
        OkHttpClient client = new OkHttpClient();

        String ur = url+"/avistamientosB?pjid="+idBird;
        ArrayList<Avistamiento> listaAvistamientos=new ArrayList<>();

        Request request = new Request.Builder().url(ur).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                if ( response.isSuccessful() && response.body() != null) {
                    listaAvistamientos.clear();
                    String responseData = response.body().string();
                    ArrayList<Avistamiento> avistamientosUser;
                    Gson gson = new Gson();
                    avistamientosUser = gson.fromJson(responseData,new TypeToken<ArrayList<Avistamiento>>(){}.getType());
                    listaAvistamientos.addAll(avistamientosUser);
                    cal.onSuccess(listaAvistamientos);
                } else {
                    cal.onError(String.valueOf(response.code()));
                }
            }
            @Override public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                Log.e("Error", "Error al realizar la solicitud", e);
            }
        });
    }

    public void listadoAvistamientosUsuario(int idUser, AvistamientoUserCallback cal){
        OkHttpClient client = new OkHttpClient();

        String ur = url+"/avistamientosU?usid="+idUser;
        ArrayList<AvistamientoU> listaAvistamientos=new ArrayList<>();

        Request request = new Request.Builder().url(ur).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                if ( response.isSuccessful() && response.body() != null) {
                    listaAvistamientos.clear();
                    String responseData = response.body().string();
                    ArrayList<AvistamientoU> avistamientosUser;
                    Gson gson = new Gson();
                    avistamientosUser = gson.fromJson(responseData,new TypeToken<ArrayList<AvistamientoU>>(){}.getType());
                    listaAvistamientos.addAll(avistamientosUser);
                    cal.onSuccess(listaAvistamientos);
                } else {
                    cal.onError(String.valueOf(response.code()));
                }
            }
            @Override public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                Log.e("Error", "Error al realizar la solicitud", e);
            }
        });
    }

    public void crearAvistamiento(Context context, int idUser, int idAve, AvistamientoCreadoCallback cal) {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            cal.onError("Permiso no concedido");
            return;
        }

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                    if (location == null) {
                        cal.onError("La ubicación no está disponible");
                        return;
                    }
            double lon = location.getLongitude();
            double lat = location.getLatitude();

            Avistamiento av = new Avistamiento(idAve, idUser, lon, lat);

            String json = gson.toJson(av);
         //   Log.d("AVISTAMIENTO_JSON", json);

            RequestBody body = RequestBody.create(json,MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder().url(url + "/regavistamientos").post(body).build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        cal.onSuccess("OK");
                    } else {
                        cal.onError("HTTP: " + response.code());
                    }
                }
                @Override
                public void onFailure(Call call, IOException e) {
                    cal.onError(e.getMessage());
                }
            });
        });
    }
}
