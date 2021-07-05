package com.example.locadoraempinamoto.ui.cliente;

import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.locadoraempinamoto.model.Cliente.Cliente;
import com.example.locadoraempinamoto.model.Moto.Moto;
import com.example.locadoraempinamoto.services.MotoService;
import com.example.locadoraempinamoto.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ClienteItemController {

    private EditText clienteNomeField;
    private EditText clienteNascimentoField;
    private EditText clienteCNHField;

    boolean isUpdating;
    String old = "";

    public ClienteItemController(View v){
        clienteNomeField = v.findViewById(R.id.clienteNomeField);
        clienteNascimentoField = v.findViewById(R.id.clienteNascimentoField);
        clienteNascimentoField.addTextChangedListener(new TextWatcher() {
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
                clienteNascimentoField.setText(mascara);
                clienteNascimentoField.setSelection(mascara.length());
            }
        });
        clienteCNHField = v.findViewById(R.id.clienteCNHField);

    }

    public void setData(Cliente cliente){

        clienteNomeField.setText(cliente.NM_CLIENTE);
        clienteNascimentoField.setText(Util.dataFormatSQLReverse(cliente.DT_NASCIMENTO));
        clienteCNHField.setText(cliente.NR_CNH);

    }

    public Cliente getDataCliente(int clienteID){
        Cliente cliente = new Cliente(clienteID,
                clienteNomeField.getText().toString(),
                clienteCNHField.getText().toString(),
                Util.dataFormatSQL(clienteNascimentoField.getText().toString())
        );
        return cliente;
    }


}
