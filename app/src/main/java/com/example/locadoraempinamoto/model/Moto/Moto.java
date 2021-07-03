package com.example.locadoraempinamoto.model.Moto;

import java.util.ArrayList;

import com.example.locadoraempinamoto.model.Categoria.Acessorio;
import com.example.locadoraempinamoto.model.Categoria.CatMoto;

public class Moto {
    public int CD_MOTO;
    public CatMoto CATMOTO;
    public String DS_MARCA;
    public String DS_MODELO;
    public int NR_ANO;
    public com.example.locadoraempinamoto.model.Categoria.Motor TP_MOTOR;
    public float CP_TANQUE;
    public float AV_CONSUMO;
    public float VL_CUSTO;
    public String ST_ATIVO;
    public ArrayList<Acessorio> Acessorios;

    public Moto(
            int CD_MOTO, CatMoto CATMOTO, String DS_MARCA, String DS_MODELO,
            int NR_ANO, com.example.locadoraempinamoto.model.Categoria.Motor TP_MOTOR, float CP_TANQUE, float AV_CONSUMO,
            float VL_CUSTO, ArrayList<Acessorio> Acessorios){
        this.CD_MOTO = CD_MOTO;
        this.CATMOTO = CATMOTO;
        this.DS_MARCA = DS_MARCA;
        this.DS_MODELO = DS_MODELO;
        this.NR_ANO = NR_ANO;
        this.TP_MOTOR = TP_MOTOR;
        this.CP_TANQUE = CP_TANQUE;
        this.AV_CONSUMO = AV_CONSUMO;
        this.VL_CUSTO = VL_CUSTO;
        this.ST_ATIVO = "S";
        this.Acessorios = Acessorios;
    }

    public Moto( Moto moto){
        this.CD_MOTO = moto.CD_MOTO;
        this.CATMOTO = moto.CATMOTO;
        this.DS_MARCA = moto.DS_MARCA;
        this.DS_MODELO = moto.DS_MODELO;
        this.NR_ANO = moto.NR_ANO;
        this.TP_MOTOR = moto.TP_MOTOR;
        this.CP_TANQUE = moto.CP_TANQUE;
        this.AV_CONSUMO = moto.AV_CONSUMO;
        this.VL_CUSTO = moto.VL_CUSTO;
        this.ST_ATIVO = "S";
        this.Acessorios = moto.Acessorios;
    }

    public Moto(int CD_MOTO, String DS_MARCA, String DS_MODELO, float VL_CUSTO){
        this.CD_MOTO = CD_MOTO;
        this.DS_MARCA = DS_MARCA;
        this.DS_MODELO = DS_MODELO;
        this.VL_CUSTO = VL_CUSTO;
    }

    public Moto(int CD_MOTO, String DS_MODELO){
        this.CD_MOTO = CD_MOTO;
        this.DS_MODELO = DS_MODELO;
    }

    public String toString(){
        return String.format("%s", this.DS_MODELO);
    }
}
