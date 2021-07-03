package com.example.locadoraempinamoto.ui.moto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.example.locadoraempinamoto.R;
import com.example.locadoraempinamoto.model.Moto.Moto;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    ArrayList<Moto> motoData;

    private LayoutInflater layoutInflater;
    private Context context;
    private FragmentTransaction fr;

    ListAdapter(ArrayList<Moto> data, FragmentTransaction fragmentTransaction){
        fr = fragmentTransaction;
        motoData = data;
    }

    @NonNull
    @NotNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        View view  = layoutInflater.inflate(R.layout.list_moto_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ListViewHolder holder, int position) {
        holder.itemModelo.setText(motoData.get(position).DS_MODELO);
        holder.itemMarca.setText(motoData.get(position).DS_MARCA);
        holder.itemAno.setText(String.format("%d",motoData.get(position).NR_ANO));
        holder.itemValor.setText(String.format("R$ %.2f",motoData.get(position).VL_CUSTO));
        holder.itemEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMoto(motoData.get(position).CD_MOTO);
            }
        });
    }

    @Override
    public int getItemCount() {
        return motoData.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView itemModelo;
        TextView itemMarca;
        TextView itemAno;
        TextView itemValor;
        ImageView itemEdit;
        ImageView itemDelete;

        public ListViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            itemModelo = itemView.findViewById(R.id.itemModelo);
            itemMarca = itemView.findViewById(R.id.itemMarca);
            itemAno = itemView.findViewById(R.id.itemAno);
            itemValor = itemView.findViewById(R.id.itemValor);
            itemEdit = itemView.findViewById(R.id.itemEdit);
            itemDelete = itemView.findViewById(R.id.itemDelete);
        }
    }

    private void editMoto(int id){
        MotoItemFragment motoItem = new MotoItemFragment();
        motoItem.setMotoID(id);
        fr.replace(R.id.nav_host_fragment_content_main, motoItem);
        fr.commit();
    }
}
