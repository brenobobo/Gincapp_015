package com.example.gincapp_015.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gincapp_015.Entidades.Equipe;
import com.example.gincapp_015.R;

import java.util.ArrayList;

public class EquipeAdapter extends ArrayAdapter<Equipe> {

    private ArrayList<Equipe> equipes;
    private Context context;

    public EquipeAdapter(@NonNull Context c, @NonNull ArrayList<Equipe> objects) {
        super(c, 0, objects);
        this.equipes = objects;
        this.context = c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if (equipes != null ){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_equipe,parent, false);

            TextView nomeEquipe = view.findViewById(R.id.tv_nome_equipe);

            TextView equipePontos = view.findViewById(R.id.tv_pontos);

            Equipe equipe = equipes.get(position);

            nomeEquipe.setText(equipe.getNome());

            if (equipe.getPontos() == null){
                equipePontos.setText("Pontos: 0");
            }else {
                equipePontos.setText("Pontos: " + equipe.getPontos());
            }

        }
        return view;
    }
}
