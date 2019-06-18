package com.example.gincapp_015.Activitys;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gincapp_015.Adapter.EquipeAdapter;
import com.example.gincapp_015.Control.Base64Custom;
import com.example.gincapp_015.Control.ConfiguracaoFirebase;
import com.example.gincapp_015.Control.ControlConvidadoGincana;
import com.example.gincapp_015.Control.ControlEquipe;
import com.example.gincapp_015.Control.ControlUsuario;
import com.example.gincapp_015.Entidades.ConvidadoGincana;
import com.example.gincapp_015.Entidades.Equipe;
import com.example.gincapp_015.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

public class GincanaActivity extends AppCompatActivity {

    private TextView textViewNomeDaGincana;

    private String nomeDaGincana;
    private String idDaGincana;
    private String chaveamento;
    private Button botaoCadastrarTime;
    private android.support.v7.widget.Toolbar toolbar;

    private ListView listaEquipes;
    private ArrayAdapter adapter;
    private ArrayList<Equipe> equipes;
    private Equipe equipe;
    private String identificadorUsuario;


    private DatabaseReference firebase;
    private Query databaseReference;
    private ValueEventListener valueEventListenerEquipe;

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
        setContentView(R.layout.activity_gincana);

        final Bundle extra = getIntent().getExtras();

        if (extra != null) {
            nomeDaGincana = extra.getString("nome");
            idDaGincana = extra.getString("id");
            chaveamento = extra.getString("chave");


        }

        //toolbar

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(nomeDaGincana);
        setSupportActionBar(toolbar);






        botaoCadastrarTime = findViewById(R.id.bt_cadastrarTime);

        listaEquipes = findViewById(R.id.listaTimesId);

        botaoCadastrarTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(GincanaActivity.this);
                alertDialog.setTitle("Qual é o nome do time?");

                alertDialog.setCancelable(true);

                final EditText textoNome = new EditText(GincanaActivity.this);


                alertDialog.setView(textoNome);

                alertDialog.setPositiveButton("Criar time", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Equipe equipe = new Equipe();
                        ControlEquipe controlEquipe = new ControlEquipe();

                        String id = extra.getString("id");

                        equipe.setId(UUID.randomUUID().toString());
                        equipe.setIdGincana(id);
                        equipe.setNome(textoNome.getText().toString());
                        controlEquipe.salvarEquipe(equipe);

                    }
                });

                alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){

                    }
                });

                alertDialog.create();
                alertDialog.show();
            }
        });

        equipes = new ArrayList<>();


        listaEquipes = findViewById(R.id.listaTimesId);
        adapter = new EquipeAdapter(GincanaActivity.this, equipes);
        listaEquipes.setAdapter(adapter);



        final String idGincana = extra.getString("id");
        databaseReference = ConfiguracaoFirebase.getFirebase().child("Equipe").child(idGincana).orderByChild("pontos");

        //Listener para recuperar gincanas
        valueEventListenerEquipe = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Limpar lista

                equipes.clear();

                //Listando gincanas
                for (DataSnapshot dados: dataSnapshot.getChildren()){

                    Equipe equipe = dados.getValue(Equipe.class);

                    equipes.add(equipe);


                    Collections.reverse(equipes);




                }

                // atualizacao da mudança
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        };

        listaEquipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final Equipe e = equipes.get(position);
                android.app.AlertDialog.Builder alertDialogPonto = new android.app.AlertDialog.Builder(GincanaActivity.this);
                alertDialogPonto.setTitle("Quantos pontos esse time tem?");
                final EditText editPontos = new EditText(GincanaActivity.this);
                if(e.getPontos()== null){
                    editPontos.setText("0");
                }else{
                    editPontos.setText(e.getPontos());
                }

                alertDialogPonto.setView(editPontos);



                alertDialogPonto.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ControlEquipe ce = new ControlEquipe();
                        e.setPontos(editPontos.getText().toString());
                        e.setIdGincana(idGincana);

                        ce.atualizarEquipe(e.getId(),e);




                    }
                });

                alertDialogPonto.create();
                alertDialogPonto.show();

            }
        });

        listaEquipes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                android.app.AlertDialog.Builder alertDialogExcluir = new android.app.AlertDialog.Builder(GincanaActivity.this);

                alertDialogExcluir.setTitle("Deseja excluir essa equipe?");

                alertDialogExcluir.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ControlEquipe controlEquipe = new ControlEquipe();
                        equipe = equipes.get(position);
                        controlEquipe.excluirEquipe(equipe.getIdGincana(), equipe.getId());

                    }
                });

                alertDialogExcluir.create();
                alertDialogExcluir.show();

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_convidado, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_convidar:
                convidarUsuario();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void convidarUsuario(){
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(GincanaActivity.this);
        alertDialog.setTitle("Digite o email do convidado");
        final EditText emailUsuario = new EditText(GincanaActivity.this);
        alertDialog.setView(emailUsuario);
        alertDialog.setPositiveButton("Convidar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String emailConvidado = emailUsuario.getText().toString();

                if (emailConvidado.isEmpty()){
                    Toast.makeText(GincanaActivity.this, "Preencha uma e-mail", Toast.LENGTH_LONG).show();
                }else {
                    //Verificar se o usuario está cadastrado no app
                    identificadorUsuario = Base64Custom.codificarBase64(emailConvidado);
                    firebase = ConfiguracaoFirebase.getFirebase().child("Usuario").child(identificadorUsuario);

                    firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null ){

                                ControlUsuario controlUsuario = new ControlUsuario();
                                String emailConvidou = controlUsuario.recoverEmailBase64();

                                ConvidadoGincana convidado = new ConvidadoGincana();
                                convidado.setIdConvidado(identificadorUsuario);
                                convidado.setEmailConvidado(emailConvidado);
                                convidado.setNomeDaGincana(nomeDaGincana);
                                convidado.setChaveamento(chaveamento);
                                convidado.setIdDaGincana(idDaGincana);
                                convidado.setEmailConvidou(emailConvidou);

                                ControlConvidadoGincana controlConvidadoGincana = new ControlConvidadoGincana();
                                controlConvidadoGincana.salvarConvidadoGincana(convidado);





                            }else{
                                Toast.makeText(GincanaActivity.this, "Não existe esse email cadastrado", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        });

        alertDialog.show();
    }
}
