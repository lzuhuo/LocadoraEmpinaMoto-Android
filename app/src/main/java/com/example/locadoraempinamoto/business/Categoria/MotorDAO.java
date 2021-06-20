package com.example.locadoraempinamoto.business.Categoria;

import java.sql.*;
import java.util.ArrayList;
import com.example.locadoraempinamoto.db.Config;
import com.example.locadoraempinamoto.model.Categoria.Motor;


public class MotorDAO extends Config{
    public ArrayList<Motor> listarMotor(){
        ArrayList<Motor> catMotos = new ArrayList<Motor>();
        try{
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM categorias_motor WHERE ST_ATIVO='S'");
            while (rs.next()) {
                catMotos.add(new Motor(rs.getInt(1), rs.getString(2)));
            }
            return catMotos;  
        }catch( SQLException e){ return null;}
    }
}