package com.example.locadoraempinamoto.business.Categoria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.locadoraempinamoto.db.DatabaseAccess;
import com.example.locadoraempinamoto.model.Categoria.Acessorio;
import com.example.locadoraempinamoto.model.Message;
import com.example.locadoraempinamoto.model.Moto.AcessorioMoto;
import java.util.ArrayList;

public class AcessorioDAO {
    private DatabaseAccess dA;
    public AcessorioDAO(Context context){
        this.dA = DatabaseAccess.getInstance(context);
    }

    //CLAPM
    public ArrayList<Acessorio> listarAcessoriosPorMoto(int codigo){
        ArrayList<Acessorio> catAcessorios = new ArrayList<Acessorio>();
        try{
            dA.open();
            String sql = " SELECT  AC.CD_ACESSORIO,                 " +
                    "         AC.DS_ACESSORIO                  " +
                    " FROM acessorios AS AC                    " +
                    " INNER JOIN acessorios_moto as AM         " +
                    " ON AM.CD_ACESSORIO = AC.CD_ACESSORIO     " +
                    " WHERE AM.CD_MOTO = " + codigo;


            Cursor c = dA.database.rawQuery(sql, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                catAcessorios.add(new Acessorio(c.getInt(0), c.getString(1)));
                c.moveToNext();
            }
            c.close();
            dA.close();
            return catAcessorios;
        }catch( Exception e){
            Log.d("catAcess", "Deu erro!");
            return null;}
    }

    //CIAM
    public Message inserirAcessorioMoto(AcessorioMoto am){
        Message resp;
        try{
            dA.open();
            ContentValues values = new ContentValues();
            values.put("CD_MOTO",am.CD_MOTO);
            values.put("CD_ACESSORIO",am.CD_ACESSORIO);

            dA.database.insert("acessorios_moto", null, values);

            resp = new Message(true, "success",am.CD_MOTO);
            dA.close();
            return resp;
        }catch (Exception e) {
            resp = new Message(false, "error:CIAM" + e.toString(),am.CD_MOTO);
            return resp;
        }
    }

    //CRAM
    public Message removeAcessorioMoto(int CD_MOTO){
        Message resp;
        try{
            dA.open();
            dA.database.execSQL("DELETE FROM acessorios_moto WHERE CD_MOTO=" + CD_MOTO);
            resp = new Message(true, "success",CD_MOTO);
            dA.close();
            return resp;
        }catch (Exception e) {
            resp = new Message(false, "error:CRAM" + e.toString(),CD_MOTO);
            return resp;
        }
    }
}
