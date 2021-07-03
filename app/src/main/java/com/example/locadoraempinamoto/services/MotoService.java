package com.example.locadoraempinamoto.services;

import android.content.Context;
import android.util.Log;

import com.example.locadoraempinamoto.business.Categoria.AcessorioDAO;
import com.example.locadoraempinamoto.business.Categoria.CatMotoDAO;
import com.example.locadoraempinamoto.business.Categoria.MotorDAO;
import com.example.locadoraempinamoto.business.MotoDAO;
import com.example.locadoraempinamoto.model.Categoria.Acessorio;
import com.example.locadoraempinamoto.model.Categoria.CatMoto;
import com.example.locadoraempinamoto.model.Categoria.Motor;
import com.example.locadoraempinamoto.model.Message;
import com.example.locadoraempinamoto.model.Moto.AcessorioMoto;
import com.example.locadoraempinamoto.model.Moto.Moto;

import java.util.ArrayList;

public class MotoService {
    private Context context;
    public MotoService(Context data){
        this.context = data;
    }

    public Moto getMoto(int CD_MOTO){
        Moto motos;
        MotoDAO conectaMotos = new MotoDAO(context);
        AcessorioDAO conectaAcessorios = new AcessorioDAO(context);
        try {
            motos = new Moto(conectaMotos.getMoto(CD_MOTO).get(0));
            motos.Acessorios = conectaAcessorios.listarAcessoriosPorMoto(motos.CD_MOTO);
        } catch (Exception e) {
            /*JOptionPane.showMessageDialog(null, "Erro \n" + e.toString());*/
            return null;
        }
        return motos;
    }

    public ArrayList<Moto> listarMotos(){
        ArrayList<Moto> motos = new ArrayList<Moto>();
        MotoDAO conectaMotos = new MotoDAO(context);
        try {
            motos = conectaMotos.getMotos();
        } catch (Exception e) {
            /*JOptionPane.showMessageDialog(null, "Erro \n" + e.toString());*/
        }

        return motos;
    }

    public ArrayList<CatMoto> listarCatMotos(){
        ArrayList<CatMoto> catMotos = new ArrayList<CatMoto>();
        CatMotoDAO conectaMotos = new CatMotoDAO(context);
        try {
            catMotos = conectaMotos.listarMotos();

        } catch (Exception e) {
            /*JOptionPane.showMessageDialog(null, "Erro \n" + e.toString());*/
        }

        return catMotos;
    }

    public ArrayList<Motor> listarMotor(){
        ArrayList<Motor> motores;
        MotorDAO motorDAO = new MotorDAO(context);
        motores = motorDAO.listarMotor();
        return motores;
    }

    public Message adicionarMoto(Moto moto){
        Message messageMoto;
        Message messageAcessorio;
        MotoDAO motoDAO = new MotoDAO(context);
        AcessorioDAO acessorioDAO = new AcessorioDAO(context);
        messageMoto = motoDAO.adicionaMoto(moto);
        try {
            for (Acessorio acessorio : moto.Acessorios) {
                Log.d("InsertAcessorio", String.format("CD_MOTO: %d, CD_ACESSORIO: %d",messageMoto.codigo, acessorio.CD_ACESSORIO));
                AcessorioMoto am = new AcessorioMoto(messageMoto.codigo, acessorio.CD_ACESSORIO);
                messageAcessorio = acessorioDAO.inserirAcessorioMoto(am);
                if(!messageAcessorio.status){
                    messageMoto = messageAcessorio;
                    break;
                }
            }
        } catch (Exception e) {
        }
        return messageMoto;
    }

    public Message atualizaMoto(Moto moto){
        Message messageMoto;
        Message messageAcessorio;
        MotoDAO motoDAO = new MotoDAO(context);
        AcessorioDAO acessorioDAO = new AcessorioDAO(context);
        messageMoto = motoDAO.atualizaMoto(moto);
        try {
            messageAcessorio = acessorioDAO.removeAcessorioMoto(moto.CD_MOTO);
            if(!messageAcessorio.status){
                Exception era = new Exception();
                messageAcessorio.message+= " removeacessorio: " + era;
                messageMoto = messageAcessorio;
            }
            for (Acessorio acessorio : moto.Acessorios) {
                AcessorioMoto am = new AcessorioMoto(messageMoto.codigo, acessorio.CD_ACESSORIO);
                messageAcessorio = acessorioDAO.inserirAcessorioMoto(am);
                if(!messageAcessorio.status){
                    messageMoto = messageAcessorio;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("errorservice: "+e);
            return null;
        }
        return messageMoto;
    }

    public Boolean removeMoto(int CD_MOTO){
        Boolean respostaMoto;
        Boolean respostaAcessorio;
        MotoDAO motoDAO = new MotoDAO(context);
        AcessorioDAO acessorioDAO = new AcessorioDAO(context);
        try {
            respostaMoto = motoDAO.removeMoto(CD_MOTO);
            respostaAcessorio = acessorioDAO.removeAcessorioMoto(CD_MOTO).status;
            if(!respostaAcessorio && !respostaMoto){
                Exception removM = new Exception("Erro ao remover moto");
                removM.notify();
            }
        } catch (Exception e) {
            Log.d("errorservice: ",e.toString());
            return null;
        }
        return respostaMoto;
    }
}
