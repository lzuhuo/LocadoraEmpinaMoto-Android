package com.example.locadoraempinamoto.services;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import com.example.locadoraempinamoto.business.LocacaoDAO;
import com.example.locadoraempinamoto.model.Message;
import com.example.locadoraempinamoto.model.Moto.Locacao;
import com.example.locadoraempinamoto.model.Moto.Moto;
import com.example.locadoraempinamoto.model.Moto.Opcional;
import com.example.locadoraempinamoto.model.Moto.Status;
import com.example.locadoraempinamoto.util.Util;

public class LocacaoService {
    private Context context;
    public LocacaoService(Context data){
        this.context = data;
    }

    public ArrayList<String> getDSMoto(String DT_INICIO, String DT_FIM){
        ArrayList<String> motos = new ArrayList<String>();
        LocacaoDAO locacaoDAO = new LocacaoDAO(context);
        try {
            motos = locacaoDAO.getDSMoto(Util.dataFormatSQL(DT_INICIO), Util.dataFormatSQL(DT_FIM));
        } catch (Exception e) {
            return null;
        }
        return motos;
    }

    public ArrayList<Moto> getModelMoto(String DT_INICIO, String DT_FIM, String DS_MARCA){
        Log.d("ModelosMotos","Chegou aqui Service");
        ArrayList<Moto> motos = new ArrayList<Moto>();
        LocacaoDAO locacaoDAO = new LocacaoDAO(context);
        try {
            motos = locacaoDAO.getModelMoto(Util.dataFormatSQL(DT_INICIO), Util.dataFormatSQL(DT_FIM), DS_MARCA);
        } catch (Exception e) {
            Log.d("ModelosMotos",e.toString());
            return null;
        }
        Log.d("ModelosMotos",String.format("%d",motos.size()));
        return motos;
    }

    public ArrayList<Opcional> listaOpcionais(){
        ArrayList<Opcional> opcionais = new ArrayList<Opcional>();
        LocacaoDAO locacaoDAO = new LocacaoDAO(context);
        try {
            opcionais = locacaoDAO.listaOpcionais();
        } catch (Exception e) {
            return null;
        }
        return opcionais;
    }

    public ArrayList<Locacao> listarLocacoes(){
        ArrayList<Locacao> locacoes = new ArrayList<Locacao>();
        LocacaoDAO locacaoDAO = new LocacaoDAO(context);
        try {
            locacoes = locacaoDAO.listaLocacoes();
        } catch (Exception e) {
            return null;
        }
        return locacoes;
    }

    public Locacao getLocacao(int CD_LOCACAO){
        Locacao locacao;
        LocacaoDAO locacaoDAO = new LocacaoDAO(context);
        try {
            locacao = locacaoDAO.getLocacao(CD_LOCACAO);
            locacao.opcional = locacaoDAO.getOpcionais(CD_LOCACAO);
        } catch (Exception e) {
            return null;
        }
        return locacao;
    }

    public ArrayList<Status> getLocStatus(){
        ArrayList<Status> status = new ArrayList<Status>();
        LocacaoDAO locacaoDAO = new LocacaoDAO(context);
        try {
            status = locacaoDAO.getLocStatus();
        } catch (Exception e) {
            return null;
        }
        return status;
    }

    public Message adicionaLocacao(Locacao l){
        Message message;
        LocacaoDAO locacaoDAO = new LocacaoDAO(context);
        try {
            message = locacaoDAO.adicionaLocacao(l);
            for (Opcional opcional : l.opcional) {
                message = locacaoDAO.adicionaOpcional(message.codigo, opcional.CD_OPCIONAL);
            }
            return message;
        } catch (Exception e) {
            message = new Message(false, "error service" + e, -1);
            return message;
        }
    }
    public Message atualizarLocacao(Locacao c){
        Message resp;
        LocacaoDAO locacaoDAO = new LocacaoDAO(context);
        try {
            resp = locacaoDAO.atualizaLocacao(c);
            if(locacaoDAO.removeOpcional(c.CD_LOCACAO)){
                for(Opcional opcional : c.opcional){
                    resp = locacaoDAO.adicionaOpcional(c.CD_LOCACAO, opcional.CD_OPCIONAL);
                }
            }else{
                Exception era = new Exception();
                resp.message+= " removeacessorio: " + era;
            }
        } catch (Exception e) {
            return new Message(false, "error service" + e, -1);
        }
        return resp;
    }

    public Float getSumLocacoes(String ST_LOCACAO){
        LocacaoDAO locacaoDAO = new LocacaoDAO(context);
        float soma = 0;
        try {
            soma = locacaoDAO.getSumLocacoes(ST_LOCACAO);
        } catch (Exception e) {
            return soma;
        }
        return soma;
    }
}

