package com.example.locadoraempinamoto.business;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.locadoraempinamoto.db.DatabaseAccess;
import com.example.locadoraempinamoto.model.Categoria.Acessorio;
import com.example.locadoraempinamoto.model.Categoria.CatMoto;
import com.example.locadoraempinamoto.model.Categoria.Motor;
import com.example.locadoraempinamoto.model.Message;
import com.example.locadoraempinamoto.model.Moto.Moto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MotoDAO {
    private DatabaseAccess dA;

    public MotoDAO(Context context){
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
                        new CatMoto(
                                c.getInt(c.getColumnIndex("CD_CATEGORIA")),
                                c.getString(c.getColumnIndex("DS_CATEGORIA"))),
                        c.getString(c.getColumnIndex("DS_MARCA")),
                        c.getString(c.getColumnIndex("DS_MODELO")),
                        c.getInt(c.getColumnIndex("NR_ANO")),
                        new Motor(
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
                    new CatMoto(
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

    public Message adicionaMoto(Moto m){
        Message resp;
        int codigo = -1;
        try{
            dA.open();
            ContentValues values = new ContentValues();

            values.put("CD_CATEGORIA", m.CATMOTO.CD_CATEGORIA);
            values.put("DS_MARCA", m.DS_MARCA);
            values.put("DS_MODELO", m.DS_MODELO);
            values.put("NR_ANO", m.NR_ANO);
            values.put("CD_MOTOR", m.TP_MOTOR.CD_MOTOR);
            values.put("CP_TANQUE", m.CP_TANQUE);
            values.put("AV_CONSUMO", m.AV_CONSUMO);
            values.put("VL_CUSTO", m.VL_CUSTO);
            values.put("ST_ATIVO", m.ST_ATIVO);

            codigo = (int) dA.database.insert("motos", null, values);

            resp = new Message(true, "success", codigo);

            dA.close();
            return resp;
        }catch (Exception e) {
            resp = new Message(false, "error:CA+M" + e.toString(), codigo);
            return resp;
        }
    }

    public Message atualizaMoto(Moto m){
        Message resp;
        int codigo = -1;
        try{
            dA.open();
            ContentValues values = new ContentValues();

            values.put("CD_CATEGORIA", m.CATMOTO.CD_CATEGORIA);
            values.put("DS_MARCA", m.DS_MARCA);
            values.put("DS_MODELO", m.DS_MODELO);
            values.put("NR_ANO", m.NR_ANO);
            values.put("CD_MOTOR", m.TP_MOTOR.CD_MOTOR);
            values.put("CP_TANQUE", m.CP_TANQUE);
            values.put("AV_CONSUMO", m.AV_CONSUMO);
            values.put("VL_CUSTO", m.VL_CUSTO);
            values.put("ST_ATIVO", m.ST_ATIVO);

            dA.database.update("motos", values, "CD_MOTO=" + m.CD_MOTO,null);

            resp = new Message(true, "success",m.CD_MOTO );
            dA.close();

            return resp;
        }catch (Exception e) {
            resp = new Message(false, "error:CAM" + e.toString(), codigo);
            return resp;
        }
    }

    public Boolean removeMoto(int CD_MOTO){
        try{
            dA.open();
            dA.database.execSQL(" DELETE FROM motos WHERE CD_MOTO=" + CD_MOTO);
            dA.close();
            Log.d("RMoto","Remove Moto");
            return true;
        }catch( Exception e){ return false;}
    }
}
