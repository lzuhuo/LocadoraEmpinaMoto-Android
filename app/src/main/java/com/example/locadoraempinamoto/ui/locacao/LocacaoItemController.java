package com.example.locadoraempinamoto.ui.locacao;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.locadoraempinamoto.R;
import com.example.locadoraempinamoto.model.AnimalNames;
import com.example.locadoraempinamoto.model.Categoria.Acessorio;
import com.example.locadoraempinamoto.model.Categoria.CatMoto;
import com.example.locadoraempinamoto.model.Categoria.Motor;
import com.example.locadoraempinamoto.model.Cliente.Cliente;
import com.example.locadoraempinamoto.model.Moto.Locacao;
import com.example.locadoraempinamoto.model.Moto.Moto;
import com.example.locadoraempinamoto.model.Moto.Opcional;
import com.example.locadoraempinamoto.model.Moto.Status;
import com.example.locadoraempinamoto.services.ClienteService;
import com.example.locadoraempinamoto.services.LocacaoService;
import com.example.locadoraempinamoto.services.MotoService;
import com.example.locadoraempinamoto.util.Util;

import java.util.ArrayList;
import java.util.List;

public class LocacaoItemController {

    private SearchView searchViewCNH;
    private EditText clienteNomeField;
    private EditText clienteNascimentoField;
    private EditText clienteCNHField;

    private EditText locacaoLocalRetiradaField;
    private EditText locacaoLocalDevolucaoField;
    private EditText locacaoDataRetiradaField;
    private EditText locacaoDataDevolucaoField;

    private Spinner motoMarcaField;
    private Spinner motoModeloField;
    private CheckBox motoOpcional01;
    private CheckBox motoOpcional02;
    private CheckBox motoOpcional03;

    private EditText custoMotoField;
    private EditText custoDiariasField;
    private EditText custoOpcionalField;
    private EditText custoTotalField;

    private Spinner locacaoStatusField;

    private View view;
    boolean isUpdating;
    String old = "";

    private Cliente clienteSearch;
    private ArrayList<Opcional> lstOpcionais;

