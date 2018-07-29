package com.shifu.user.recrutin_android;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.shifu.user.recrutin_android.json.JobsResponse;
import com.shifu.user.recrutin_android.json.JsonApi;
import com.shifu.user.recrutin_android.realm.Jobs;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListFragment extends Fragment{

    private final String CLASS_TAG = ListFragment.class.getSimpleName();

    // Init layout elements
    private View mProgressView;
    private NestedScrollView scrollView;
    private CheckBox checkSalaryView;
    private RecyclerView recyclerView;

    // Init search variables
    private boolean isLoading = false;

    private String lastSearch = null;
    private boolean maxScrolled = false;
    private int pageNumber = 1;


    // Init Rx variables
    private static CompositeDisposable compositeDisposable;
    //private static Disposable currentDisposable = null;
    private PublishProcessor<Integer> paginator = PublishProcessor.create();

    // Init REST variables
    private final static String URL = " http://142.93.33.19/api/";

    // Init program variables
    private static RealmController rc = null;
    private static RealmRVAdapter ra = null;



    final Handler h = new Handler(new Handler.Callback() {
        String TAG = CLASS_TAG+".h";

        @Override
        public boolean handleMessage(android.os.Message msg) {
            Log.d(TAG, "Event type:" + Integer.toString(msg.what));
            Log.d(TAG, "Event:" + msg.obj);
            if (msg.what == 1) {
                switch ((String) msg.obj) {
                    case "RC.clear":
                        subscribeForData(lastSearch, h);
                        break;

                    case "RC.addJobs":
                        ra.setData(rc.getBase(Jobs.class, "title"));
                        showProgress(isLoading = false);
                        break;
                    case "FC.loadAllJobs":
                        showProgress(isLoading = false);
                        break;
                }
            }
            if (msg.what == 0) {
                showProgress(isLoading = false);
            }
            if (msg.what == 3) {
                showProgress(isLoading = false);
                lastSearch = null;
                Toast.makeText(getActivity(), getResources().getString(R.string.toast_empty), Toast.LENGTH_LONG).show();
            }
            return false;
        }
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_fragment, container, false);
        setUpToolbar(view);

        if (rc == null) rc = MainActivity.getRC();
        if (ra == null) ra = MainActivity.getRA();



        final EditText searchText = view.findViewById(R.id.search_text);
        searchText.requestFocus();

        mProgressView = view.findViewById(R.id.progress);
        checkSalaryView = view.findViewById(R.id.check_salary);

        checkSalaryView.setOnClickListener(view12 -> lastSearch = null);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setNestedScrollingEnabled(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(ra);

        scrollView = view.findViewById(R.id.nested_scroll_view);
        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            final String TAG = "onScrolled";
            if (v.getChildAt(v.getChildCount() - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) && scrollY >= oldScrollY) {
                    Log.d(TAG, "next portion");
                    if (!isLoading && !maxScrolled) {
                        pageNumber++;
                        paginator.onNext(pageNumber);
                    }
                }
            }
        });

        Button b = view.findViewById(R.id.search_button);
        b.setOnClickListener(view1 -> {
            if (!isLoading && (lastSearch == null || !lastSearch.equals(searchText.getText().toString().toLowerCase()))) {
                lastSearch = searchText.getText().toString().toLowerCase();
                scrollView.scrollTo(0, 0);
                pageNumber = 1;
                maxScrolled = false;

                if (rc.getSize(Jobs.class) > 0) {
                    rc.clear(h);
                } else {
                    subscribeForData(lastSearch, h);
                }
            }
        });


//        searchText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (!isLoading && editable.length() > 3 && !editable.toString().equals(lastSearch)) {
//                    lastSearch = editable.toString();
//                    if (!isLoading) {
//                        showProgress(isLoading = true);
//                        MainActivity.getRC().setData(JsonFake.initEntryList(getResources()), h);
//                    }
//                }
//                searchText.requestFocus();
//            }
//        });

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
        int animTime = getResources().getInteger(android.R.integer.config_mediumAnimTime);

//        recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
//        recyclerView.animate().setDuration(animTime).alpha(
//                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
//            }
//        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(animTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }


    private void subscribeForData(String search, Handler h) {

        final String TAG = "subscribeForData";

        Disposable disposable = paginator
                .onBackpressureDrop()
                .concatMap((Function <Integer, Publisher <Response <JobsResponse>>>) page -> {
                    showProgress(isLoading = true);
                    return loadJobsRx(search, page);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response, search, TAG, h), error -> handleError(error, TAG, h));

        compositeDisposable.add(disposable);
        paginator.onNext(pageNumber);
    }

    public Flowable<Response<JobsResponse>> loadJobsRx(String search, int page) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(interceptor)
                .build();

        JsonApi jsonApi = new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(JsonApi.class);


        Flowable<Response<JobsResponse>> flowable;
        if (checkSalaryView.isChecked()) {
            flowable = jsonApi.loadJobsWithSalary(Integer.toString(page), search);
        } else {
            flowable = jsonApi.loadAllJobs(Integer.toString(page), search);
        }
        return flowable.subscribeOn(Schedulers.io());
    }

    private void handleResponse(Response<JobsResponse> response, String search, String TAG, Handler h) {
        if (response.isSuccessful()) {
            if (pageNumber == 1 && response.body().getJobs().size() == 0) {
                h.sendMessage(Message.obtain(h, 3, TAG));
            } else if (response.body().getJobs().size() == 0){
                maxScrolled = true;
                h.sendMessage(Message.obtain(h, 0));
            }
            else {
                rc.addJobs(response.body(), search, h);
            }
        } else {
            try {
                String error = (response.body() == null) ? "null" : response.body().toString();
                Log.e(TAG, "Response body: " + error);

                error = (response.errorBody() == null) ? "null" : response.errorBody().string();
                Log.e(TAG, "Response errorBody: " + error);

                h.sendMessage(Message.obtain(h, 0, TAG));
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.toString());
                h.sendMessage(Message.obtain(h, 0, TAG));
            }
        }
    }

    private void handleError(Throwable t, String TAG, Handler h) {
        Log.e(TAG, "Failure: " + t.toString());
        h.sendMessage(Message.obtain(h, 0, TAG));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
