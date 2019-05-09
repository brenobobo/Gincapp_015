package com.example.gincapp_015.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gincapp_015.Entidades.ConvidadoGincana;
import com.example.gincapp_015.Entidades.Gincana;
import com.example.gincapp_015.R;

import java.util.ArrayList;

public class GincanaConvidadoAdapter extends ArrayAdapter<ConvidadoGincana> {

    private ArrayList<ConvidadoGincana> convidadoGincanas;
    private Context context;

    public GincanaConvidadoAdapter(@NonNull Context c, @NonNull ArrayList<ConvidadoGincana> objects) {
        super(c, 0, objects);
        this.convidadoGincanas = objects;
        this.context = c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if (convidadoGincanas != null ){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_convida_gincana,parent, false);

            TextView nomeGincana = view.findViewById(R.id.tv_nome_convidado_gincana);

            ConvidadoGincana convidadoGincana = convidadoGincanas.get(position);

            nomeGincana.setText(convidadoGincana.getNomeDaGincana());





        }
        return view;
    }


}
