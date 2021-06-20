package com.example.locadoraempinamoto.services;

import java.util.ArrayList;

import com.example.locadoraempinamoto.business.Categoria.AcessorioDAO;
import com.example.locadoraempinamoto.model.Categoria.Acessorio;

public class AcessorioService {
    public ArrayList<Acessorio> listarAcessorios(){
        ArrayList<Acessorio> Acessorios;
        AcessorioDAO acessorioDAO = new AcessorioDAO();
        Acessorios = acessorioDAO.listarAcessorios();
        return Acessorios;
    }
}
