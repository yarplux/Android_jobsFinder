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

    @GET(".")
    Flowable<Response<JobsResponse>> loadAllJobs(
            @Header("page") String page,
            @Header("keywords") String keywords);

    @GET(".")
    Flowable<Response<JobsResponse>> loadJobsWithSalary(
            @Header("page") String page,
            @Header("keywords") String keywords,
            @Header("only-with-salary") String withSalary);
}
