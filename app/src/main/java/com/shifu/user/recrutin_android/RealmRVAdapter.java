package com.shifu.user.recrutin_android;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.shifu.user.recrutin_android.realm.Jobs;

import org.jetbrains.annotations.NotNull;

import io.realm.OrderedRealmCollection;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

/**
 * работа с listener-ами: https://stackoverflow.com/questions/28995380/best-practices-to-use-realm-with-a-recycler-view/32758869
 */

class RealmRVAdapter extends RealmRecyclerViewAdapter<Jobs, RealmRVAdapter.ViewHolder> {

    private Handler h;
    private final static String TAG = "RA";

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, salary, company, updated; //description;
        String url;

        public Jobs data;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            salary = itemView.findViewById(R.id.item_salary);
            company = itemView.findViewById(R.id.item_company);
            //description = itemView.findViewById(R.id.item_description);
            updated = itemView.findViewById(R.id.item_updated);

            title.setClickable(true);
            title.setOnClickListener(view -> h.sendMessage(Message.obtain(h, 2, url)));
        }
    }

    RealmRVAdapter(OrderedRealmCollection<Jobs> data, Handler h) {
        super(data, true);
        setHasStableIds(true);
        this.h = h;
        h.sendMessage(Message.obtain(h, 1, TAG));
    }

    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder viewHolder, int position) {
        final Jobs obj = getItem(position);

        viewHolder.data = obj;

        viewHolder.title.setText(obj.getTitle());
        viewHolder.company.setText(obj.getCompany());
        //viewHolder.description.setText(obj.getDescription());
        viewHolder.salary.setText(obj.getSalary());
        viewHolder.updated.setText(obj.getUpdated());

        viewHolder.url = obj.getUrl();

        }

    @Override
    public long getItemId(int index) {
        //Log.d("RA.getItemId", getItem(index).toString());
        //TODO "заплатка" (return должен быть long, a uuid string. По утверждению автора работает норм примерно до 1*10^6 записей)
        return java.nio.ByteBuffer.wrap(getItem(index).getUid().getBytes()).asLongBuffer().get();
    }

    public void setData(OrderedRealmCollection<Jobs> data){
        Log.d(TAG, "setDataSize: "+data.size()+" from baseSize: "+MainActivity.getRC().getSize(Jobs.class));
        updateData(data);
        notifyDataSetChanged();
    }

}
