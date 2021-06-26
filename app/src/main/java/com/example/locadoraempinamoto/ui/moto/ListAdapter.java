package com.example.locadoraempinamoto.ui.moto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locadoraempinamoto.R;

import org.jetbrains.annotations.NotNull;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    String[] langData = {};

    private LayoutInflater layoutInflater;

    ListAdapter(String[] data){
        langData = data;
    }

    @NonNull
    @NotNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view  = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ListViewHolder holder, int position) {
        String title = langData[position];
        holder.titleItem.setText(title);

    }

    @Override
    public int getItemCount() {
        return langData.length;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        ImageView imgIcon;
        TextView titleItem;
        public ListViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            titleItem = itemView.findViewById(R.id.itemTitle);
        }
    }
}
