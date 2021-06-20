package com.example.locadoraempinamoto.services;

import java.util.ArrayList;

import com.example.locadoraempinamoto.business.LocacaoDAO;
import com.example.locadoraempinamoto.model.Message;
import com.example.locadoraempinamoto.model.Moto.Locacao;
import com.example.locadoraempinamoto.model.Moto.Moto;
import com.example.locadoraempinamoto.model.Moto.Opcional;
import com.example.locadoraempinamoto.model.Moto.Status;
import util.Util;

public class LocacaoService {
    private LocacaoDAO locacaoDAO;

    public LocacaoService(){
        this.locacaoDAO = new LocacaoDAO();
    }
    public ArrayList<String> getDSMoto(String DT_INICIO, String DT_FIM){
        ArrayList<String> motos = new ArrayList<String>();
        
        try {
            motos = locacaoDAO.getDSMoto(Util.dataFormatSQL(DT_INICIO), Util.dataFormatSQL(DT_FIM));
        } catch (Exception e) {
            return null;
        }
        return motos;
    }

    public ArrayList<Moto> getModelMoto(String DT_INICIO, String DT_FIM, String DS_MARCA){
        ArrayList<Moto> motos = new ArrayList<Moto>();
        
        try {
            motos = locacaoDAO.getModelMoto(Util.dataFormatSQL(DT_INICIO), Util.dataFormatSQL(DT_FIM), DS_MARCA);
        } catch (Exception e) {
            return null;
        }
        return motos;
    }

    public ArrayList<Opcional> listaOpcionais(){
        ArrayList<Opcional> opcionais = new ArrayList<Opcional>();

        try {
            opcionais = locacaoDAO.listaOpcionais();
        } catch (Exception e) {
            return null;
        }
        return opcionais;
    }

    public ArrayList<Locacao> listaLocacoes(){
        ArrayList<Locacao> locacoes = new ArrayList<Locacao>();

        try {
            locacoes = locacaoDAO.listaLocacoes();
        } catch (Exception e) {
            return null;
        }
        return locacoes;
    }

    public Locacao getLocacao(int CD_LOCACAO){
        Locacao locacao;

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
        try {
            status = locacaoDAO.getLocStatus();
        } catch (Exception e) {
            return null;
        }
        return status;
    }

    public Message adicionaLocacao(Locacao l){
        Message message;

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
    public Message atualizaLocacao(Locacao c){
        Message resp;
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
        float soma = 0;
        try {
            soma = locacaoDAO.getSumLocacoes(ST_LOCACAO);
        } catch (Exception e) {
            return soma;
        }
        return soma;
    }
}

