package com.example.locadoraempinamoto.business;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.sql.*;
import java.util.ArrayList;

import com.example.locadoraempinamoto.db.Config;
import com.example.locadoraempinamoto.db.DatabaseAccess;
import com.example.locadoraempinamoto.model.Cliente.Cliente;
import com.example.locadoraempinamoto.model.Message;

public class ClienteDAO{
    private DatabaseAccess dA;

    public ClienteDAO(Context context){
        this.dA = DatabaseAccess.getInstance(context);
    }

    public Cliente getCliente(int CD_CLIENTE){
        Cliente cliente = new Cliente();
        try{
            dA.open();
            Cursor c = dA.database.rawQuery("SELECT * FROM clientes WHERE CD_CLIENTE=" + CD_CLIENTE, null);

            c.moveToFirst();
            while (!c.isAfterLast()) {
                cliente =new Cliente(   c.getInt(c.getColumnIndex("CD_CLIENTE")),
                                        c.getString(c.getColumnIndex("NM_CLIENTE")),
                                        c.getString(c.getColumnIndex("DT_NASCIMENTO")),
                                        c.getString(c.getColumnIndex("NR_CNH")),
                                        c.getString(c.getColumnIndex("ST_ATIVO"))
                                    );
                c.moveToNext();
            }
            c.close();
            dA.close();
            return cliente;
        }catch( Exception e){return null;}
    }

    public ArrayList<Cliente> getClientes(String NR_CNH){
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        try{
            dA.open();
            String sql =    "SELECT * FROM clientes " +
                            "WHERE NR_CNH LIKE '" + NR_CNH + "%'";

            Cursor c = dA.database.rawQuery(sql, null);

            c.moveToFirst();
            while (!c.isAfterLast()) {
                clientes.add(new Cliente(   c.getInt(c.getColumnIndex("CD_CLIENTE")),
                                            c.getString(c.getColumnIndex("NM_CLIENTE")),
                                            c.getString(c.getColumnIndex("DT_NASCIMENTO")),
                                            c.getString(c.getColumnIndex("NR_CNH")),
                                            c.getString(c.getColumnIndex("ST_ATIVO"))
                                        ));
                c.moveToNext();
            }

            c.close();
            dA.close();
            
        }catch( Exception e){
            System.out.println(e) ;
        }
        return clientes;
    }

    public ArrayList<Cliente> listarClientes(){
        ArrayList<Cliente> clientes =  new ArrayList<Cliente>();
        try{
            dA.open();

            Cursor c = dA.database.rawQuery("SELECT * FROM clientes WHERE ST_ATIVO='S'", null);

            c.moveToFirst();
            while (!c.isAfterLast()) {
                clientes.add(new Cliente(   c.getInt(c.getColumnIndex("CD_CLIENTE")),
                                            c.getString(c.getColumnIndex("NM_CLIENTE")),
                                            c.getString(c.getColumnIndex("DT_NASCIMENTO")),
                                            c.getString(c.getColumnIndex("NR_CNH")),
                                            c.getString(c.getColumnIndex("ST_ATIVO"))
                ));
                c.moveToNext();
            }
            c.close();
            dA.close();
            return clientes;
        }catch( Exception e){return null;}
    }

    public Message adicionaCliente(Cliente c){
        Message resp;
        int codigo = -1;
        try{
            dA.open();
            ContentValues values = new ContentValues();

            values.put("NM_CLIENTE",c.NM_CLIENTE);
            values.put("DT_NASCIMENTO",c.DT_NASCIMENTO);
            values.put("NR_CNH",c.NR_CNH);
            values.put("ST_ATIVO","S");

            codigo = (int) dA.database.insert("clientes", null, values);
            resp = new Message(true, "success",codigo);
            dA.close();
            return resp;
        }catch (Exception e) {
            resp = new Message(false, "error" + e.toString(),codigo);
            return resp;
        }
    }
    
    //CAC
    public Message atualizaCliente(Cliente c){
        Message resp;
        int codigo = -1;
        try{
            dA.open();
            ContentValues values = new ContentValues();
            values.put("NM_CLIENTE",c.NM_CLIENTE);
            values.put("DT_NASCIMENTO",c.DT_NASCIMENTO);
            values.put("NR_CNH",c.NR_CNH);

            dA.database.update("clientes", values, "CD_CLIENTE=" + c.CD_CLIENTE,null);
            resp = new Message(true, "success",c.CD_CLIENTE);
            dA.close();
            return resp;
        }catch (Exception e) {
            resp = new Message(false, "error:CAC" + e.toString(), codigo);
            return resp;
        }
    }

    public Boolean removeCliente(int CD_CLIENTE){
        try{
            dA.open();
            dA.database.execSQL(" DELETE FROM clientes WHERE CD_CLIENTE=" + CD_CLIENTE);
            dA.close();
            return true;  
        }catch( Exception e){ return false;}
    }
}