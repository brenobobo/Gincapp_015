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

import com.example.gincapp_015.Control.ControlEquipe;
import com.example.gincapp_015.Entidades.Equipe;
import com.example.gincapp_015.R;

import java.util.UUID;

public class AdcionarEquipeActivity extends AppCompatActivity {

    private EditText campoNomeEquipe;
    private Button prontoCadastroTime;
    private RadioGroup grupoPosicao;
    private RadioButton radioSelecionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adcionar_equipe);

        campoNomeEquipe = findViewById(R.id.editTextNomeEquipe);
        prontoCadastroTime = findViewById(R.id.button_prontoCadastroEquipe);
        grupoPosicao = findViewById(R.id.radioGroupPosicao);

        prontoCadastroTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (campoNomeEquipe != null){

                    Equipe equipe = new Equipe();
                    ControlEquipe controlEquipe = new ControlEquipe();

                    int grupo = grupoPosicao.getCheckedRadioButtonId();

                    if (grupo > 0){
                        String nome = campoNomeEquipe.getText().toString();
                        radioSelecionado = findViewById(grupo);
                        String lugarNaTela = radioSelecionado.getText().toString();

                        final Bundle extra = getIntent().getExtras();
                        String idGincana = extra.getString("id");
                        equipe.setId(UUID.randomUUID().toString());
                        equipe.setIdGincana(idGincana);
                        equipe.setNome(nome);
                        equipe.setLugar(lugarNaTela);
                        controlEquipe.salvarEquipe(equipe);

                        Intent intent = new Intent(AdcionarEquipeActivity.this, SemiFinalActivity.class);

                        intent.putExtra("lugar", lugarNaTela);
                        intent.putExtra("nomeDoTime", nome);

                        startActivity(intent);

                        finish();

                    }else {
                        Toast.makeText(AdcionarEquipeActivity.this, "Selecione uma posicao", Toast.LENGTH_LONG);
                    }

                }else {
                    Toast.makeText(AdcionarEquipeActivity.this, "Digite um nome", Toast.LENGTH_LONG);
                }
            }
        });
    }
}
