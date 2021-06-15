package com.example.locadoraempinamoto.ui.moto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.locadoraempinamoto.R;
import com.example.locadoraempinamoto.databinding.FragmentMotoItemBinding;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MotoItemFragment extends Fragment implements View.OnClickListener {

    private MotoViewModel homeViewModel;
    private FragmentMotoItemBinding binding;
    private AlertDialog saveAlert;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(MotoViewModel.class);
        binding = FragmentMotoItemBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        root.findViewById(R.id.motoSave).setOnClickListener(this);
        root.findViewById(R.id.motoCancel).setOnClickListener(this::backFragment);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createCategorias();
        createTipoMotor();
        createAcessorios();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void createCategorias(){
        Spinner spin = (Spinner) getView().findViewById(R.id.motoCategoriaField);
        List<String> toCategorias = new ArrayList<String>();
        toCategorias.add("Selecione");
        toCategorias.add("Econômico");
        toCategorias.add("Standard");
        toCategorias.add("Premium");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,toCategorias);
        spin.setAdapter(adapter);
    }

    private void createTipoMotor(){
        Spinner spin = (Spinner) getView().findViewById(R.id.motoTipoMotorField);
        List<String> toTipoMotor = new ArrayList<String>();
        toTipoMotor.add("Selecione");
        toTipoMotor.add("Monocilíndrico");
        toTipoMotor.add("Bicilíndrico");
        toTipoMotor.add("Tricilíndrico");
        toTipoMotor.add("Tetracilíndrico");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,toTipoMotor);
        spin.setAdapter(adapter);
    }

    private void createAcessorios(){
        Spinner spin = (Spinner) getView().findViewById(R.id.motoAcessorioField);
        List<String> toAcessorios = new ArrayList<String>();
        toAcessorios.add("Selecione");
        toAcessorios.add("Porta de Energia DC");
        toAcessorios.add("Rádio/CD Player");
        toAcessorios.add("Wind Shield");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,toAcessorios);
        spin.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Motocicleta");
        builder.setMessage("Moto cadastrada com sucesso!");
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
        fr.replace(R.id.nav_host_fragment_content_main, new MotoFragment());
        fr.commit();
    }

}