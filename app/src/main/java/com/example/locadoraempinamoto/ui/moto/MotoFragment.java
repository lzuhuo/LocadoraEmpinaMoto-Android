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
import com.example.locadoraempinamoto.databinding.FragmentMotoBinding;

public class MotoFragment extends Fragment implements View.OnClickListener {

    private MotoViewModel homeViewModel;
    private FragmentMotoBinding binding;
    private RecyclerView recyclerView;
    /*private MotoAdapter adapter;*/

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(MotoViewModel.class);

        binding = FragmentMotoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        root.findViewById(R.id.addMoto).setOnClickListener(this);

        return root;
    }

    /*@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.moto_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MotoAdapter(this);
        recyclerView.setAdapter(adapter);
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fr = getFragmentManager().beginTransaction();
        fr.replace(R.id.nav_host_fragment_content_main, new MotoItemFragment());
        fr.commit();
        /*Toast.makeText(getActivity(),"Abrindo novo cadastro",Toast.LENGTH_SHORT).show();*/
    }

    /*public static class MotoAdapter extends RecyclerView.Adapter<PasswordsViewHolder>{
    }*/
}