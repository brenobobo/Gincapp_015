package com.example.gincapp_015.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gincapp_015.Control.ConfiguracaoFirebase;
import com.example.gincapp_015.Entidades.Equipe;
import com.example.gincapp_015.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SemiFinalActivity extends AppCompatActivity {

    private Button botaoAdcionarEquipe;
    private Button botaoAddPontos;
    private TextView campo1;
    private TextView campo2;
    private TextView campo3;
    private TextView campo4;

    private String idDaGincana;

    private ArrayList<Equipe> equipes;

    private Query databaseReference;
    private ValueEventListener valueEventListenerEquipe;
    private FirebaseUser firebaseAuth;


    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(valueEventListenerEquipe);
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseReference.removeEventListener(valueEventListenerEquipe);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semi_final);

        final Bundle extra = getIntent().getExtras();

        if (extra != null) {
            idDaGincana = extra.getString("chave");

        }

        botaoAdcionarEquipe = findViewById(R.id.button_addEquipe);
        botaoAddPontos = findViewById(R.id.botaoAddPontosId);
        campo1 = findViewById(R.id.campo1);
        campo2 = findViewById(R.id.campo2);
        campo3 = findViewById(R.id.campo3);
        campo4 = findViewById(R.id.campo4);








        botaoAdcionarEquipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SemiFinalActivity.this, AdcionarEquipeActivity.class);

                if (extra != null) {
                    String idGincana = extra.getString("id");
                    intent.putExtra("id", idGincana);
                    startActivity(intent);
                }else {
                    Toast.makeText(SemiFinalActivity.this,"ERROR: Necessita do id da gincana  idGincana",Toast.LENGTH_LONG).show();
                }

            }
        });

        botaoAddPontos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth().getCurrentUser();

                String email = firebaseAuth.getEmail();

                campo1.setText(email);

            }
        });

        databaseReference = ConfiguracaoFirebase.getFirebase().child("Equipe").child(idDaGincana).orderByChild("lugar").equalTo("Equipe1");

        valueEventListenerEquipe = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Equipe equipe = dataSnapshot.getValue(Equipe.class);

                //String lugarDaEquipe = equipe.getLugar();

                //campo1.setText(lugarDaEquipe);


            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };








        final String posicao = extra.getString("lugar");
        String nomeDaEquipe = extra.getString("nome");

        if (posicao == "1"){
            campo1.setText(nomeDaEquipe);

        }
        if (posicao == "3"){
            campo1.setText(nomeDaEquipe);

        }
        if (posicao == "4"){
            campo1.setText(nomeDaEquipe);

        }
        if (posicao == "5"){
            campo1.setText(nomeDaEquipe);

        }

    }
}
