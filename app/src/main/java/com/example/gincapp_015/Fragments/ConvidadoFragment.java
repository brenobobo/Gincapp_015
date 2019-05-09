package com.example.gincapp_015.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gincapp_015.Adapter.GincanaAdapter;
import com.example.gincapp_015.Adapter.GincanaConvidadoAdapter;
import com.example.gincapp_015.Control.ConfiguracaoFirebase;
import com.example.gincapp_015.Control.ControlUsuario;
import com.example.gincapp_015.Entidades.ConvidadoGincana;
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
public class ConvidadoFragment extends Fragment {


    private ListView listViewConvidadosGincanas;
    private ArrayList<ConvidadoGincana> convidadoGincanas;

    private ArrayAdapter adapter;

    // Databasereference em vez do query. Pois eu queria mudar a ordem da listagem e com o databasereference não achei um jeito prático
    private Query databaseReference;

    private ValueEventListener valueEventListenerGincanaConvidado;

    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(valueEventListenerGincanaConvidado);
    }

    @Override
    public void onStop() {
        super.onStop();
        databaseReference.removeEventListener(valueEventListenerGincanaConvidado);

    }


    public ConvidadoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_convidado, container, false);

        listViewConvidadosGincanas = view.findViewById(R.id.listViewGincanasConvidadosId);

        convidadoGincanas = new ArrayList<>();



        adapter = new GincanaConvidadoAdapter(getContext(), convidadoGincanas );


        listViewConvidadosGincanas.setAdapter(adapter);


        // puxando o email do usuário, para codificar em base 64, para selecionar cada competição em cada usuário
        ControlUsuario controlUsuario = new ControlUsuario();
        String emailCodificado = controlUsuario.recoverEmailBase64();



        databaseReference = ConfiguracaoFirebase.getFirebase().child("ConvidadoGincana").child(emailCodificado).orderByChild("nome");

        //Listener para recuperar gincanas
        valueEventListenerGincanaConvidado = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Limpar lista

                convidadoGincanas.clear();

                //Listando gincanas
                for (DataSnapshot dados: dataSnapshot.getChildren()){

                     ConvidadoGincana convidadoGincana = dados.getValue(ConvidadoGincana.class);
                    convidadoGincanas.add(convidadoGincana);
                }

                // atualizacao da mudança
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        listViewConvidadosGincanas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ConvidadoGincana convidadoGincana = convidadoGincanas.get(position);



            }
        });

        return view;
    }


}
