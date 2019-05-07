package com.example.gincapp_015.Entidades;

public class ConvidadoGincana {
    private String idConvidado;
    private String idDaGincana;
    private String email;
    private String nomeDaGincana;
    private String chaveamento;


    public ConvidadoGincana() {
    }

    public String getIdConvidado() {
        return idConvidado;
    }

    public void setIdConvidado(String idConvidado) {
        this.idConvidado = idConvidado;
    }

    public String getNomeDaGincana() {
        return nomeDaGincana;
    }

    public void setNomeDaGincana(String nome) {
        this.nomeDaGincana = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdDaGincana() {
        return idDaGincana;
    }

    public void setIdDaGincana(String idDaGincana) {
        this.idDaGincana = idDaGincana;
    }

    public String getChaveamento() {
        return chaveamento;
    }

    public void setChaveamento(String chaveamento) {
        this.chaveamento = chaveamento;
    }
}
