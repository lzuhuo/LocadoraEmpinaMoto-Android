package com.example.locadoraempinamoto.model.Moto;

import java.util.ArrayList;

import com.example.locadoraempinamoto.model.Cliente.Cliente;

public class Locacao {
    public int CD_LOCACAO;
    public Moto moto;
    public Cliente cliente;
    public String DT_RETIRADA;
    public String LC_RETIRADA;
    public String DT_DEVOLUCAO;
    public String LC_DEVOLUCAO;
    public Status status;
    public Float VL_TOTAL;
    public ArrayList<Opcional> opcional;

    public Locacao(
        int CD_LOCACAO,
        Moto moto,
        Cliente cliente,
        String DT_RETIRADA,
        String LC_RETIRADA,
        String DT_DEVOLUCAO,
        String LC_DEVOLUCAO,
        Status status,
        Float VL_TOTAL,
        ArrayList<Opcional> opcional
    ){
        this.CD_LOCACAO = CD_LOCACAO;
        this.moto = moto;
        this.cliente = cliente;
        this.DT_RETIRADA = DT_RETIRADA;
        this.LC_RETIRADA = LC_RETIRADA;
        this.DT_DEVOLUCAO = DT_DEVOLUCAO;
        this.LC_DEVOLUCAO = LC_DEVOLUCAO;
        this.status = status;
        this.VL_TOTAL = VL_TOTAL;
        this.opcional = opcional;
    }
    
    public Locacao(int CD_LOCACAO, Status status, Float VL_TOTAL, ArrayList<Opcional> opcional){
        this.CD_LOCACAO = CD_LOCACAO;
        this.status = status;
        this.VL_TOTAL = VL_TOTAL;
        this.opcional = opcional;
    }

    public Locacao(){
        return;
    }
}
