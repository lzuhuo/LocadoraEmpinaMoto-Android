package com.example.locadoraempinamoto.business.Categoria;

import java.sql.*;
import java.util.ArrayList;
import com.example.locadoraempinamoto.db.Config;
import com.example.locadoraempinamoto.model.Message;
import com.example.locadoraempinamoto.model.Categoria.Acessorio;
import com.example.locadoraempinamoto.model.Moto.AcessorioMoto;


public class AcessorioDAO extends Config{
    public ArrayList<Acessorio> listarAcessorios(){
        ArrayList<Acessorio> catAcessorios = new ArrayList<Acessorio>();
        try{
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery("SELECT CD_ACESSORIO, DS_ACESSORIO FROM acessorios WHERE ST_ATIVO='S'");
            while (rs.next()) {
                catAcessorios.add(new Acessorio(rs.getInt(1), rs.getString(2)));
            }
            return catAcessorios;  
        }catch( SQLException e){ return null;}
    }

    //CLAPM
    public ArrayList<Acessorio> listarAcessoriosPorMoto(int codigo){
        ArrayList<Acessorio> catAcessorios = new ArrayList<Acessorio>();
        try{
            Statement st = conexao.createStatement();
            String sql = " SELECT  AC.CD_ACESSORIO,                 " +
                         "         AC.DS_ACESSORIO                  " +
                         " FROM acessorios AS AC                    " +
                         " INNER JOIN acessorios_moto as AM         " +
                         " ON AM.CD_ACESSORIO = AC.CD_ACESSORIO     " +
                         " WHERE AM.CD_MOTO = " + codigo;
            
    
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                catAcessorios.add(new Acessorio(rs.getInt(1), rs.getString(2)));
            }
            return catAcessorios;  
        }catch( SQLException e){ return null;}
    }

    //CIAM
    public Message inserirAcessorioMoto(AcessorioMoto c){
        Message resp;
        try{
            Statement st = conexao.createStatement();
            st.executeUpdate("INSERT INTO acessorios_moto VALUES (" 
                             + c.CD_MOTO + ","
                             + c.CD_ACESSORIO + ")");
            resp = new Message(true, "success",c.CD_MOTO);
            st.close();
            return resp;
        }catch (SQLException e) {
            resp = new Message(false, "error:CIAM" + e.toString(),c.CD_MOTO);
            return resp;
        }
    }

    //CRAM
    public Message removeAcessorioMoto(int CD_MOTO){
        Message resp;
        try{
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM acessorios_moto WHERE CD_MOTO=" + CD_MOTO );
            resp = new Message(true, "success",CD_MOTO);
            st.close();
            return resp;
        }catch (SQLException e) {
            resp = new Message(false, "error:CRAM" + e.toString(),CD_MOTO);
            return resp;
        }
    }
}