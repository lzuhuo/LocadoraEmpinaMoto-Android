package com.example.locadoraempinamoto.ui.cliente;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locadoraempinamoto.R;
import com.example.locadoraempinamoto.databinding.FragmentClienteBinding;
import com.example.locadoraempinamoto.databinding.FragmentMotoBinding;
import com.example.locadoraempinamoto.model.Cliente.Cliente;
import com.example.locadoraempinamoto.model.Moto.Moto;
import com.example.locadoraempinamoto.services.ClienteService;
import com.example.locadoraempinamoto.services.MotoService;

import java.util.ArrayList;

public class ClienteFragment extends Fragment implements View.OnClickListener {

    private FragmentClienteBinding binding;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Cliente> listclientes;
    private View listItemsView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentClienteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        FragmentTransaction fr = getFragmentManager().beginTransaction();


        root = inflater.inflate(R.layout.fragment_cliente, container, false);
        recyclerView = root.findViewById(R.id.listVClientes);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        listclientes = listaClientes();

        adapter = new ClienteAdapter(listclientes, fr);
        recyclerView.setAdapter(adapter);

        root.findViewById(R.id.addCliente).setOnClickListener(this);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fr = getFragmentManager().beginTransaction();
        ClienteItemFragment motoItem = new ClienteItemFragment();
        fr.replace(R.id.nav_host_fragment_content_main, motoItem);
        fr.commit();
        /*Toast.makeText(getActivity(),"Abrindo novo cadastro",Toast.LENGTH_SHORT).show();*/
    }

    public ArrayList<Cliente> listaClientes(){
        ClienteService clienteService = new ClienteService(getContext());
        return clienteService.listarClientes();
    }
}