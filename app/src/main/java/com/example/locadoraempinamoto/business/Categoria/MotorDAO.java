package com.example.locadoraempinamoto.business.Categoria;

import android.content.Context;
import android.database.Cursor;

import java.sql.*;
import java.util.ArrayList;
import com.example.locadoraempinamoto.db.Config;
import com.example.locadoraempinamoto.db.DatabaseAccess;
import com.example.locadoraempinamoto.model.Categoria.Motor;


public class MotorDAO extends Config{
    private DatabaseAccess dA;

    public MotorDAO(Context context){
        this.dA = DatabaseAccess.getInstance(context);
    }

    public ArrayList<Motor> listarMotor(){
        ArrayList<Motor> motor = new ArrayList<Motor>();
        dA.open();
        try{
            Cursor c = dA.database.rawQuery(
                    "SELECT * FROM categorias_motor WHERE ST_ATIVO='S'",
                    null);

            c.moveToFirst();
            while (!c.isAfterLast()) {
                motor.add(new Motor(
                        c.getInt(c.getColumnIndex("CD_MOTOR")),
                        c.getString(c.getColumnIndex("DS_MOTOR"))));
                c.moveToNext();
            }
            c.close();
            dA.close();
            return motor;
        }catch( Exception e){ return null;}
    }
}