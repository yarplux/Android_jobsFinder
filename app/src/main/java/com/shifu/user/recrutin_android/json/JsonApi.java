package com.shifu.user.recrutin_android.json;

import io.reactivex.Flowable;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonApi {

    @POST("{key}")
    Call<JoobleJsonResponse> loadJobs(@Path("key") String key, @Body JoobleJsonRequest jsonRequest);

    @POST("{key}")
    Flowable<Response<JoobleJsonResponse>> loadJobsRx(@Path("key") String key, @Body JoobleJsonRequest jsonRequest);


}
