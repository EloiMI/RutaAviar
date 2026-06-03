package com.example.rutaaviar.vista.actividades;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rutaaviar.R;
import com.example.rutaaviar.modelo.entidades.Usuario;
import com.example.rutaaviar.rest.AccesoRest;
import com.example.rutaaviar.rest.GuardarUser;
import com.example.rutaaviar.rest.UsuarioCallback;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.app.ActivityCompat;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST);
        }


        Usuario savedUser = GuardarUser.cargarUsuario(getApplicationContext());

        if (savedUser != null) {
            //Toast.makeText(this, "Bienvenido " + savedUser.getNombre(), Toast.LENGTH_LONG).show();
            //salto a menu principal
            //startMainActivity();
            Intent i= new Intent(Login.this, ListadoA.class);
            startActivity(i);
            finish();
            return;
        }

        TextInputLayout userLayout = findViewById(R.id.user);
        TextInputLayout passLayout = findViewById(R.id.pass);

        Button bS = findViewById(R.id.button);
        Button bR = findViewById(R.id.button2);

        bS.setOnClickListener(v -> {
            EditText userEdit = userLayout.getEditText();
            EditText passEdit = passLayout.getEditText();

            if (userEdit == null || passEdit == null) {
                Toast.makeText(v.getContext(), "Error en la interfaz", Toast.LENGTH_SHORT).show();
                return;
            }

            String username = userEdit.getText().toString();
            String password = passEdit.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(v.getContext(), "Faltan datos", Toast.LENGTH_LONG).show();
                return;
            }

            Usuario log=new Usuario(username, password);

            new AccesoRest().accederUsuario(log, new UsuarioCallback() {
                @Override
                public void onSuccess(Usuario u) {
                    runOnUiThread(() -> {

                        if (u == null) {
                          //  Toast.makeText(Login.this,"Error de login",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        GuardarUser.Usuario(getApplicationContext(), u);

                        Intent i = new Intent(Login.this, ListadoA.class);
                        startActivity(i);
                        finish();
                    });
                }

                @Override
                public void onError(String error) {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_LONG).show());
                }
            });
        });

        bR.setOnClickListener(v -> {
            runOnUiThread(() -> {
                //pantalla configuración
                Intent i= new Intent(Login.this, SignUp.class);
                startActivity(i);
              //  finish();
            });
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(Login.this, Login.class);
        startActivity(intent);
        finish();
    }
}