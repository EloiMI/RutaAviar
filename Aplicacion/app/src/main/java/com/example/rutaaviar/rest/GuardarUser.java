package com.example.rutaaviar.rest;

import android.content.Context;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import com.example.rutaaviar.modelo.entidades.Usuario;
import com.google.gson.Gson;

public class GuardarUser {

    private static final String log = "user_data.json";

    // Save user object as JSON
    public static void Usuario(Context context, Usuario u) {
        Gson gson = new Gson();
        String json = gson.toJson(u);

        try (FileOutputStream fos = context.openFileOutput(log, Context.MODE_PRIVATE)) {
            fos.write(json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Usuario cargarUsuario(Context context) {
        try (FileInputStream fis = context.openFileInput(log)) {
            byte[] data = new byte[fis.available()];
            fis.read(data);
            String json = new String(data);
            Gson gson = new Gson();
            return gson.fromJson(json, Usuario.class);
        } catch (IOException e) {
            return null;
        }
    }

    public static int UsuarioId(Context ct){
        Usuario u = cargarUsuario(ct);
        if (u != null) {
            return u.getId();
        } else {
            return -1; 
        }
    }

    public static String UsuarioNombre(Context ct){
        Usuario u = cargarUsuario(ct);
        if (u != null) {
            return u.getNombre();
        } else {
            return "";
        }
    }

    public static void Logout(Context context) {
        context.deleteFile(log);
    }
}