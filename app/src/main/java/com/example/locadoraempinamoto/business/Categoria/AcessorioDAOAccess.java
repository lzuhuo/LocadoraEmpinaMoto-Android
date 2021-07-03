package com.example.locadoraempinamoto.business.Categoria;

import android.content.Context;
import android.database.Cursor;

import com.example.locadoraempinamoto.db.DatabaseAccess;
import com.example.locadoraempinamoto.model.Categoria.Acessorio;
import com.example.locadoraempinamoto.model.Moto.Moto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AcessorioDAOAccess {
    private DatabaseAccess dA;
    public AcessorioDAOAccess(Context context){
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
                catAcessorios.add(new Acessorio(c.getInt(1), c.getString(2)));
                c.moveToNext();
            }
            c.close();
            dA.close();

            return catAcessorios;
        }catch( Exception e){ return null;}
    }
}
