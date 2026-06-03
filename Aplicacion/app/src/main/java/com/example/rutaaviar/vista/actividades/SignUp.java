package com.example.rutaaviar.vista.actividades;

import android.content.Intent;
import android.os.Bundle;
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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextInputLayout userLayout = findViewById(R.id.userReg);
        TextInputLayout passLayout = findViewById(R.id.passReg);
        TextInputLayout passLayoutConf = findViewById(R.id.passReg2);

        Button bR = findViewById(R.id.buttonUserReg);

        bR.setOnClickListener(v -> {
            EditText userEdit = userLayout.getEditText();
            EditText passEdit = passLayout.getEditText();
            EditText passEditConf = passLayoutConf.getEditText();

            if (userEdit == null || passEdit == null || passEditConf==null) {
                Toast.makeText(v.getContext(), "Error en la interfaz", Toast.LENGTH_SHORT).show();
                return;
            }

            String username = userEdit.getText().toString();
            String password = passEdit.getText().toString();
            String confPassword = passEditConf.getText().toString();

            if (username.isEmpty() || password.isEmpty() || confPassword.isEmpty()) {
                Toast.makeText(v.getContext(), "Faltan datos", Toast.LENGTH_LONG).show();
                return;
            }

            if(!password.equals(confPassword)){
                Toast.makeText(v.getContext(), "La contraseña no coincide", Toast.LENGTH_LONG).show();
                return;
            }

            String pass = "";

            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));

                StringBuilder sb = new StringBuilder();
                for (byte b : hash) {
                    sb.append(String.format("%02x", b));
                }

                pass = sb.toString();

            } catch (NoSuchAlgorithmException e) {
                Toast.makeText(
                        getApplicationContext(),
                        "Error al procesar la contraseña",
                        Toast.LENGTH_LONG
                ).show();
                return;
            }

            Usuario reg=new Usuario(username, pass, false);

            new AccesoRest().crearUsuario(reg, new UsuarioCallback() {
                @Override
                public void onSuccess(Usuario u) {
                    GuardarUser.Usuario(getApplicationContext(), u);

                    runOnUiThread(() -> {
                        if (u == null) {
                            Toast.makeText(SignUp.this,"El usuario ya existe",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //salto a menu principal
                        Toast.makeText(getApplicationContext(), "Bienvenido " + u.getNombre(), Toast.LENGTH_LONG).show();
                        Intent i= new Intent(SignUp.this, ListadoA.class);
                        startActivity(i);
                        finish();
                    });
                }

                @Override
                public void onError(String error) {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error al crear el usuario", Toast.LENGTH_LONG).show());
                }
            });
        });
    }
}