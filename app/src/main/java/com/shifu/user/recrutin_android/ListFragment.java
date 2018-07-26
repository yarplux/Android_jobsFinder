package com.shifu.user.recrutin_android;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.shifu.user.recrutin_android.json.JsonFake;

public class ListFragment extends Fragment{

    private boolean isLoading = false;
    private RecyclerView recyclerView;
    private View mProgressView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_fragment, container, false);

        setUpToolbar(view);


        final Handler h = new Handler(new Handler.Callback() {
            String TAG = "H.ListFragment";

            @Override
            public boolean handleMessage(android.os.Message msg) {
                Log.d(TAG, "Event type:" + Integer.toString(msg.what));
                Log.d(TAG, "Event:" + msg.obj);
                if (msg.what == 1) {
                    switch ((String) msg.obj) {
                        case "RC.setData":
                            showProgress(isLoading = false);
                            MainActivity.getRA().notifyDataSetChanged();
                            break;
                    }
                }
                return false;
            }
        });

        mProgressView = view.findViewById(R.id.progress);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(MainActivity.getRA());

        Button b = view.findViewById(R.id.search_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLoading) {
                    showProgress(isLoading = true);
                    MainActivity.getRC().setData(JsonFake.initEntryList(getResources()), h);
                }
            }
        });

        EditText searchText = view.findViewById(R.id.search_text);
        searchText.requestFocus();
        return view;
    }


    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        recyclerView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }


}
