package com.example.locadoraempinamoto.business;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.sql.*;
import java.util.ArrayList;

import com.example.locadoraempinamoto.db.Config;
import com.example.locadoraempinamoto.db.DatabaseAccess;
import com.example.locadoraempinamoto.model.Message;
import com.example.locadoraempinamoto.model.Cliente.Cliente;
import com.example.locadoraempinamoto.model.Moto.Locacao;
import com.example.locadoraempinamoto.model.Moto.Moto;
import com.example.locadoraempinamoto.model.Moto.Opcional;
import com.example.locadoraempinamoto.model.Moto.Status;

public class LocacaoDAO{
    private DatabaseAccess dA;

    public LocacaoDAO(Context context){
        this.dA = DatabaseAccess.getInstance(context);
    }

    public ArrayList<String> getDSMoto(String DT_INICIO, String DT_FIM){
        ArrayList<String> motos = new ArrayList<String>();
        try{
            dA.open();
            String sql = " SELECT MO.DS_MARCA FROM motos AS MO                                " +
                         " WHERE MO.CD_MOTO NOT IN                                            " +
                         " (   SELECT LO.CD_MOTO FROM locacoes AS LO                          " +
                         "     WHERE ST_LOCACAO = 'R'                                         " +
                         "     AND CAST('" + DT_INICIO + "' AS DATE) BETWEEN                  " +
                         "     CAST(LO.DT_RETIRADA AS DATE) AND CAST(LO.DT_DEVOLUCAO AS DATE) " +
                         "     AND CAST('" + DT_FIM + "' AS DATE) BETWEEN                     " +
                         "     CAST(LO.DT_RETIRADA AS DATE) AND CAST(LO.DT_DEVOLUCAO AS DATE) " +
                         " )   AND MO.ST_ATIVO='S' GROUP BY 1                                 " ;


            Cursor c = dA.database.rawQuery(sql, null);

            c.moveToFirst();
            while (!c.isAfterLast()) {
                motos.add(c.getString(c.getColumnIndex("DS_MARCA")));
                c.moveToNext();
            }

            c.close();
            dA.close();
            return motos;
        }catch( Exception e){
            return null;
        }
    }

    public ArrayList<Moto> getModelMoto(String DT_INICIO, String DT_FIM, String DS_MARCA){
        Log.d("ModelosMotos","Chegou aqui DAO");
        ArrayList<Moto> motos = new ArrayList<Moto>();
        try{
            dA.open();
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


            Cursor c = dA.database.rawQuery(sql, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                motos.add(new Moto( c.getInt(c.getColumnIndex("CD_MOTO")),
                                    c.getString(c.getColumnIndex("DS_MARCA")),
                                    c.getString(c.getColumnIndex("DS_MODELO")),
                                    c.getFloat(c.getColumnIndex("VL_CUSTO"))
                ));
                c.moveToNext();
            }
            c.close();
            dA.close();
            return motos;
        }catch( Exception e){
            Log.d("ModelosMotos",e.toString());
            return null;
        }
    }

    public ArrayList<Opcional> getOpcionais(int CD_LOCACAO){
        ArrayList<Opcional> opcionais = new ArrayList<Opcional>();
        try{
            dA.open();
            String sql = " SELECT * from locacoes_opcionais AS LS   " +
                         " LEFT JOIN locacoes AS LO                 " +
                         " ON LO.CD_LOCACAO = LS.CD_LOCACAO         " +
                         " LEFT JOIN opcionais AS O                 " +
                         " ON O.CD_OPCIONAL = LS.CD_OPCIONAL        " +
                         " WHERE LO.CD_LOCACAO = " + CD_LOCACAO       ;

            Cursor c = dA.database.rawQuery(sql, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                opcionais.add(new Opcional( c.getInt(c.getColumnIndex("CD_OPCIONAL")),
                                            c.getString(c.getColumnIndex("DS_OPCIONAL")),
                                            c.getFloat(c.getColumnIndex("VL_OPCIONAL"))
                ));
                c.moveToNext();
            }
            c.close();
            dA.close();
            return opcionais;
        }catch( Exception e){
            System.out.println(e);
            return null;
        }
    }

