package com.example.locadoraempinamoto.ui.locacao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.example.locadoraempinamoto.R;
import com.example.locadoraempinamoto.model.Cliente.Cliente;
import com.example.locadoraempinamoto.model.Moto.Locacao;
import com.example.locadoraempinamoto.services.ClienteService;
import com.example.locadoraempinamoto.util.Util;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class LocacaoAdapter extends RecyclerView.Adapter<LocacaoAdapter.ListViewHolder> {
    ArrayList<Locacao> locacaoData;

    private LayoutInflater layoutInflater;
    private Context context;
    private FragmentTransaction fr;

    LocacaoAdapter(ArrayList<Locacao> data, FragmentTransaction fragmentTransaction){
        fr = fragmentTransaction;
        locacaoData = data;
    }

    @NonNull
    @NotNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        View view  = layoutInflater.inflate(R.layout.list_locacao_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ListViewHolder holder, int position) {
        holder.itemCliente.setText(locacaoData.get(position).cliente.NM_CLIENTE);
        holder.itemCNH.setText(locacaoData.get(position).cliente.NR_CNH);
        holder.itemNascimento.setText(Util.dataFormatSQLReverse(locacaoData.get(position).cliente.DT_NASCIMENTO));
        holder.itemValor.setText(String.format("R$ %.2f",locacaoData.get(position).VL_TOTAL));
        holder.itemMarca.setText(locacaoData.get(position).moto.DS_MARCA);
        holder.itemModelo.setText(locacaoData.get(position).moto.DS_MODELO);
        holder.itemEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLocacao(locacaoData.get(position).CD_LOCACAO);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locacaoData.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView itemCliente;
        TextView itemNascimento;
        TextView itemCNH;
        TextView itemValor;
        TextView itemMarca;
        TextView itemModelo;
        ImageView itemEdit;
        ImageView itemDelete;

        public ListViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            itemCliente = itemView.findViewById(R.id.itemCliente);
            itemNascimento = itemView.findViewById(R.id.itemNascimento);
            itemCNH = itemView.findViewById(R.id.itemCNH);
            itemValor = itemView.findViewById(R.id.itemValor);
            itemMarca = itemView.findViewById(R.id.itemMarca);
            itemModelo = itemView.findViewById(R.id.itemModelo);
            itemEdit = itemView.findViewById(R.id.itemEdit);
            itemDelete = itemView.findViewById(R.id.itemDelete);
        }
    }

    private void editLocacao(int id){
        LocacaoItemFragment locacaoItem = new LocacaoItemFragment();
        locacaoItem.setLocacaoID(id);
        fr.replace(R.id.nav_host_fragment_content_main, locacaoItem);
        fr.commit();
    }

}
