package com.example.locadoraempinamoto.ui.cliente;

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

import java.util.ArrayList;
import java.util.List;

public class ClienteItemController {

    private EditText clienteNomeField;
    private EditText clienteNascimentoField;
    private EditText clienteCNHField;

    public ClienteItemController(View v){
        clienteNomeField = v.findViewById(R.id.clienteNomeField);
        clienteNascimentoField = v.findViewById(R.id.clienteNascimentoField);
        clienteCNHField = v.findViewById(R.id.clienteCNHField);

    }

    public void setData(Cliente cliente){

        clienteNomeField.setText(cliente.NM_CLIENTE);
        clienteNascimentoField.setText(cliente.DT_NASCIMENTO);
        clienteCNHField.setText(cliente.NR_CNH);

    }

    public Cliente getDataCliente(int clienteID){
        Cliente cliente = new Cliente(clienteID,
                clienteNomeField.getText().toString(),
                clienteCNHField.getText().toString(),
                clienteNascimentoField.getText().toString()
        );

        return cliente;
    }


}
