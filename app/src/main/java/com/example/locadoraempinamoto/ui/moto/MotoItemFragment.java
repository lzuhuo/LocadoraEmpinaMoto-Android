package com.example.locadoraempinamoto.ui.moto;

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
import com.example.locadoraempinamoto.databinding.FragmentMotoItemBinding;
import com.example.locadoraempinamoto.model.Message;
import com.example.locadoraempinamoto.model.Moto.Moto;
import com.example.locadoraempinamoto.services.MotoService;

public class MotoItemFragment extends Fragment implements View.OnClickListener {

    private FragmentMotoItemBinding binding;
    private AlertDialog saveAlert;
    private int motoID = 0;
    private MotoItemController mic;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMotoItemBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        root.findViewById(R.id.clienteSave).setOnClickListener(this);
        root.findViewById(R.id.clienteCancel).setOnClickListener(this::backFragment);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mic = new MotoItemController(view);
        Log.d("motoID", String.format("%d", motoID));
        if(motoID > 0){
            Toast.makeText(getContext(), "Abrindo registro n: " + motoID,Toast.LENGTH_SHORT).show();
            mic.setData(getMoto(motoID));
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
        this.motoID = id;
    }

    @Override
    public void onClick(View v) {
        Message message;
        Moto moto = mic.getDataMoto(this.motoID);
        MotoService motoService = new MotoService(v.getContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Motocicleta");

        if(motoID > 0){
            message = motoService.atualizaMoto(moto);
            if(message.status){
                builder.setMessage("Registro atualizado com sucesso!");
            }else{
                builder.setMessage(message.toString());
            }

        }else{
            message = motoService.adicionarMoto(moto);
            if(message.status){
                builder.setMessage("Registro adicionado com sucesso!");
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
        fr.replace(R.id.nav_host_fragment_content_main, new MotoFragment());
        fr.commit();
    }

    private Moto getMoto(int id){
        MotoService motoService = new MotoService(getContext());
        return motoService.getMoto(id);
    }


}