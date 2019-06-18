package com.example.gincapp_015.Control;

import com.example.gincapp_015.Entidades.Gincana;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ControlGincana {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ControlUsuario controlUsuario = new ControlUsuario();


    public void salvarGincana(Gincana gincana){


        DatabaseReference referenceFirebase = ConfiguracaoFirebase.getFirebase();
        referenceFirebase.child("Gincana").child(gincana.getIdUsuario()).child(gincana.getId()).setValue(gincana);


    }

    public void excluirGincana(String id){
        String emailCodificado = controlUsuario.recoverEmailBase64();
        DatabaseReference referenceFirebase = ConfiguracaoFirebase.getFirebase();
        referenceFirebase.child("Gincana").child(emailCodificado).child(id).removeValue();
    }

    public void excluirEquipeDaGincana(String idGincana){
        DatabaseReference referenceFirebase = ConfiguracaoFirebase.getFirebase();
        referenceFirebase.child("Equipe").child(idGincana).removeValue();
    }
}
