package com.example.locadoraempinamoto.model.Categoria;

public class Acessorio {
    public int CD_ACESSORIO;
    public String DS_ACESSORIO;
    public String ST_ATIVO;

    public Acessorio(int CD_ACESSORIO, String DS_ACESSORIO){
        this.CD_ACESSORIO = CD_ACESSORIO;
        this.ST_ATIVO = "S";
        this.DS_ACESSORIO = DS_ACESSORIO;
    }

    public String toString(){
        return String.format("%s", this.DS_ACESSORIO);
    }
}
