package com.example.gincapp_015.Control;

import com.example.gincapp_015.Entidades.Equipe;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ControlEquipe {

    FirebaseDatabase database = FirebaseDatabase.getInstance();


    public void salvarEquipe(Equipe equipe){
        DatabaseReference referenceFirebase = ConfiguracaoFirebase.getFirebase();
        referenceFirebase.child("Equipe").child(equipe.getIdGincana()).child(equipe.getId()).setValue(equipe);

    }

    public void excluirEquipe(String idGincana, String idEquipe){
        DatabaseReference referenceFirebase = ConfiguracaoFirebase.getFirebase();
        referenceFirebase.child("Equipe").child(idGincana).child(idEquipe).removeValue();
    }

    public void atualizarEquipe(String id, Equipe equipe){
        DatabaseReference referenceFirebase = ConfiguracaoFirebase.getFirebase();
        referenceFirebase.child("Equipe").child(equipe.getIdGincana()).child(id).setValue(equipe);
    }
}
