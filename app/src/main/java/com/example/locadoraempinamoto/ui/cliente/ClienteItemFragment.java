package com.example.locadoraempinamoto.ui.cliente;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.locadoraempinamoto.R;
import com.example.locadoraempinamoto.databinding.FragmentClienteItemBinding;
import com.example.locadoraempinamoto.model.Cliente.Cliente;
import com.example.locadoraempinamoto.model.Message;
import com.example.locadoraempinamoto.services.ClienteService;

public class ClienteItemFragment extends Fragment implements View.OnClickListener {

    private FragmentClienteItemBinding binding;
    private AlertDialog saveAlert;
    private int clienteID = 0;
    private ClienteItemController mic;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentClienteItemBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        root.findViewById(R.id.clienteSave).setOnClickListener(this);
        root.findViewById(R.id.clienteCancel).setOnClickListener(this::backFragment);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mic = new ClienteItemController(view);
        Log.d("motoID", String.format("%d", clienteID));
        if(clienteID > 0){
            Toast.makeText(getContext(), "Abrindo registro n: " + clienteID,Toast.LENGTH_SHORT).show();
            mic.setData(getCliente(clienteID));
        }else{
            Toast.makeText(getContext(), "Novo cadastro",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setMotoID(int id){
        this.clienteID = id;
    }

    @Override
    public void onClick(View v) {
        Message message;
        Cliente cliente = mic.getDataCliente(this.clienteID);
        ClienteService clienteService = new ClienteService(v.getContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Cliente");

        if(clienteID > 0){
            message = clienteService.atualizarCliente(cliente);
            if(message.status){
                builder.setMessage("Registro atualizado com sucesso!");
            }else{
                builder.setMessage(message.toString());
            }

        }else{
            message = clienteService.inserirCliente(cliente);
            if(message.status){
                builder.setMessage("Registro criado com sucesso!");
            }else{
                builder.setMessage(message.toString());
            }

        }

        builder.setNeutralButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                backFragment(getView());
            }
        });
        saveAlert = builder.create();
        saveAlert.show();
    }

    public void backFragment(View view){
        FragmentTransaction fr = getFragmentManager().beginTransaction();
        fr.replace(R.id.nav_host_fragment_content_main, new ClienteFragment());
        fr.commit();
    }

    private Cliente getCliente(int id){
        ClienteService clienteService = new ClienteService(getContext());
        return clienteService.getCliente(id);
    }


}