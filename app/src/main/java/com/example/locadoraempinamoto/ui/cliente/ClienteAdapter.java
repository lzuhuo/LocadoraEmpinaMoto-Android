package com.example.locadoraempinamoto.ui.cliente;

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
import com.example.locadoraempinamoto.services.ClienteService;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ListViewHolder> {
    ArrayList<Cliente> clienteData;

    private LayoutInflater layoutInflater;
    private Context context;
    private FragmentTransaction fr;

    ClienteAdapter(ArrayList<Cliente> data, FragmentTransaction fragmentTransaction){
        fr = fragmentTransaction;
        clienteData = data;
    }

    @NonNull
    @NotNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        View view  = layoutInflater.inflate(R.layout.list_cliente_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ListViewHolder holder, int position) {
        holder.itemCliente.setText(clienteData.get(position).NM_CLIENTE);
        holder.itemCNH.setText(clienteData.get(position).NR_CNH);
        holder.itemNascimento.setText(clienteData.get(position).DT_NASCIMENTO);

        holder.itemEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCliente(clienteData.get(position).CD_CLIENTE);
            }
        });

        holder.itemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(deleteCliente(clienteData.get(position).CD_CLIENTE, v)){
                    Toast.makeText(v.getContext(), "Registro deletado com sucesso",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(v.getContext(), "Falha ao remover registro",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return clienteData.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView itemCliente;
        TextView itemNascimento;
        TextView itemCNH;
        ImageView itemEdit;
        ImageView itemDelete;

        public ListViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            itemCliente = itemView.findViewById(R.id.itemCliente);
            itemNascimento = itemView.findViewById(R.id.itemNascimento);
            itemCNH = itemView.findViewById(R.id.itemCNH);
            itemEdit = itemView.findViewById(R.id.itemEdit);
            itemDelete = itemView.findViewById(R.id.itemDelete);
        }
    }

    private void editCliente(int id){
        ClienteItemFragment clienteItem = new ClienteItemFragment();
        clienteItem.setMotoID(id);
        fr.replace(R.id.nav_host_fragment_content_main, clienteItem);
        fr.commit();
    }

    private Boolean deleteCliente(int clienteID, View v){
        ClienteService motoService = new ClienteService(v.getContext());
        ClienteFragment clienteItem = new ClienteFragment();
        fr.replace(R.id.nav_host_fragment_content_main, clienteItem);
        fr.commit();
        return motoService.removeCliente(clienteID);
    }
}
