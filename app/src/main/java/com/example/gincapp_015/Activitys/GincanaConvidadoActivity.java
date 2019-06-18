package com.example.gincapp_015.Activitys;

import android.graphics.Path;
import android.icu.text.RelativeDateTimeFormatter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gincapp_015.Adapter.EquipeAdapter;
import com.example.gincapp_015.Adapter.GincanaConvidadoAdapter;
import com.example.gincapp_015.Control.ConfiguracaoFirebase;
import com.example.gincapp_015.Control.ControlUsuario;
import com.example.gincapp_015.Entidades.ConvidadoGincana;
import com.example.gincapp_015.Entidades.Equipe;
import com.example.gincapp_015.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GincanaConvidadoActivity extends AppCompatActivity {

    private ListView listViewConvidadosEquipes;
    private ArrayList<Equipe> convidadoEquipes;

    private String idDaGincana;

    private ArrayAdapter adapter;

    // Databasereference em vez do query. Pois eu queria mudar a ordem da listagem e com o databasereference não achei um jeito prático
    private Query databaseReference;

    private ValueEventListener valueEventListenerConvidadoEquipe;

    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(valueEventListenerConvidadoEquipe);
    }

    @Override
    public void onStop() {
        super.onStop();
        databaseReference.removeEventListener(valueEventListenerConvidadoEquipe);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gincana_convidado);

        listViewConvidadosEquipes = findViewById(R.id.listaTimesId);

        convidadoEquipes = new ArrayList<>();



        adapter = new EquipeAdapter(getApplicationContext(), convidadoEquipes );


        listViewConvidadosEquipes.setAdapter(adapter);


        // puxando o email do usuário, para codificar em base 64, para selecionar cada competição em cada usuário
        ControlUsuario controlUsuario = new ControlUsuario();
        String emailCodificado = controlUsuario.recoverEmailBase64();

        final Bundle extra = getIntent().getExtras();

        if (extra != null) {
            idDaGincana = extra.getString("idGincana");



        }



        databaseReference = ConfiguracaoFirebase.getFirebase().child("Equipe").child(idDaGincana).orderByChild("pontos");

        //Listener para recuperar gincanas
        valueEventListenerConvidadoEquipe = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Limpar lista

                convidadoEquipes.clear();

                //Listando gincanas
                for (DataSnapshot dados: dataSnapshot.getChildren()){

                    Equipe equipe = dados.getValue(Equipe.class);
                    convidadoEquipes.add(equipe);
                }

                // atualizacao da mudança
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

    }
}
