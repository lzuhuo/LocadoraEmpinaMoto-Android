package com.example.locadoraempinamoto.ui.moto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locadoraempinamoto.R;
import com.example.locadoraempinamoto.business.MotoDAOAccess;
import com.example.locadoraempinamoto.databinding.FragmentMotoBinding;
import com.example.locadoraempinamoto.model.Moto.Moto;
import com.example.locadoraempinamoto.services.MotoService;
import com.example.locadoraempinamoto.services.MotoServiceAccess;

import java.util.ArrayList;

public class MotoFragment extends Fragment implements View.OnClickListener {

    private MotoViewModel homeViewModel;
    private FragmentMotoBinding binding;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Moto> listmotos;
    private View listItemsView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(MotoViewModel.class);

        binding = FragmentMotoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        FragmentTransaction fr = getFragmentManager().beginTransaction();


        root = inflater.inflate(R.layout.fragment_moto, container, false);
        recyclerView = root.findViewById(R.id.listVMotos);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        listmotos = listaMotos();

        adapter = new ListAdapter(listmotos, fr);
        recyclerView.setAdapter(adapter);

        root.findViewById(R.id.addMoto).setOnClickListener(this);


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
        MotoItemFragment motoItem = new MotoItemFragment();
        fr.replace(R.id.nav_host_fragment_content_main, motoItem);
        fr.commit();
        /*Toast.makeText(getActivity(),"Abrindo novo cadastro",Toast.LENGTH_SHORT).show();*/
    }

    public ArrayList<Moto> listaMotos(){
        MotoServiceAccess motoService = new MotoServiceAccess(getContext());
        return motoService.listarMotos();
    }
}