    public LocacaoItemController(View v){
        view = v;

        //Informações do Cliente
        searchViewCNH = v.findViewById(R.id.searchViewCNH);
        searchViewCNH.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setCliente(newText);
                return false;
            }
        });


        clienteNomeField = v.findViewById(R.id.clienteNomeField);
        clienteNascimentoField = v.findViewById(R.id.clienteNascimentoField);
        clienteCNHField = v.findViewById(R.id.clienteCNHField);

        //Informações da Locacao
        locacaoLocalRetiradaField = v.findViewById(R.id.locacaoLocalRetiradaField);
        locacaoLocalDevolucaoField = v.findViewById(R.id.locacaoLocalDevolucaoField);
        locacaoDataRetiradaField = v.findViewById(R.id.locacaoDataRetiradaField);
        locacaoDataDevolucaoField = v.findViewById(R.id.locacaoDataDevolucaoField);
        locacaoDataRetiradaField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String mascara = "";
                if (isUpdating) {
                    old = s.toString();
                    isUpdating = false;
                    return;
                }
                isUpdating = true;
                mascara = Util.dataFormat(s.toString());
                locacaoDataRetiradaField.setText(mascara);
                locacaoDataDevolucaoField.setText("");
                locacaoDataRetiradaField.setSelection(mascara.length());
            }
        });
        locacaoDataDevolucaoField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String mascara = "";
                if (isUpdating) {
                    old = s.toString();
                    isUpdating = false;
                    return;
                }
                isUpdating = true;
                mascara = Util.dataFormat(s.toString());
                locacaoDataDevolucaoField.setText(mascara);
                locacaoDataDevolucaoField.setSelection(mascara.length());
                if(mascara.length() == 10){
                    CreateMarca();
                    calcDays(locacaoDataRetiradaField.getText().toString(),
                            locacaoDataDevolucaoField.getText().toString());
                }
            }
        });

        //Informações da Moto
        motoMarcaField = v.findViewById(R.id.motoMarcaField);
        motoMarcaField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    CreateModelos(parent.getAdapter().getItem(position).toString());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        motoModeloField = v.findViewById(R.id.motoModeloField);
        motoModeloField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    Moto motoSelected = (Moto) parent.getAdapter().getItem(position);
                    custoMotoField.setText(String.format("%.2f",motoSelected.VL_CUSTO));
                    sumTotal();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        motoOpcional01 = v.findViewById(R.id.motoOpcional01);
        motoOpcional02 = v.findViewById(R.id.motoOpcional02);
        motoOpcional03 = v.findViewById(R.id.motoOpcional03);

        motoOpcional01.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                calcOpcionais();
            }
        });

        motoOpcional02.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                calcOpcionais();
            }
        });

        motoOpcional03.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                calcOpcionais();
            }
        });

        lstOpcionais = listaOpcionais();

        //Custos Gerais
        custoMotoField = v.findViewById(R.id.custoMotoField);
        custoMotoField.setText("0.00");
        custoDiariasField = v.findViewById(R.id.custoDiariasField);
        custoDiariasField.setText("0");
        custoOpcionalField = v.findViewById(R.id.custoOpcionalField);
        custoOpcionalField.setText("0.00");
        custoTotalField = v.findViewById(R.id.custoTotalField);
        custoTotalField.setText("0.00");

        //Status Locacao
        locacaoStatusField = v.findViewById(R.id.locacaoStatusField);
        CreateStatus();
    }

    public void setData(Locacao locacao){
        TextView pesquisaLabel = view.findViewById(R.id.pesquisaCliente);
        pesquisaLabel.setText("");
        //Informações Cliente
        clienteNomeField.setText(locacao.cliente.NM_CLIENTE);
        clienteNascimentoField.setText(String.format("%d",Util.calIdade(Util.dataFormatSQLReverse(locacao.cliente.DT_NASCIMENTO))));
        clienteCNHField.setText(locacao.cliente.NR_CNH);
        searchViewCNH.setVisibility(view.INVISIBLE);

        //Informações Locação
        locacaoLocalRetiradaField.setEnabled(false);
        locacaoLocalDevolucaoField.setEnabled(false);
        locacaoDataRetiradaField.setEnabled(false);
        locacaoDataDevolucaoField.setEnabled(false);

        locacaoLocalRetiradaField.setText(locacao.LC_RETIRADA);
        locacaoLocalDevolucaoField.setText(locacao.LC_DEVOLUCAO);
        locacaoDataRetiradaField.setText(Util.dataFormatSQLReverse(locacao.DT_RETIRADA));
        locacaoDataDevolucaoField.setText(Util.dataFormatSQLReverse(locacao.DT_DEVOLUCAO));

        //Informações Moto
        motoMarcaField.setEnabled(false);
        motoModeloField.setEnabled(false);

        List<String> toMarca = new ArrayList<String>();
        toMarca.add(locacao.moto.DS_MARCA);
        ArrayAdapter<String> adapterMarca = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item,toMarca);
        motoMarcaField.setAdapter(adapterMarca);

        List<Moto> toModelo = new ArrayList<Moto>();
        toModelo.add(locacao.moto);
        ArrayAdapter<Moto> adapterModelo = new ArrayAdapter<Moto>(view.getContext(),android.R.layout.simple_spinner_item,toModelo);
        motoModeloField.setAdapter(adapterModelo);

        float sumOp = 0;
        for(Opcional opcional: locacao.opcional){

            if(opcional.CD_OPCIONAL == 1){
                motoOpcional01.setChecked(true);
                sumOp += opcional.VL_OPCIONAL;
            }
            if(opcional.CD_OPCIONAL == 2){
                motoOpcional02.setChecked(true);
                sumOp += opcional.VL_OPCIONAL;
            }
            if(opcional.CD_OPCIONAL == 3){
                motoOpcional03.setChecked(true);
                sumOp += opcional.VL_OPCIONAL;
            }
        }

        //Custos Gerais
        custoMotoField.setText(String.format("%.2f",locacao.moto.VL_CUSTO));
        calcDays(locacao.DT_RETIRADA, locacao.DT_DEVOLUCAO);
        custoOpcionalField.setText(String.format("%.2f",sumOp));
        custoTotalField.setText(String.format("%.2f",locacao.VL_TOTAL));

        //Status
        for(int i = 0; i < locacaoStatusField.getAdapter().getCount(); i++){
            if(((Status) locacaoStatusField.getAdapter().getItem(i)).ST_LOCACAO.equals(locacao.status.ST_LOCACAO)){
                locacaoStatusField.setSelection(i, true);
            }
        }

        if(!locacao.status.ST_LOCACAO.equals("A")){
            motoOpcional01.setEnabled(false);
            motoOpcional02.setEnabled(false);
            motoOpcional03.setEnabled(false);
        }
    }

    public Locacao getDataLocacao(int locacaoID){
        ArrayList<Opcional> opcionais = new ArrayList<Opcional>();
        if(motoOpcional01.isChecked()){
            opcionais.add(lstOpcionais.get(0));
        }
        if(motoOpcional02.isChecked()){
            opcionais.add(lstOpcionais.get(1));
        }
        if(motoOpcional03.isChecked()){
            opcionais.add(lstOpcionais.get(2));
        }
        Log.d("LocacaoSave",String.format("%d",opcionais.size()));
        Locacao locacao = new Locacao(locacaoID,
                (Status) locacaoStatusField.getSelectedItem(),
                Float.parseFloat(custoTotalField.getText().toString()),
                opcionais
                );
        if(!(locacaoID > 0)){
            locacao.moto = new Moto((Moto) motoModeloField.getSelectedItem());
            locacao.cliente = clienteSearch;
            locacao.DT_RETIRADA = Util.dataFormatSQL(locacaoDataRetiradaField.getText().toString());
            locacao.DT_DEVOLUCAO = Util.dataFormatSQL(locacaoDataDevolucaoField.getText().toString());
            locacao.LC_RETIRADA = locacaoLocalRetiradaField.getText().toString();
            locacao.LC_DEVOLUCAO = locacaoLocalDevolucaoField.getText().toString();
        }

        return locacao;
    }

    private void setCliente(String cnh){
        ClienteService clienteService = new ClienteService(view.getContext());
        ArrayList<Cliente> clientes = clienteService.getClientes(cnh);
        if(clientes.size() == 1){
            clienteNomeField.setText(clientes.get(0).NM_CLIENTE);
            clienteNascimentoField.setText(String.format("%d",Util.calIdade(Util.dataFormatSQLReverse(clientes.get(0).DT_NASCIMENTO))));
            clienteCNHField.setText(clientes.get(0).NR_CNH);
            clienteSearch = clientes.get(0);
        }else{
            clienteNomeField.setText("");
            clienteNascimentoField.setText("");
            clienteCNHField.setText("");
        }
    }

    private void CreateMarca(){
        List<String> toMarca = new ArrayList<String>();
        toMarca.add("Selecione");

        for (String marca: getMarcas()) {
            toMarca.add(marca);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item,toMarca);
        motoMarcaField.setAdapter(adapter);
    }

    private void CreateModelos(String marca){
        ArrayList<Moto> toMoto = new ArrayList<Moto>();
        toMoto.add(new Moto(0,"Selecione"));

        for (Moto moto: getModelMoto(marca)) {
            toMoto.add(moto);
        }

        ArrayAdapter<Moto> adapter = new ArrayAdapter<Moto>(view.getContext(),android.R.layout.simple_spinner_item,toMoto);
        motoModeloField.setAdapter(adapter);
    }

    private void CreateStatus(){
        ArrayList<Status> toStatus = new ArrayList<Status>();

        for (Status status: getLocStatus()) {
            toStatus.add(status);
        }
        ArrayAdapter<Status> adapter = new ArrayAdapter<Status>(view.getContext(),android.R.layout.simple_spinner_item,toStatus);
        locacaoStatusField.setAdapter(adapter);

    }

    private ArrayList<String> getMarcas(){
        LocacaoService locacaoService = new LocacaoService(view.getContext());
        String DT_INICIO = locacaoDataRetiradaField.getText().toString();
        String DT_FIM = locacaoDataDevolucaoField.getText().toString();
        ArrayList<String> marcas = new ArrayList<String>();

        marcas = locacaoService.getDSMoto(DT_INICIO, DT_FIM);
        return marcas;
    }

    private ArrayList<Moto> getModelMoto(String marca){
        LocacaoService locacaoService = new LocacaoService(view.getContext());
        String DT_INICIO = locacaoDataRetiradaField.getText().toString();
        String DT_FIM = locacaoDataDevolucaoField.getText().toString();
        return locacaoService.getModelMoto(DT_INICIO, DT_FIM, marca);
    }

    private ArrayList<Status> getLocStatus(){
        LocacaoService locacaoService = new LocacaoService(view.getContext());
        return locacaoService.getLocStatus();
    }

    private void calcDays(String d1, String d2){
        if(d1.contains("/")){
            custoDiariasField.setText(String.format("%d",Util.diffDates(d1,d2)));
        }else{
            custoDiariasField.setText(String.format("%d",Util.diffDates(
                    Util.dataFormatSQLReverse(d1),
                    Util.dataFormatSQLReverse(d2))));
        }
        sumTotal();
    }

    private void calcOpcionais(){
        float sumOp = 0;
        for(Opcional opcional: lstOpcionais){

            if(motoOpcional01.isChecked()){
                sumOp += opcional.VL_OPCIONAL;
            }
            if(motoOpcional02.isChecked()){
                sumOp += opcional.VL_OPCIONAL;
            }
            if(motoOpcional03.isChecked()){
                sumOp += opcional.VL_OPCIONAL;
            }
        }
        custoOpcionalField.setText(String.format("%.2f",sumOp));
        sumTotal();
    }

    private ArrayList<Opcional> listaOpcionais(){
        LocacaoService locacaoService = new LocacaoService(view.getContext());
        return locacaoService.listaOpcionais();
    }

    private void sumTotal(){
        float sum = 0;
        float vlm = Float.parseFloat(custoMotoField.getText().toString());
        float diar = Float.parseFloat(custoDiariasField.getText().toString());
        float vlo = Float.parseFloat(custoOpcionalField.getText().toString());

        sum = (vlm * diar) + vlo;
        custoTotalField.setText(String.format("%.2f",sum));
    }


}
