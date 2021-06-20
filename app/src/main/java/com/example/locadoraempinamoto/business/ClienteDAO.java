package com.example.locadoraempinamoto.business;
import java.sql.*;
import java.util.ArrayList;

import com.example.locadoraempinamoto.db.Config;
import com.example.locadoraempinamoto.model.Cliente.Cliente;
import com.example.locadoraempinamoto.model.Message;

public class ClienteDAO extends Config{
    public Cliente getCliente(int CD_CLIENTE){
        Cliente cliente = new Cliente();
        try{
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM clientes WHERE CD_CLIENTE=" + CD_CLIENTE);
            while(rs.next()){
                cliente =new Cliente(   rs.getInt("CD_CLIENTE"),
                                        rs.getString("NM_CLIENTE"),
                                        rs.getString("DT_NASCIMENTO"),
                                        rs.getString("NR_CNH"),
                                        rs.getString("ST_ATIVO")
                                    );
            }
            return cliente;
        }catch( SQLException e){return null;}
    }

    public ArrayList<Cliente> getClientes(String NR_CNH){
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        try{
            Statement st = conexao.createStatement();
            String sql =    "SELECT * FROM clientes " +
                            "WHERE NR_CNH LIKE '" + NR_CNH + "%' LIMIT 10";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                clientes.add(new Cliente(   rs.getInt("CD_CLIENTE"),
                                            rs.getString("NM_CLIENTE"),
                                            rs.getString("DT_NASCIMENTO"),
                                            rs.getString("NR_CNH"),
                                            rs.getString("ST_ATIVO")
                                        ));
            }
            
            st.close();
            
        }catch( SQLException e){
            System.out.println(e) ;
        }
        return clientes;
    }

    public ArrayList<Cliente> listarClientes(){
        ArrayList<Cliente> clientes =  new ArrayList<Cliente>();
        try{
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM clientes WHERE ST_ATIVO='S'");
            while(rs.next()){
                clientes.add(new Cliente(   rs.getInt("CD_CLIENTE"),
                                            rs.getString("NM_CLIENTE"),
                                            rs.getString("DT_NASCIMENTO"),
                                            rs.getString("NR_CNH"),
                                            rs.getString("ST_ATIVO")
                                        ));
            }
            return clientes;
        }catch( SQLException e){return null;}
    }

    public Message adicionaCliente(Cliente c){
        Message resp;
        int codigo = -1;
        try{
            Statement st = conexao.createStatement();
            st.executeUpdate("INSERT INTO clientes VALUES (NULL, '"
            + c.NM_CLIENTE + "'," + " '" + c.DT_NASCIMENTO
            + "', '" + c.NR_CNH + "', '"+ c.ST_ATIVO +"')");

            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                codigo = rs.getInt(1);
            }
            resp = new Message(true, "success",codigo);
            rs.close();
            st.close();
            
            return resp;
        }catch (SQLException e) {
            resp = new Message(false, "error" + e.toString(),codigo);
            return resp;
        }
    }
    
    //CAC
    public Message atualizaCliente(Cliente c){
        Message resp;
        int codigo = -1;
        try{
            Statement st = conexao.createStatement();
            st.executeUpdate("UPDATE clientes SET " +
                            "NM_CLIENTE='" + c.NM_CLIENTE + "', " + 
                            "DT_NASCIMENTO='" + c.DT_NASCIMENTO + "', " + 
                            "NR_CNH='" + c.NR_CNH + "' " + 
                            "WHERE CD_CLIENTE=" + c.CD_CLIENTE    
                            );
            
            resp = new Message(true, "success",c.CD_CLIENTE);
            
            return resp;
        }catch (SQLException e) {
            resp = new Message(false, "error:CAC" + e.toString(), codigo);
            return resp;
        }
    }

    public Boolean removeCliente(int CD_CLIENTE){
        try{
            Statement st = conexao.createStatement();
            String sql =    " DELETE FROM clientes" +
                            " WHERE CD_CLIENTE=" + CD_CLIENTE;
            st.executeUpdate(sql);
            return true;  
        }catch( SQLException e){ return false;}
    }
}