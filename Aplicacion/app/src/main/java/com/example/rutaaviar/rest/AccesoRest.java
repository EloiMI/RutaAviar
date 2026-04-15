package com.example.rutaaviar.rest;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.rutaaviar.modelo.entidades.Pajaro;
import com.google.gson.Gson;
import com.example.rutaaviar.modelo.entidades.Usuario;
import com.google.gson.reflect.TypeToken;

import org.bouncycastle.crypto.generators.BCrypt;


import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AccesoRest {
    public AccesoRest() {
    }
    private static String url="http://192.168.1.138:8080/db";
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

    public void accederUsuario(String nombre, UsuarioCallback cal){
        OkHttpClient client = new OkHttpClient();
        //String hashed = BCrypt.hashpw(contra, BCrypt.gensalt());

        //contra = BCrypt.hashpw(contra, BCrypt.gensalt());

        String ur = url+"/login?nombre=" + nombre;

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
    }

    public void crearUsuario(Usuario nombre, UsuarioCallback cal){
        OkHttpClient client = new OkHttpClient();

        //crear usuario

        //cifrar contraseña

        //String hashed = BCrypt.hashpw(contra, BCrypt.gensalt());

        //contra = BCrypt.hashpw(contra, BCrypt.gensalt());

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
}
