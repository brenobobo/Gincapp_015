package com.example.gincapp_015.Entidades;

public class Equipe {

    private String id;
    private String idGincana;
    private String nome;
    private String pontos;
    private String lugar;




    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPontos() {
        return pontos;
    }

    public void setPontos(String pontos) {
        this.pontos = pontos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdGincana() {
        return idGincana;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setIdGincana(String idGincana) {
        this.idGincana = idGincana;
    }

    @Override
    public String toString() {
        return nome;
    }

}
