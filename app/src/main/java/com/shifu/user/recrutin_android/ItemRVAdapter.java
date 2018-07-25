package com.shifu.user.recrutin_android;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shifu.user.recrutin_android.json.JsonFake;

import java.util.List;

public class ItemRVAdapter extends RecyclerView.Adapter<ItemRVAdapter.ItemViewHolder> {

    private List<JsonFake> itemList;
    private AppCompatActivity activity;

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView title, salary, company, description;
        String url;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            salary = itemView.findViewById(R.id.item_salary);
            company = itemView.findViewById(R.id.item_company);
            description = itemView.findViewById(R.id.item_description);

            title.setClickable(true);
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WebFragment fragment = new WebFragment();
                    Bundle args = new Bundle();
                    args.putString("url", url);
                    fragment.setArguments(args);
                    ((NavigationHost) activity).navigateTo(fragment, true);
                }
            });
        }
    }

    ItemRVAdapter(AppCompatActivity activity){
        this.activity = activity;
    }

    public void setData(List<JsonFake> itemList){
        this.itemList = itemList;
        notifyDataSetChanged();
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

            SpannableString asUrl = new SpannableString(item.title);
            asUrl.setSpan(new UnderlineSpan(), 0, item.title.length(), 0);
            holder.title.setText(asUrl);

            holder.salary.setText(item.salary);
            holder.company.setText(item.company);
            holder.description.setText(item.description);
            holder.url = item.url;

            //TODO save url or make title clickable by it here
        }
    }

    @Override
    public int getItemCount() {
        if (itemList == null) {
            return 0;
        } else {
            return itemList.size();
        }
    }
}
