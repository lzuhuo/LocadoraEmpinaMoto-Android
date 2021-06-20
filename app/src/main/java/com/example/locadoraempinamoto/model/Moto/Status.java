package com.example.locadoraempinamoto.model.Moto;

public class Status {
    public String ST_LOCACAO;
    public String DS_LOCACAO;

    public Status(String ST_LOCACAO, String DS_LOCACAO){
        this.ST_LOCACAO = ST_LOCACAO;
        this.DS_LOCACAO = DS_LOCACAO;
    }

    public String toString(){
        return String.format("%s",this.DS_LOCACAO);
    }
}
