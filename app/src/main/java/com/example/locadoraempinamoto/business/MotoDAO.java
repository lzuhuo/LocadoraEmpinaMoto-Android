package com.example.locadoraempinamoto.business;
import android.content.Context;
import android.database.Cursor;

import java.sql.*;
import java.util.ArrayList;

import com.example.locadoraempinamoto.db.Config;
import com.example.locadoraempinamoto.db.DatabaseAccess;
import com.example.locadoraempinamoto.model.Message;
import com.example.locadoraempinamoto.model.Moto.Moto;
import com.example.locadoraempinamoto.model.Categoria.Acessorio;

public class MotoDAO extends Config{
    public ArrayList<Moto> getMoto(int CD_MOTO){
        ArrayList<Moto> catMotos = new ArrayList<Moto>();
        try{
            Statement st = conexao.createStatement();
            String sql =    " SELECT *                              " +
                            " FROM motos AS MO                      " +
                            " INNER JOIN categorias_motos as CS     " +
                            " ON CS.CD_CATEGORIA = MO.CD_CATEGORIA  " +
                            " INNER JOIN categorias_motor as CR     " +
                            " ON CR.CD_MOTOR = MO.CD_MOTOR          " +
                            " WHERE MO.CD_MOTO=" + CD_MOTO;

            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                catMotos.add(new Moto(  rs.getInt("CD_MOTO"), 
                                        new com.example.locadoraempinamoto.model.Categoria.Moto(rs.getInt("CD_CATEGORIA"), rs.getString("DS_CATEGORIA")),
                                        rs.getString("DS_MARCA"), rs.getString("DS_MODELO"), rs.getInt("NR_ANO"),
                                        new com.example.locadoraempinamoto.model.Categoria.Motor(rs.getInt("CD_MOTOR"),rs.getString("DS_MOTOR")),
                                        rs.getFloat("CP_TANQUE"), rs.getFloat("AV_CONSUMO"), rs.getFloat("VL_CUSTO"),
                                        new ArrayList<Acessorio>()
                                    ));
            }
            return catMotos;  
        }catch( SQLException e){ return null;}
    }

    public ArrayList<Moto> listarMotos(){
        ArrayList<Moto> motos = new ArrayList<Moto>();
        try{
            Statement st = conexao.createStatement();
            String sql =    " SELECT *                              " +
                            " FROM motos AS MO                      " +
                            " INNER JOIN categorias_motos as CS     " +
                            " ON CS.CD_CATEGORIA = MO.CD_CATEGORIA  " +
                            " INNER JOIN categorias_motor as CR     " +
                            " ON CR.CD_MOTOR = MO.CD_MOTOR          " +
                            " WHERE MO.ST_ATIVO='S'                 " ;

            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                motos.add(new Moto(  rs.getInt("CD_MOTO"), 
                                        new com.example.locadoraempinamoto.model.Categoria.Moto(rs.getInt("CD_CATEGORIA"), rs.getString("DS_CATEGORIA")),
                                        rs.getString("DS_MARCA"), rs.getString("DS_MODELO"), rs.getInt("NR_ANO"),
                                        new com.example.locadoraempinamoto.model.Categoria.Motor(rs.getInt("CD_MOTOR"),rs.getString("DS_MOTOR")),
                                        rs.getFloat("CP_TANQUE"), rs.getFloat("AV_CONSUMO"), rs.getFloat("VL_CUSTO"),
                                        new ArrayList<Acessorio>()
                                    ));
            }
            return motos;  
        }catch( SQLException e){ return null;}
    }

    public Message adicionaMoto(Moto m){
        Message resp;
        int codigo = -1;
        try{
            Statement st = conexao.createStatement();
            st.executeUpdate("INSERT INTO motos VALUES (NULL ," +
                            "" + m.CATMOTO.CD_CATEGORIA + "," + 
                            "'" + m.DS_MARCA + "'," + 
                            "'" + m.DS_MODELO + "'," + 
                            "" + m.NR_ANO + "," + 
                            "" + m.TP_MOTOR.CD_MOTOR + "," + 
                            "" + m.CP_TANQUE + "," + 
                            "" + m.AV_CONSUMO + "," +
                            "" + m.VL_CUSTO + "," + 
                            "'" + m.ST_ATIVO + "'" + 
                            ")"    
                            );
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                codigo = rs.getInt(1);
            }
            resp = new Message(true, "success",codigo);
            rs.close();
            return resp;
        }catch (SQLException e) {
            resp = new Message(false, "error:CA+M" + e.toString(), codigo);
            return resp;
        }
    }

    //CAM
    public Message atualizaMoto(Moto m){
        Message resp;
        int codigo = -1;
        try{
            Statement st = conexao.createStatement();
            st.executeUpdate("UPDATE motos SET " +
                            "CD_MOTO=" + m.CD_MOTO + ", " + 
                            "CD_CATEGORIA=" + m.CATMOTO.CD_CATEGORIA + ", " + 
                            "DS_MARCA='" + m.DS_MARCA + "', " + 
                            "DS_MODELO='" + m.DS_MODELO + "', " + 
                            "NR_ANO=" + m.NR_ANO + ", " + 
                            "CD_MOTOR=" + m.TP_MOTOR.CD_MOTOR + ", " + 
                            "CP_TANQUE=" + m.CP_TANQUE + ", " + 
                            "AV_CONSUMO=" + m.AV_CONSUMO + ", " +
                            "VL_CUSTO=" + m.VL_CUSTO + ", " + 
                            "ST_ATIVO='" + m.ST_ATIVO + "' " + 
                            "WHERE CD_MOTO=" + m.CD_MOTO    
                            );
            
            resp = new Message(true, "success",m.CD_MOTO );
            
            return resp;
        }catch (SQLException e) {
            resp = new Message(false, "error:CAM" + e.toString(), codigo);
            return resp;
        }
    }

    public Boolean removeMoto(int CD_MOTO){
        try{
            Statement st = conexao.createStatement();
            String sql =    " DELETE FROM motos" +
                            " WHERE CD_MOTO=" + CD_MOTO;
            st.executeUpdate(sql);
            return true;  
        }catch( SQLException e){ return false;}
    }
}