package com.example.locadoraempinamoto.model.Categoria;

public class Moto {
    public int CD_CATEGORIA;
    public String DS_CATEGORIA;
    public String ST_ATIVO;

    public Moto(int CD_MOTO, String DS_CATEGORIA){
        this.CD_CATEGORIA = CD_MOTO;
        this.ST_ATIVO = "S";
        this.DS_CATEGORIA = DS_CATEGORIA;
    }

    public String toString(){
        return String.format("%s", this.DS_CATEGORIA);
    }
}
