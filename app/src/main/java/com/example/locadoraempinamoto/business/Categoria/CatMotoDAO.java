package com.example.locadoraempinamoto.business.Categoria;

import android.content.Context;
import android.database.Cursor;

import java.sql.*;
import java.util.ArrayList;

import com.example.locadoraempinamoto.db.Config;
import com.example.locadoraempinamoto.db.DatabaseAccess;
import com.example.locadoraempinamoto.model.Categoria.CatMoto;

public class CatMotoDAO extends Config{
    private DatabaseAccess dA;

    public CatMotoDAO(Context context){
        this.dA = DatabaseAccess.getInstance(context);
    }
    public ArrayList<CatMoto> listarMotos(){
        ArrayList<CatMoto> catMotos = new ArrayList<CatMoto>();
        dA.open();
        try{
            Cursor c = dA.database.rawQuery(
                    "SELECT CD_CATEGORIA, DS_CATEGORIA FROM categorias_motos WHERE ST_ATIVO='S'",
                    null);

            c.moveToFirst();
            while (!c.isAfterLast()) {
                catMotos.add(new CatMoto(
                        c.getInt(c.getColumnIndex("CD_CATEGORIA")),
                        c.getString(c.getColumnIndex("DS_CATEGORIA"))));
                c.moveToNext();
            }
            c.close();
            dA.close();
            return catMotos;  
        }catch( Exception e){ return null;}
    }
}