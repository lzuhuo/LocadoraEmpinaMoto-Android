package com.example.locadoraempinamoto.ui.locacao;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.locadoraempinamoto.R;
import com.example.locadoraempinamoto.databinding.FragmentLocacaoBinding;

public class LocacaoFragment extends Fragment {

    private LocacaoViewModel slideshowViewModel;
    private FragmentLocacaoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(LocacaoViewModel.class);

        binding = FragmentLocacaoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}