    public ArrayList<Opcional> listaOpcionais(){
        ArrayList<Opcional> opcionais = new ArrayList<Opcional>();
        try{
            dA.open();
            String sql = " SELECT * FROM opcionais " +
                         " WHERE ST_ATIVO = 'S' " ;

            Cursor c = dA.database.rawQuery(sql, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                opcionais.add(new Opcional( c.getInt(c.getColumnIndex("CD_OPCIONAL")), 
                                            c.getString(c.getColumnIndex("DS_OPCIONAL")), 
                                            c.getFloat(c.getColumnIndex("VL_OPCIONAL"))
                                    ));
                c.moveToNext();
            }
            c.close();
            dA.close();
            return opcionais;
        }catch( Exception e){
            System.out.println(e);
            return null;
        }
    }

    public ArrayList<Locacao> listaLocacoes(){
        ArrayList<Locacao> opcionais = new ArrayList<Locacao>();
        try{
            dA.open();
            String sql = " SELECT * FROM locacoes as LO     " +
                         " INNER JOIN motos as MO           " +
                         " ON MO.CD_MOTO = LO.CD_MOTO       " +
                         " INNER JOIN clientes as CI        " +
                         " ON CI.CD_CLIENTE = LO.CD_CLIENTE " +
                         " INNER JOIN locacoes_status AS LS " +
                         " ON LS.ST_LOCACAO = LO.ST_LOCACAO " +
                         " WHERE LO.ST_LOCACAO <> 'C'       " ;

            Cursor c = dA.database.rawQuery(sql, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                opcionais.add(new Locacao( c.getInt(c.getColumnIndex("CD_LOCACAO")), 
                                            new Moto(c.getInt(c.getColumnIndex("CD_MOTO")),
                                                    c.getString(c.getColumnIndex("DS_MARCA")),
                                                    c.getString(c.getColumnIndex("DS_MODELO")),
                                                    c.getFloat(c.getColumnIndex("VL_CUSTO"))),
                                            new Cliente(c.getInt(c.getColumnIndex("CD_CLIENTE")),
                                                        c.getString(c.getColumnIndex("NM_CLIENTE")),
                                                        c.getString(c.getColumnIndex("NR_CNH")),
                                                        c.getString(c.getColumnIndex("DT_NASCIMENTO"))),
                                            c.getString(c.getColumnIndex("DT_RETIRADA")),
                                            c.getString(c.getColumnIndex("LC_RETIRADA")),
                                            c.getString(c.getColumnIndex("DT_DEVOLUCAO")),
                                            c.getString(c.getColumnIndex("LC_DEVOLUCAO")),
                                            new Status( c.getString(c.getColumnIndex("ST_LOCACAO")),
                                                        c.getString(c.getColumnIndex("DS_LOCACAO"))),
                                            c.getFloat(c.getColumnIndex("VL_TOTAL")),
                                            new ArrayList<Opcional>()
                                    ));
                c.moveToNext();
            }
            c.close();
            dA.close();
            return opcionais;
        }catch( Exception e){
            System.out.println(e);
            return null;
        }
    }

    public ArrayList<Status> getLocStatus(){
        ArrayList<Status> status = new ArrayList<Status>();
        try {
            dA.open();
            String sql = " SELECT * FROM locacoes_status " +
                         " WHERE ST_ATIVO = 'S'          " ;

            Cursor c = dA.database.rawQuery(sql, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                status.add(new Status(  c.getString(c.getColumnIndex("ST_LOCACAO")),
                                        c.getString(c.getColumnIndex("DS_LOCACAO"))));
                c.moveToNext();
            }
            c.close();
            dA.close();
            return status;
        } catch (Exception e) {
            return null;
        }
    }

