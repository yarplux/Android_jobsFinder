package com.shifu.user.recrutin_android.json;

import io.reactivex.Flowable;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonApi {

    @POST("{key}")
    Flowable<Response<JoobleJsonResponse>> loadJobsRx(@Path("key") String key, @Body JoobleJsonRequest jsonRequest);

    @GET()
    Flowable<Response<JoobleJsonResponse>> loadJobs(@Header("page") int page, @Header("keywords") String keywords);

}
