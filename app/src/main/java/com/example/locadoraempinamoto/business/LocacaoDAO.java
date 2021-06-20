package com.example.locadoraempinamoto.business;
import java.sql.*;
import java.util.ArrayList;

import com.example.locadoraempinamoto.db.Config;
import com.example.locadoraempinamoto.model.Message;
import com.example.locadoraempinamoto.model.Cliente.Cliente;
import com.example.locadoraempinamoto.model.Moto.Locacao;
import com.example.locadoraempinamoto.model.Moto.Moto;
import com.example.locadoraempinamoto.model.Moto.Opcional;
import com.example.locadoraempinamoto.model.Moto.Status;

public class LocacaoDAO extends Config{

    public ArrayList<String> getDSMoto(String DT_INICIO, String DT_FIM){
        ArrayList<String> motos = new ArrayList<String>();
        try{
            Statement st = conexao.createStatement();
            String sql = " SELECT MO.DS_MARCA FROM motos AS MO                                " +
                         " WHERE MO.CD_MOTO NOT IN                                            " +
                         " (   SELECT LO.CD_MOTO FROM locacoes AS LO                          " +
                         "     WHERE ST_LOCACAO = 'R'                                         " +
                         "     AND CAST('" + DT_INICIO + "' AS DATE) BETWEEN                  " +
                         "     CAST(LO.DT_RETIRADA AS DATE) AND CAST(LO.DT_DEVOLUCAO AS DATE) " +
                         "     AND CAST('" + DT_FIM + "' AS DATE) BETWEEN                     " +
                         "     CAST(LO.DT_RETIRADA AS DATE) AND CAST(LO.DT_DEVOLUCAO AS DATE) " +
                         " )   AND MO.ST_ATIVO='S' GROUP BY 1                                 " ;
            

            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                motos.add(rs.getString("DS_MARCA"));
            }
            st.close();
            return motos;
        }catch( SQLException e){
            System.out.println(e);
            return null;
        }
    }

    public ArrayList<Moto> getModelMoto(String DT_INICIO, String DT_FIM, String DS_MARCA){
        ArrayList<Moto> motos = new ArrayList<Moto>();
        try{
            Statement st = conexao.createStatement();
            String sql =  " SELECT * FROM motos AS MO                                   " +
                          " WHERE MO.CD_MOTO NOT IN                                     " +
                          " (   SELECT LO.CD_MOTO FROM locacoes AS LO                   " +
                          "     WHERE ST_LOCACAO = 'R'                                  " +
                          "     AND ( (DATE('" + DT_INICIO + "') BETWEEN                " +
                          "     DATE(LO.DT_RETIRADA) AND DATE(LO.DT_DEVOLUCAO))         " +
                          "     OR (DATE('" + DT_FIM + "') BETWEEN                      " +
                          "     DATE(LO.DT_RETIRADA) AND DATE(LO.DT_DEVOLUCAO)))        " +
                          " )   AND MO.DS_MARCA = '"+ DS_MARCA + "' AND MO.ST_ATIVO='S' " +
                          "     GROUP BY DS_MODELO                                      " ;
            

            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                motos.add(new Moto( rs.getInt("CD_MOTO"), rs.getString("DS_MARCA"), 
                                    rs.getString("DS_MODELO"), rs.getFloat("VL_CUSTO")
                                    ));
            }
            st.close();
            return motos;
        }catch( SQLException e){
            System.out.println(e);
            return null;
        }
    }

    public ArrayList<Opcional> getOpcionais(int CD_LOCACAO){
        ArrayList<Opcional> opcionais = new ArrayList<Opcional>();
        try{
            Statement st = conexao.createStatement();
            String sql = " SELECT * from locacoes_opcionais AS LS   " +
                         " LEFT JOIN locacoes AS LO                 " +
                         " ON LO.CD_LOCACAO = LS.CD_LOCACAO         " +
                         " LEFT JOIN opcionais AS O                 " +
                         " ON O.CD_OPCIONAL = LS.CD_OPCIONAL        " +
                         " WHERE LO.CD_LOCACAO = " + CD_LOCACAO       ;
            
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                opcionais.add(new Opcional( rs.getInt("CD_OPCIONAL"), rs.getString("DS_OPCIONAL"), 
                                    rs.getFloat("VL_OPCIONAL")
                                    ));
            }
            rs.close();
            return opcionais;
        }catch( SQLException e){
            System.out.println(e);
            return null;
        }
    }

    public ArrayList<Opcional> listaOpcionais(){
        ArrayList<Opcional> opcionais = new ArrayList<Opcional>();
        try{
            Statement st = conexao.createStatement();
            String sql = " SELECT * FROM opcionais " +
                         " WHERE ST_ATIVO = 'S' " ;
            

            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                opcionais.add(new Opcional( rs.getInt("CD_OPCIONAL"), rs.getString("DS_OPCIONAL"), 
                                    rs.getFloat("VL_OPCIONAL")
                                    ));
            }
            rs.close();
            return opcionais;
        }catch( SQLException e){
            System.out.println(e);
            return null;
        }
    }

    public ArrayList<Locacao> listaLocacoes(){
        ArrayList<Locacao> opcionais = new ArrayList<Locacao>();
        try{
            Statement st = conexao.createStatement();
            String sql = " SELECT * FROM locacoes as LO     " +
                         " INNER JOIN motos as MO           " +
                         " ON MO.CD_MOTO = LO.CD_MOTO       " +
                         " INNER JOIN clientes as CI        " +
                         " ON CI.CD_CLIENTE = LO.CD_CLIENTE " +
                         " INNER JOIN locacoes_status AS LS " +
                         " ON LS.ST_LOCACAO = LO.ST_LOCACAO " +
                         " WHERE LO.ST_LOCACAO <> 'C'       " ;

            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                opcionais.add(new Locacao( rs.getInt("CD_LOCACAO"), 
                                            new Moto(rs.getInt("CD_MOTO"),rs.getString("DS_MODELO")), 
                                            new Cliente(rs.getInt("CD_CLIENTE"),rs.getString("NM_CLIENTE"),
                                            rs.getString("NR_CNH"),rs.getString("DT_NASCIMENTO")), 
                                            rs.getString("DT_RETIRADA"), rs.getString("LC_RETIRADA"), 
                                            rs.getString("DT_DEVOLUCAO"), rs.getString("LC_DEVOLUCAO"), 
                                            new Status(rs.getString("ST_LOCACAO"), rs.getString("DS_LOCACAO")),
                                            rs.getFloat("VL_TOTAL"),                                            
                                            new ArrayList<Opcional>()
                                    ));
            }
            rs.close();
            return opcionais;
        }catch( SQLException e){
            System.out.println(e);
            return null;
        }
    }

    public ArrayList<Status> getLocStatus(){
        ArrayList<Status> status = new ArrayList<Status>();
        try {
            Statement st = conexao.createStatement();
            String sql = " SELECT * FROM locacoes_status " +
                         " WHERE ST_ATIVO = 'S'          " ;
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                status.add(new Status(rs.getString("ST_LOCACAO"), rs.getString("DS_LOCACAO")));
            }
            return status;
        } catch (Exception e) {
            return null;
        }
    }

    public Locacao getLocacao(int CD_LOCACAO){
        ArrayList<Locacao> locacao = new ArrayList<Locacao>();
        
        try {
            Statement st = conexao.createStatement();
            String sql =" SELECT * FROM locacoes as LO " +
                        " INNER JOIN motos as MO " +
                        " ON MO.CD_MOTO = LO.CD_MOTO " +
                        " INNER JOIN clientes as CI " +
                        " ON CI.CD_CLIENTE = LO.CD_CLIENTE " +
                        " INNER JOIN locacoes_status AS LS " +
                        " ON LS.ST_LOCACAO = LO.ST_LOCACAO " +
                        " WHERE LO.ST_LOCACAO = 'R'        " +
                        " AND LO.CD_LOCACAO=" + CD_LOCACAO   +
                        " LIMIT 1";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                locacao.add(new Locacao( rs.getInt("CD_LOCACAO"), 
                                            new Moto(rs.getInt("CD_MOTO"),rs.getString("DS_MARCA"),
                                            rs.getString("DS_MODELO"),rs.getFloat("VL_CUSTO")), 
                                            new Cliente(rs.getInt("CD_CLIENTE"),rs.getString("NM_CLIENTE"),
                                            rs.getString("NR_CNH"),rs.getString("DT_NASCIMENTO")), 
                                            rs.getString("DT_RETIRADA"), rs.getString("LC_RETIRADA"), 
                                            rs.getString("DT_DEVOLUCAO"), rs.getString("LC_DEVOLUCAO"), 
                                            new Status(rs.getString("ST_LOCACAO"), rs.getString("DS_LOCACAO")),
                                            rs.getFloat("VL_TOTAL"),                                            
                                            new ArrayList<Opcional>()
                                    ));
            }
            rs.close();
            return locacao.get(0);
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public Message adicionaLocacao(Locacao l){
        Message resp;
        int codigo = -1;
        try{
            Statement st = conexao.createStatement();
            st.executeUpdate("INSERT INTO locacoes VALUES (NULL ," +
                            "" + l.moto.CD_MOTO + "," + 
                            "" + l.cliente.CD_CLIENTE + "," + 
                            "'" + l.DT_RETIRADA + "'," + 
                            "'" + l.LC_RETIRADA + "'," + 
                            "'" + l.DT_DEVOLUCAO + "'," + 
                            "'" + l.LC_DEVOLUCAO + "'," + 
                            "'" + l.status.ST_LOCACAO + "'," + 
                            "" + l.VL_TOTAL + "" + 
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
            resp = new Message(false, "error:CA+L" + e.toString(), codigo);
            return resp;
        }
    }

    public Message adicionaOpcional(int CD_LOCACAO, int CD_OPCIONAL){
        Message resp;
        int codigo = -1;
        try{
            Statement st = conexao.createStatement();
            st.executeUpdate("INSERT INTO locacoes_opcionais VALUES (" +
                            "" + CD_LOCACAO + "," + 
                            "" + CD_OPCIONAL + "" +
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
            resp = new Message(false, "error:CA+LO" + e.toString(), codigo);
            return resp;
        }
    }

    //CLC
    public Message atualizaLocacao(Locacao c){
        Message resp;
        int codigo = -1;
        try{
            Statement st = conexao.createStatement();
            String sql = "UPDATE locacoes SET                       " +
                         "ST_LOCACAO='" + c.status.ST_LOCACAO + "', " + 
                         "VL_TOTAL=" + c.VL_TOTAL + "               " + 
                         "WHERE CD_LOCACAO=" + c.CD_LOCACAO ;
            st.executeUpdate(sql);
            
            resp = new Message(true, "success",c.CD_LOCACAO);
            return resp;
        }catch (SQLException e) {
            resp = new Message(false, "error:CLC" + e.toString(), codigo);
            return resp;
        }
    }

    public Boolean removeOpcional(int CD_LOCACAO){
        try{
            Statement st = conexao.createStatement();
            String sql =    " DELETE FROM locacoes_opcionais" +
                            " WHERE CD_LOCACAO=" + CD_LOCACAO;
            st.executeUpdate(sql);
            return true;  
        }catch( SQLException e){ return false;}
    }

    public Float getSumLocacoes(String ST_LOCACAO){
        float soma = 0;
        try {
            Statement st = conexao.createStatement();
            String sql = " SELECT SUM(VL_TOTAL) FROM locacoes    " +
                         " WHERE ST_LOCACAO = '"+ ST_LOCACAO + "'" ; 
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                soma = rs.getFloat(1);
            }
            return soma;
        } catch (Exception e) {
            return soma;
        }
    }
}