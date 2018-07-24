package com.shifu.user.recrutin_android;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shifu.user.recrutin_android.json.JsonFake;

import java.util.List;

public class ItemRVAdapter extends RecyclerView.Adapter<ItemRVAdapter.ItemViewHolder> {

    private List<JsonFake> itemList;

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView title, salary, company, description;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            salary = itemView.findViewById(R.id.item_salary);
            company = itemView.findViewById(R.id.item_company);
            description = itemView.findViewById(R.id.item_description);
        }
    }

    ItemRVAdapter(List<JsonFake> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ItemViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if (itemList != null && position < itemList.size()) {
            JsonFake item = itemList.get(position);
            holder.title.setText(item.title);
            holder.salary.setText(item.salary);
            holder.company.setText(item.company);
            holder.description.setText(item.description);

            //TODO save url or make title clickable by it here
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
