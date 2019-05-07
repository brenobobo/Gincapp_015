package com.example.gincapp_015.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.gincapp_015.Control.ControlGincana;
import com.example.gincapp_015.Control.ControlUsuario;
import com.example.gincapp_015.Entidades.Gincana;
import com.example.gincapp_015.R;
import com.google.firebase.auth.FirebaseUser;

import java.util.UUID;

public class CadastroCompeticaoActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private Button botaoPronto;
    private EditText editText_nomeGincana;
    private RadioGroup grupoChaveamento;
    private RadioButton radioEscolhido;

    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_competicao);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Criação da Gincana");

        botaoPronto = findViewById(R.id.button_pronto);
        editText_nomeGincana = findViewById(R.id.editText_nomeGincana);
        grupoChaveamento = findViewById(R.id.radioGroupChaveamento);


        botaoPronto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeDaGincana = editText_nomeGincana.getText().toString();

                int idRadioEscolhido = grupoChaveamento.getCheckedRadioButtonId();


                if (nomeDaGincana == null){
                    Toast.makeText(CadastroCompeticaoActivity.this, "Digite um nome.", Toast.LENGTH_SHORT).show();
                }else {
                    if (idRadioEscolhido > 0) {
                        radioEscolhido = findViewById(idRadioEscolhido);
                        final String rEscolhido = radioEscolhido.getText().toString();
                        ControlUsuario controlUsuario = new ControlUsuario();

                        String email = controlUsuario.recoverEmailBase64();


                        ControlGincana controlGincana = new ControlGincana();
                        Gincana gincana = new Gincana();
                        gincana.setId(UUID.randomUUID().toString());
                        gincana.setIdUsuario(email);
                        gincana.setNome(nomeDaGincana);
                        gincana.setChaveamento(rEscolhido);
                        controlGincana.salvarGincana(gincana);


                        Intent intent = new Intent(CadastroCompeticaoActivity.this, MainActivity.class);
                        startActivity(intent);

                        finish();
                    } else {
                        Toast.makeText(CadastroCompeticaoActivity.this, "Marque uma das opções.", Toast.LENGTH_SHORT);
                    }

                }





            }
        });


    }
}
