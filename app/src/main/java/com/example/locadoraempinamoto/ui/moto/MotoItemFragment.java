package com.example.locadoraempinamoto.ui.moto;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.locadoraempinamoto.R;
import com.example.locadoraempinamoto.databinding.FragmentMotoBinding;
import com.example.locadoraempinamoto.databinding.FragmentMotoItemBinding;

public class MotoItemFragment extends Fragment implements View.OnClickListener {

    private MotoViewModel homeViewModel;
    private FragmentMotoItemBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(MotoViewModel.class);
        binding = FragmentMotoItemBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {


        Log.e("Fragmento","qual é o erro");
        /*Toast.makeText(getActivity(),"Abrindo novo cadastro",Toast.LENGTH_SHORT).show();*/
    }

}