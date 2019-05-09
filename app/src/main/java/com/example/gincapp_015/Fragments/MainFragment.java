package com.example.gincapp_015.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gincapp_015.Activitys.CadastroCompeticaoActivity;
import com.example.gincapp_015.Activitys.GincanaActivity;
import com.example.gincapp_015.Activitys.MainActivity;
import com.example.gincapp_015.Activitys.SemiFinalActivity;
import com.example.gincapp_015.Adapter.GincanaAdapter;
import com.example.gincapp_015.Control.ConfiguracaoFirebase;
import com.example.gincapp_015.Control.ControlGincana;
import com.example.gincapp_015.Control.ControlUsuario;
import com.example.gincapp_015.Entidades.Gincana;
import com.example.gincapp_015.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private FloatingActionButton botaoCadastrar;
    private ListView listViewGincanas;
    private ArrayList<Gincana> gincanas;

    private ArrayAdapter adapter;

    // Databasereference em vez do query. Pois eu queria mudar a ordem da listagem e com o databasereference não achei um jeito prático
    private Query databaseReference;

    private ValueEventListener valueEventListenerGincana;



    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(valueEventListenerGincana);
    }

    @Override
    public void onStop() {
        super.onStop();
        databaseReference.removeEventListener(valueEventListenerGincana);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        botaoCadastrar = view.findViewById(R.id.floatingActionButton_addGincana);

        listViewGincanas = view.findViewById(R.id.listViewGincanasId);

        gincanas = new ArrayList<>();


        adapter = new GincanaAdapter(getContext(), gincanas );


        listViewGincanas.setAdapter(adapter);


        // puxando o email do usuário, para codificar em base 64, para selecionar cada competição em cada usuário
        ControlUsuario controlUsuario = new ControlUsuario();
        String emailCodificado = controlUsuario.recoverEmailBase64();



        databaseReference = ConfiguracaoFirebase.getFirebase().child("Gincana").child(emailCodificado).orderByChild("nome");

        //Listener para recuperar gincanas
        valueEventListenerGincana = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Limpar lista

                gincanas.clear();

                //Listando gincanas
                for (DataSnapshot dados: dataSnapshot.getChildren()){

                    Gincana gincana = dados.getValue(Gincana.class);
                    gincanas.add(gincana);
                }

                // atualizacao da mudança
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CadastroCompeticaoActivity.class);
                startActivity(intent);
            }
        });

        listViewGincanas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Gincana gincana = gincanas.get(position);

                String valor = gincana.getChaveamento();

                if (valor.equals("Semi-Final")){

                    Intent intent = new Intent(getActivity(), SemiFinalActivity.class);

                    intent.putExtra("chave", gincana.getChaveamento());
                    intent.putExtra("nome", gincana.getNome());
                    intent.putExtra("id", gincana.getId());

                    startActivity(intent);


                }else{

                    Intent intent = new Intent(getActivity(), GincanaActivity.class);
                    intent.putExtra("chave", gincana.getChaveamento());
                    intent.putExtra("nome", gincana.getNome());
                    intent.putExtra("id", gincana.getId());
                    startActivity(intent);
                }


            }
        });

        listViewGincanas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

                alertDialog.setTitle("Você deseja excluir a gincana?");

                alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ControlGincana control = new ControlGincana();

                        Gincana g = gincanas.get(position);

                        control.excluirGincana(g.getId());



                    }

                });

                alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alertDialog.create();
                alertDialog.show();


                return true;
            }
        });


        return view;
    }

}
