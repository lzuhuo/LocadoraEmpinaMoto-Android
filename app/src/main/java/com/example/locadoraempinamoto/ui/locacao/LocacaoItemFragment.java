package com.example.locadoraempinamoto.ui.locacao;

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

import com.example.locadoraempinamoto.databinding.FragmentLocacaoItemBinding;
import com.example.locadoraempinamoto.model.Message;
import com.example.locadoraempinamoto.model.Moto.Locacao;
import com.example.locadoraempinamoto.services.LocacaoService;

public class LocacaoItemFragment extends Fragment implements View.OnClickListener {

    private FragmentLocacaoItemBinding binding;
    private AlertDialog saveAlert;
    private int locacaoID = 0;
    private LocacaoItemController mic;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLocacaoItemBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        root.findViewById(R.id.locacaoSave).setOnClickListener(this);
        root.findViewById(R.id.locacaoCancel).setOnClickListener(this::backFragment);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mic = new LocacaoItemController(view);
        Log.d("locacaoID", String.format("%d", locacaoID));
        if(locacaoID > 0){
            Toast.makeText(getContext(), "Abrindo registro n: " + locacaoID,Toast.LENGTH_SHORT).show();
            mic.setData(getLocacao(this.locacaoID));
        }else{
            Toast.makeText(getContext(), "Novo cadastro",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setLocacaoID(int id){
        this.locacaoID = id;
    }

    @Override
    public void onClick(View v) {
        Message message;
        Locacao locacao = mic.getDataLocacao(this.locacaoID);
        LocacaoService locacaoService = new LocacaoService(v.getContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Locação");

        if(locacaoID > 0){
            message = locacaoService.atualizarLocacao(locacao);
            if(message.status){
                builder.setMessage("Registro atualizado com sucesso!");
            }else{
                builder.setMessage(message.toString());
            }

        }else{
            message = locacaoService.adicionaLocacao(locacao);
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
        fr.replace(R.id.nav_host_fragment_content_main, new LocacaoFragment());
        fr.commit();
    }

    private Locacao getLocacao(int id){
        LocacaoService locacaoService = new LocacaoService(getContext());
        return locacaoService.getLocacao(id);
    }
}