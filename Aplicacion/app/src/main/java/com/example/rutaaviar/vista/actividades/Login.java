package com.example.rutaaviar.vista.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rutaaviar.R;
import com.example.rutaaviar.modelo.entidades.Usuario;
import com.example.rutaaviar.rest.AccesoRest;
import com.example.rutaaviar.rest.GuardarUser;
import com.example.rutaaviar.rest.UsuarioCallback;
import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

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

        Usuario savedUser = GuardarUser.cargarUsuario(getApplicationContext());
        if (savedUser != null) {
            Toast.makeText(this, "Bienvenido " + savedUser.getNombre(), Toast.LENGTH_LONG).show();
            //salto a menu principal
            //startMainActivity();
            return;
        }

        TextInputLayout userLayout = findViewById(R.id.user);
        TextInputLayout passLayout = findViewById(R.id.pass);

        Button bS = findViewById(R.id.button);

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

            new AccesoRest().accederUsuario(username, new UsuarioCallback() {
                @Override
                public void onSuccess(Usuario u) {
                    GuardarUser.Usuario(getApplicationContext(), u);

                    runOnUiThread(() -> {
                        //salto a menu principal
                        Toast.makeText(getApplicationContext(), "Bienvenido " + u.getNombre(), Toast.LENGTH_LONG).show();

                       // startMainActivity();
                    });
                }

                @Override
                public void onError(String error) {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show());
                }
            });
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(Login.this, Login.class);
        startActivity(intent);
        finish();
    }
}