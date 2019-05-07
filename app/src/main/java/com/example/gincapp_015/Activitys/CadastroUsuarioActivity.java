package com.example.gincapp_015.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gincapp_015.Control.Base64Custom;
import com.example.gincapp_015.Control.ConfiguracaoFirebase;
import com.example.gincapp_015.Control.ControlUsuario;
import com.example.gincapp_015.Entidades.Usuario;
import com.example.gincapp_015.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText nome;
    private EditText email;
    private EditText senha;
    private EditText confirmarSenha;
    private Button cadastrar;
    private TextView teste;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        nome = findViewById(R.id.editText_cadastroNome);
        email = findViewById(R.id.editText_cadastroEmail);
        senha = findViewById(R.id.editText_cadastroSenha);
        confirmarSenha = findViewById(R.id.editText_confirmarSenha);
        cadastrar = findViewById(R.id.button_cadastrar);
        teste = findViewById(R.id.textView_teste);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario u = new Usuario();
                ControlUsuario cu = new ControlUsuario();
                u.setNome(nome.getText().toString());
                u.setEmail(email.getText().toString());
                u.setSenha(senha.getText().toString());
                autenticacaoUsuario(u);









            }
        });


    }

    public void autenticacaoUsuario(final Usuario usuario){
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();
        firebaseAuth.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroUsuarioActivity.this,"Cadastrado com sucesso.", Toast.LENGTH_LONG ).show();
                    //Intent intent = new Intent(CadastroUsuarioActivity.this, LoginActivity.class);
                    // startActivity(intent);
                    ControlUsuario cu = new ControlUsuario();
                    String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    usuario.setId(identificadorUsuario);
                    cu.salvarUsuario(usuario);


                    abriLoginUsuario();


                }else{

                    String erroExcecao = "";
                    try{
                        throw  task.getException();


                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Digite uma senha mais forte";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao =  "Seu e-mail é invalido.";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "Esse e-mail já está em uso";


                    } catch (Exception e) {
                        erroExcecao = "Error ao efetuar o cadastro";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroUsuarioActivity.this,erroExcecao, Toast.LENGTH_LONG ).show();
                }

            }
        });
    }

    public void limparCampos(){
        nome.setText("");
        email.setText("");
        senha.setText("");
        confirmarSenha.setText("");
    }

    public void abriLoginUsuario(){
        Intent intent = new Intent(CadastroUsuarioActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
