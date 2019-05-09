package com.example.gincapp_015.Control;

import com.example.gincapp_015.Entidades.ConvidadoGincana;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ControlConvidadoGincana {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ControlUsuario controlUsuario = new ControlUsuario();

    public void salvarConvidadoGincana(ConvidadoGincana convidadoGincana){


        DatabaseReference referenceFirebase = ConfiguracaoFirebase.getFirebase();
        referenceFirebase.child("ConvidadoGincana").child(convidadoGincana.getIdConvidado()).child(convidadoGincana.getIdDaGincana()).setValue(convidadoGincana);
    }
}
