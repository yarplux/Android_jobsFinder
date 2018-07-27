package com.shifu.user.recrutin_android;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.shifu.user.recrutin_android.json.JoobleJsonRequest;
import com.shifu.user.recrutin_android.json.JoobleJsonResponse;
import com.shifu.user.recrutin_android.json.JsonApi;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestController {

    private final static String URL = "https://us.jooble.org/api/";
    private final static String API_KEY = "55a5dcfd-6776-4725-8e2b-2e5a5c279a77";

    private final static String CLASS_TAG = "FC.";

    private final static RealmController rc;

    // Предполагается, что вызвать этот класс не будет вызван раньше onCreate MainActivity
    static {
        rc = MainActivity.getRC();
    }

    private static JsonApi init(String baseUrl) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(JsonApi.class);
    }

    /**
     *
     * @param obj - searchText
     * @param h - handler
     */
    public static void loadJobs(final Bundle obj, final Handler h) {

        JsonApi jsonApi = init(URL);
        final String TAG = CLASS_TAG+"loadJobs";
        JoobleJsonRequest request = new JoobleJsonRequest(obj.getString("searchText"), obj.getString("page"));

        Log.d(TAG, API_KEY);
        jsonApi.loadJobs(API_KEY, request).enqueue(new Callback<JoobleJsonResponse>() {
            @Override
            public void onResponse(@NotNull Call<JoobleJsonResponse> call, @NotNull Response<JoobleJsonResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Success for: " +response.body().toString());
                        rc.addJoobleJobs(response.body(), h);
                } else {
                    try {
                        String error = (response.body() == null)?"null":response.body().toString();
                        Log.e(TAG, "Response body: "+error);

                        error = (response.errorBody() == null)?"null":response.errorBody().toString();
                        Log.e(TAG, "Response errorBody: "+error);

                        h.sendMessage(Message.obtain(h, 0, TAG));
                    } catch (Exception e) {
                        Log.e(TAG, "Exception: "+e.toString());
                        h.sendMessage(Message.obtain(h, 0, TAG));
                    }
                }
            }
            @Override
            public void onFailure(@NotNull Call<JoobleJsonResponse> call, @NotNull Throwable t) {
                Log.e(TAG, "Failure: "+t.toString());
            }
        });
    }
}