    public Locacao getLocacao(int CD_LOCACAO){
        ArrayList<Locacao> locacao = new ArrayList<Locacao>();
        
        try {
            dA.open();
            String sql =" SELECT * FROM locacoes as LO " +
                        " INNER JOIN motos as MO " +
                        " ON MO.CD_MOTO = LO.CD_MOTO " +
                        " INNER JOIN clientes as CI " +
                        " ON CI.CD_CLIENTE = LO.CD_CLIENTE " +
                        " INNER JOIN locacoes_status AS LS " +
                        " ON LS.ST_LOCACAO = LO.ST_LOCACAO " +
                        " WHERE LO.CD_LOCACAO=" + CD_LOCACAO   +
                        " LIMIT 1";

            Cursor c = dA.database.rawQuery(sql, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                locacao.add(new Locacao( c.getInt(c.getColumnIndex("CD_LOCACAO")),
                                            new Moto(   c.getInt(c.getColumnIndex("CD_MOTO")),
                                                        c.getString(c.getColumnIndex("DS_MARCA")),
                                                        c.getString(c.getColumnIndex("DS_MODELO")),
                                                        c.getFloat(c.getColumnIndex("VL_CUSTO"))),
                                            new Cliente(c.getInt(c.getColumnIndex("CD_CLIENTE")),
                                                        c.getString(c.getColumnIndex("NM_CLIENTE")),
                                                        c.getString(c.getColumnIndex("NR_CNH")),
                                                        c.getString(c.getColumnIndex("DT_NASCIMENTO"))),
                                            c.getString(c.getColumnIndex("DT_RETIRADA")),
                                            c.getString(c.getColumnIndex("LC_RETIRADA")),
                                            c.getString(c.getColumnIndex("DT_DEVOLUCAO")),
                                            c.getString(c.getColumnIndex("LC_DEVOLUCAO")),
                                            new Status( c.getString(c.getColumnIndex("ST_LOCACAO")),
                                                        c.getString(c.getColumnIndex("DS_LOCACAO"))),
                                            c.getFloat(c.getColumnIndex("VL_TOTAL")),
                                            new ArrayList<Opcional>()
                                    ));
                c.moveToNext();
            }
            c.close();
            dA.close();
            return locacao.get(0);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Message adicionaLocacao(Locacao l){
        Message resp;
        int codigo = -1;
        try{
            dA.open();
            ContentValues values = new ContentValues();

            values.put("CD_MOTO",l.moto.CD_MOTO);
            values.put("CD_CLIENTE",l.cliente.CD_CLIENTE);
            values.put("DT_RETIRADA",l.DT_RETIRADA);
            values.put("LC_RETIRADA",l.LC_RETIRADA);
            values.put("DT_DEVOLUCAO",l.DT_DEVOLUCAO);
            values.put("LC_DEVOLUCAO",l.LC_DEVOLUCAO);
            values.put("ST_LOCACAO",l.status.ST_LOCACAO);
            values.put("VL_TOTAL",l.VL_TOTAL);

            codigo = (int) dA.database.insert("locacoes", null, values);
            resp = new Message(true, "success",codigo);
            dA.close();
            return resp;
        }catch (Exception e) {
            resp = new Message(false, "error:CA+L" + e.toString(), codigo);
            return resp;
        }
    }

    public Message adicionaOpcional(int CD_LOCACAO, int CD_OPCIONAL){
        Message resp;
        int codigo = -1;
        try{
            dA.open();
            ContentValues values = new ContentValues();
            values.put("CD_LOCACAO",CD_LOCACAO);
            values.put("CD_OPCIONAL",CD_OPCIONAL);

            codigo = (int) dA.database.insert("locacoes_opcionais", null, values);
            resp = new Message(true, "success",codigo);
            dA.close();
            return resp;
        }catch (Exception e) {
            resp = new Message(false, "error:CA+LO" + e.toString(), codigo);
            return resp;
        }
    }

    //CLC
    public Message atualizaLocacao(Locacao c){
        Message resp;
        int codigo = -1;
        try{
            dA.open();
            ContentValues values = new ContentValues();
            values.put("ST_LOCACAO",c.status.ST_LOCACAO);
            values.put("VL_TOTAL",c.VL_TOTAL);

            dA.database.update("locacoes", values, "CD_LOCACAO=" + c.CD_LOCACAO,null);
            resp = new Message(true, "success",c.CD_LOCACAO);
            return resp;
        }catch (Exception e) {
            resp = new Message(false, "error:CLC" + e.toString(), codigo);
            return resp;
        }
    }

    public Boolean removeOpcional(int CD_LOCACAO){
        try{
            String sql =    " DELETE FROM locacoes_opcionais" +
                            " WHERE CD_LOCACAO=" + CD_LOCACAO;
            dA.open();
            dA.database.execSQL(sql);
            dA.close();
            return true;  
        }catch( Exception e){ return false;}
    }

    public Float getSumLocacoes(String ST_LOCACAO){
        float soma = 0;
        try {
            dA.open();

            String sql = " SELECT SUM(VL_TOTAL) FROM locacoes    " +
                         " WHERE ST_LOCACAO = '"+ ST_LOCACAO + "'" ;

            Cursor c = dA.database.rawQuery(sql, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                soma = c.getFloat(0);
            }
            return soma;
        } catch (Exception e) {
            return soma;
        }
    }
}