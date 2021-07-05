package com.example.locadoraempinamoto.ui.locacao;

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
import com.example.locadoraempinamoto.databinding.FragmentLocacaoBinding;
import com.example.locadoraempinamoto.model.Cliente.Cliente;
import com.example.locadoraempinamoto.model.Moto.Locacao;
import com.example.locadoraempinamoto.services.ClienteService;
import com.example.locadoraempinamoto.services.LocacaoService;

import java.util.ArrayList;

public class LocacaoFragment extends Fragment implements View.OnClickListener {

    private FragmentLocacaoBinding binding;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Locacao> listlocacoes;
    private View listItemsView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLocacaoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        FragmentTransaction fr = getFragmentManager().beginTransaction();


        root = inflater.inflate(R.layout.fragment_locacao, container, false);
        recyclerView = root.findViewById(R.id.listVLocacoes);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        listlocacoes = listaLocacoes();

        adapter = new LocacaoAdapter(listlocacoes, fr);
        recyclerView.setAdapter(adapter);

        root.findViewById(R.id.addLocacao).setOnClickListener(this);

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
        LocacaoItemFragment locacaoItem = new LocacaoItemFragment();
        fr.replace(R.id.nav_host_fragment_content_main, locacaoItem);
        fr.commit();
        /*Toast.makeText(getActivity(),"Abrindo novo cadastro",Toast.LENGTH_SHORT).show();*/
    }

    public ArrayList<Locacao> listaLocacoes(){
        LocacaoService locacaoService = new LocacaoService(getContext());
        return locacaoService.listarLocacoes();
    }
}