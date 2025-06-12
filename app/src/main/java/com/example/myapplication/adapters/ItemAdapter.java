package com.example.myapplication.adapters;


import android.content.Context;
import com.example.myapplication.R;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activities.ItemDetailActivity;
import com.example.myapplication.models.Item;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<Item> itemList;
    private int userId;

    public ItemAdapter(Context context, ArrayList<Item> itemList, int userId) {
        this.context = context;
        this.itemList = itemList;
        this.userId = userId;
    }

    public void updateData(ArrayList<Item> newList) {
        itemList = newList;
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.textTitle.setText(item.getTitle());
        holder.textDesc.setText(item.getDescription());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ItemDetailActivity.class);
            intent.putExtra("mode", "edit");
            intent.putExtra("item_id", item.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textDesc;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDesc = itemView.findViewById(R.id.textDesc);
        }
    }
}
