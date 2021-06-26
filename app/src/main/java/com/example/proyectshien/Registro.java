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

public class Registro extends AppCompatActivity {

    EditText Name, Email, Contra, Phone;
    Button Registro;
    TextView mLoginBtn;
    ProgressBar ProgressBar;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Name = findViewById(R.id.Nombre);
        Email = findViewById(R.id.Correo);
        Contra = findViewById(R.id.contrasena2);
        Phone = findViewById(R.id.Telefono);
        Registro = findViewById(R.id.Registro);
        mLoginBtn = findViewById(R.id.iniciarSesion);
        ProgressBar = findViewById(R.id.progressBar);

        fAuth = FirebaseAuth.getInstance();

        /*if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),Principal.class));
            finish();
        }*/

        Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString().trim();
                String password = Contra.getText().toString().trim();
                String nombreC = Name.getText().toString().trim();
                String telefono = Phone.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    Email.setError("El correo es requerido");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Contra.setError("La contraseña es requerida");
                    return;
                }
                if (password.length() < 6){
                    Contra.setError("La contraseña debe se mayor a 5 caracteres");
                    return;
                }
                if (TextUtils.isEmpty(nombreC)){
                    Name.setError("El nombre es requerido");
                    return;
                }
                if (TextUtils.isEmpty(telefono)){
                    Phone.setError("El telefono es requerido");
                    return;
                }

                ProgressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Registro.this,"Usuario creado existosamente", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Principal.class));
                        } else {
                            Toast.makeText(Registro.this,"Se produjo un error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            ProgressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}