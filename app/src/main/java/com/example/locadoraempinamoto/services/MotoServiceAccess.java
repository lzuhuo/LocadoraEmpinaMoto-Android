package com.example.locadoraempinamoto.services;

import android.content.Context;

import com.example.locadoraempinamoto.business.Categoria.AcessorioDAO;
import com.example.locadoraempinamoto.business.Categoria.AcessorioDAOAccess;
import com.example.locadoraempinamoto.business.MotoDAO;
import com.example.locadoraempinamoto.business.MotoDAOAccess;
import com.example.locadoraempinamoto.model.Moto.Moto;

import java.util.ArrayList;

public class MotoServiceAccess {
    private Context context;
    public MotoServiceAccess(Context data){
        this.context = data;
    }

    public Moto getMoto(int CD_MOTO){
        Moto motos;
        MotoDAOAccess conectaMotos = new MotoDAOAccess(context);
        AcessorioDAOAccess conectaAcessorios = new AcessorioDAOAccess(context);
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
        MotoDAOAccess conectaMotos = new MotoDAOAccess(context);
        try {
            motos = conectaMotos.getMotos();
        } catch (Exception e) {
            /*JOptionPane.showMessageDialog(null, "Erro \n" + e.toString());*/
        }

        return motos;
    }
}
