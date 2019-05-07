package com.example.gincapp_015.Control;

import com.example.gincapp_015.Entidades.Usuario;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ControlUsuario {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser firebaseUser;



    public void salvarUsuario( Usuario usuario){
        DatabaseReference referenceFirebase = ConfiguracaoFirebase.getFirebase();
        referenceFirebase.child("Usuario").child(usuario.getId()).setValue(usuario);


    }

    // puxando o email do usuário, para codificar em base 64, para selecionar cada competição em cada usuário
    public String recoverEmailBase64(){
        String email;
        firebaseUser = ConfiguracaoFirebase.getFirebaseAuth().getCurrentUser();
        email = firebaseUser.getEmail();
        Base64Custom base64Custom = new Base64Custom();
        String emailCodificado = base64Custom.codificarBase64(email);
        return emailCodificado;

    }
}
