package com.example.locadoraempinamoto.business;

import android.content.Context;
import android.database.Cursor;

import com.example.locadoraempinamoto.db.DatabaseAccess;
import com.example.locadoraempinamoto.model.Categoria.Acessorio;
import com.example.locadoraempinamoto.model.Moto.Moto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MotoDAOAccess{
    private DatabaseAccess dA;

    public MotoDAOAccess(Context context){
        this.dA = DatabaseAccess.getInstance(context);
    }

    public ArrayList<Moto> getMoto(int CD_MOTO){
        ArrayList<Moto> catMotos = new ArrayList<Moto>();
        dA.open();
        try{

            String sql =    " SELECT *                              " +
                    " FROM motos AS MO                      " +
                    " INNER JOIN categorias_motos as CS     " +
                    " ON CS.CD_CATEGORIA = MO.CD_CATEGORIA  " +
                    " INNER JOIN categorias_motor as CR     " +
                    " ON CR.CD_MOTOR = MO.CD_MOTOR          " +
                    " WHERE MO.CD_MOTO=" + CD_MOTO;

            Cursor c = dA.database.rawQuery(sql, null);

            c.moveToFirst();
            while (!c.isAfterLast()) {
                catMotos.add(new Moto(  c.getInt(c.getColumnIndex("CD_MOTO")),
                        new com.example.locadoraempinamoto.model.Categoria.Moto(
                                c.getInt(c.getColumnIndex("CD_CATEGORIA")),
                                c.getString(c.getColumnIndex("DS_CATEGORIA"))),
                        c.getString(c.getColumnIndex("DS_MARCA")),
                        c.getString(c.getColumnIndex("DS_MODELO")),
                        c.getInt(c.getColumnIndex("NR_ANO")),
                        new com.example.locadoraempinamoto.model.Categoria.Motor(
                                c.getInt(c.getColumnIndex("CD_MOTOR")),
                                c.getString(c.getColumnIndex("DS_MOTOR"))),
                        c.getFloat(c.getColumnIndex("CP_TANQUE")),
                        c.getFloat(c.getColumnIndex("AV_CONSUMO")),
                        c.getFloat(c.getColumnIndex("VL_CUSTO")),
                        new ArrayList<Acessorio>()
                ));
                c.moveToNext();
            }
            c.close();
            dA.close();
            return catMotos;
        }catch( Exception e){ return null;}
    }

    public ArrayList<Moto> getMotos() {
        ArrayList<Moto> list = new ArrayList<>();
        dA.open();
        String sql =    " SELECT *                              " +
                        " FROM motos AS MO                      " +
                        " INNER JOIN categorias_motos as CS     " +
                        " ON CS.CD_CATEGORIA = MO.CD_CATEGORIA  " +
                        " INNER JOIN categorias_motor as CR     " +
                        " ON CR.CD_MOTOR = MO.CD_MOTOR          " +
                        " WHERE MO.ST_ATIVO='S'                 " ;

        Cursor c = dA.database.rawQuery(sql, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            list.add(new Moto(c.getInt(c.getColumnIndex("CD_MOTO")),
                    new com.example.locadoraempinamoto.model.Categoria.Moto(
                            c.getInt(c.getColumnIndex("CD_CATEGORIA")),
                            c.getString(c.getColumnIndex("DS_CATEGORIA"))),
                    c.getString(c.getColumnIndex("DS_MARCA")),
                    c.getString(c.getColumnIndex("DS_MODELO")),
                    c.getInt(c.getColumnIndex("NR_ANO")),
                    new com.example.locadoraempinamoto.model.Categoria.Motor(
                            c.getInt(c.getColumnIndex("CD_MOTOR")),
                            c.getString(c.getColumnIndex("DS_MOTOR"))),
                    c.getFloat(c.getColumnIndex("CP_TANQUE")),
                    c.getFloat(c.getColumnIndex("AV_CONSUMO")),
                    c.getFloat(c.getColumnIndex("VL_CUSTO")),
                    new ArrayList<Acessorio>()
            ));
            c.moveToNext();
        }
        c.close();
        dA.close();
        return list;
    }
}
