package com.shifu.user.recrutin_android.json;

import io.reactivex.Flowable;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface JsonApi {

    @GET(".")
    Flowable<Response<JobsResponse>> loadAllJobs(
            @Header("page") String page,
            @Header("keywords") String keywords);

    @GET("?only-with-salary")
    Flowable<Response<JobsResponse>> loadJobsWithSalary(
            @Query("page") String page,
            @Query("keywords") String keywords);

}
