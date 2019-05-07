package com.example.gincapp_015.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gincapp_015.Control.ConfiguracaoFirebase;
import com.example.gincapp_015.Entidades.Usuario;
import com.example.gincapp_015.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private Button entrar;
    private FirebaseAuth firebaseAuth;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificarUsuarioLogado();

        email = findViewById(R.id.editText_email);
        senha = findViewById(R.id.editText_senha);
        entrar = findViewById(R.id.button_entrar);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                validarLogin();
            }
        });


    }

    private void validarLogin(){



        firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();
        firebaseAuth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirTelaPrinciapal();
                    Toast.makeText(LoginActivity.this, "Sucesso ao fazer Login", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(LoginActivity.this, "Error ao fazer Login", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void abrirTelaPrinciapal(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void abrirCadastroUsuario( View view){

        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);

    }

    public void verificarUsuarioLogado(){
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();
        if (firebaseAuth.getCurrentUser() != null ){
            abrirTelaPrinciapal();
        }
    }
}
