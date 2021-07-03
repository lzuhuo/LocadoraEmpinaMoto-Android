package com.example.locadoraempinamoto.ui.moto;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.locadoraempinamoto.R;
import com.example.locadoraempinamoto.model.Categoria.Acessorio;
import com.example.locadoraempinamoto.model.Categoria.CatMoto;
import com.example.locadoraempinamoto.model.Categoria.Motor;
import com.example.locadoraempinamoto.model.Moto.Moto;
import com.example.locadoraempinamoto.services.MotoService;

import java.util.ArrayList;
import java.util.List;

public class MotoItemController {
    private Spinner motoCategoriaField;
    private EditText motoMarcaField;
    private EditText motoModeloField;
    private EditText motoAnoField;
    private Spinner motoTipoMotorField;
    private EditText motoCapTanqueField;
    private EditText motoMediaConsumoField;
    private EditText motoValorCustoField;
    private CheckBox motoAcessorio01;
    private CheckBox motoAcessorio02;
    private CheckBox motoAcessorio03;

    public MotoItemController(View v){
        motoCategoriaField = v.findViewById(R.id.clienteNomeField);
        motoMarcaField = v.findViewById(R.id.clienteNascimentoField);
        motoModeloField = v.findViewById(R.id.clienteCNHField);
        motoAnoField = v.findViewById(R.id.motoAnoField);
        motoTipoMotorField = v.findViewById(R.id.motoTipoMotorField);
        motoCapTanqueField = v.findViewById(R.id.motoCapTanqueField);
        motoMediaConsumoField = v.findViewById(R.id.motoMediaConsumoField);
        motoValorCustoField = v.findViewById(R.id.motoValorCustoField);
        motoAcessorio01 = v.findViewById(R.id.motoAcessorio01);
        motoAcessorio02 = v.findViewById(R.id.motoAcessorio02);
        motoAcessorio03 = v.findViewById(R.id.motoAcessorio03);

        createCategorias(v);
        createTipoMotor(v);
    }

    public void setData(Moto moto){

        for(int i = 0; i < motoCategoriaField.getAdapter().getCount(); i++){
            if(((CatMoto) motoCategoriaField.getAdapter().getItem(i)).CD_CATEGORIA == moto.CATMOTO.CD_CATEGORIA){
                motoCategoriaField.setSelection(i, true);
            }
        }

        for(int i = 0; i < motoTipoMotorField.getAdapter().getCount(); i++){
            if(((Motor) motoTipoMotorField.getAdapter().getItem(i)).CD_MOTOR == moto.TP_MOTOR.CD_MOTOR){
                motoTipoMotorField.setSelection(i, true);
            }
        }

        for(Acessorio acessorio: moto.Acessorios){
            Log.d("ACESSORIOS", acessorio.toString());
            switch (acessorio.CD_ACESSORIO){
                case 1:
                    motoAcessorio01.setChecked(true);
                    break;
                case 2:
                    motoAcessorio02.setChecked(true);
                    break;
                case 3:
                    motoAcessorio03.setChecked(true);
                    break;
            }
        }


        motoMarcaField.setText(moto.DS_MARCA);
        motoModeloField.setText(moto.DS_MODELO);
        motoAnoField.setText(String.format("%d",moto.NR_ANO));
        motoCapTanqueField.setText(String.format("%.2f",moto.CP_TANQUE));
        motoMediaConsumoField.setText(String.format("%.2f",moto.AV_CONSUMO));
        motoValorCustoField.setText(String.format("%.2f",moto.VL_CUSTO));
    }

    private void createCategorias(View v){

        ArrayList<CatMoto> toCategorias = new ArrayList<CatMoto>();
        toCategorias.add(new CatMoto(0,"Selecione"));

        for (CatMoto catMoto: getCatMoto(v)) {
            toCategorias.add(catMoto);
        }

        ArrayAdapter<CatMoto> adapter = new ArrayAdapter<CatMoto>(v.getContext(),android.R.layout.simple_spinner_item,toCategorias);
        motoCategoriaField.setAdapter(adapter);
    }

    private void createTipoMotor(View v){
        List<Motor> toTipoMotor = new ArrayList<Motor>();
        toTipoMotor.add(new Motor(0, "Selecione"));

        for (Motor motor: getMotor(v)) {
            toTipoMotor.add(motor);
        }

        ArrayAdapter<Motor> adapter = new ArrayAdapter<Motor>(v.getContext(),android.R.layout.simple_spinner_item,toTipoMotor);
        motoTipoMotorField.setAdapter(adapter);
    }

    private ArrayList<CatMoto> getCatMoto(View v){
        MotoService motoService = new MotoService(v.getContext());
        return motoService.listarCatMotos();
    }

    private ArrayList<Motor> getMotor(View v){
        MotoService motoService = new MotoService(v.getContext());
        return motoService.listarMotor();
    }

    private ArrayList<Acessorio> getDataAcessorio(){
        ArrayList<Acessorio> acessorios = new ArrayList<Acessorio>();

        if(motoAcessorio01.isChecked()){
            acessorios.add(new Acessorio(1,motoAcessorio01.getText().toString()));
        }

        if(motoAcessorio02.isChecked()){
            acessorios.add(new Acessorio(2,motoAcessorio01.getText().toString()));
        }

        if(motoAcessorio03.isChecked()){
            acessorios.add(new Acessorio(3,motoAcessorio01.getText().toString()));
        }
        Log.d("acessorioSize",String.format("Acessorio Size:%d", acessorios.size()));
        return acessorios;
    }

    public Moto getDataMoto(int motoID){
        Moto moto = new Moto(motoID,
                (CatMoto) motoCategoriaField.getSelectedItem(),
                motoMarcaField.getText().toString(),
                motoModeloField.getText().toString(),
                Integer.parseInt(motoAnoField.getText().toString()),
                (Motor) motoTipoMotorField.getSelectedItem(),
                Float.parseFloat(motoCapTanqueField.getText().toString()),
                Float.parseFloat(motoMediaConsumoField.getText().toString()),
                Float.parseFloat(motoValorCustoField.getText().toString()),
                getDataAcessorio()
        );
        return moto;
    }


}
