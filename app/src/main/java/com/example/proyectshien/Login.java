package com.example.proyectshien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText Correo, Contrasena;
    Button Login;
    TextView mCrearCuentaBtn;
    ProgressBar ProgressBar;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Correo = findViewById(R.id.correo);
        Contrasena = findViewById(R.id.contrasena);
        Login = findViewById(R.id.login);
        mCrearCuentaBtn = findViewById(R.id.CrearCuenta);
        ProgressBar = findViewById(R.id.progressBar2);

        fAuth = FirebaseAuth.getInstance();

        /*if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),Principal.class));
            finish();
        }*/

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Correo.getText().toString().trim();
                String password = Contrasena.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    Correo.setError("El correo es requerido");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Contrasena.setError("La contraseña es requerida");
                    return;
                }

                ProgressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Login.this,"Login Exitoso", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Principal.class));
                        } else {
                            Toast.makeText(Login.this,"Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            ProgressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        mCrearCuentaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Registro.class));
            }
        });
    }
}