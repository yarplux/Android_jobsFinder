package com.shifu.user.recrutin_android.json;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonApi {

    String MESSAGES = "messages";
    String USERS = "users";

//    /**
//     *  Messages actions
//    */
//    @GET(MESSAGES+".json")
//    Call<Map<String, JsonMsg>> loadMessages(@Query("orderBy") String authorField, @Query("equalTo") String authorName, @Query("auth") String idToken);
//
//    @POST(MESSAGES+".json")
//    Call<JsonResponse> pushMessage(@Body JsonMsg jsonBody, @Query("auth") String idToken);
//
//    @DELETE(MESSAGES+"/{firebase_id}.json")
//    Call<JsonResponse> deleteMessage(@Path("firebase_id") String firebase_id, @Query("auth") String idToken);
//
//    @PUT(MESSAGES+"/{firebase_id}.json")
//    Call<JsonMsg> putMessage(@Path("firebase_id") String firebase_id, @Body JsonMsg jsonBody, @Query("auth") String idToken);
//
//
//    /**
//     * Users actions with base of users
//    */
//    @POST("{action}")
//    Call<JsonLoginResponse> login(@Path("action") String action, @Header("Content-Type") String header, @Query("key") String API_KEY, @Body JsonLoginRequest request);
//
//    @POST("token")
//    Call<JsonRefreshResponse> refresh(@Header("Content-Type") String header, @Query("key") String API_KEY, @Body JsonRefreshRequest request);
//
//    @POST("setAccountInfo")
//    Call<JsonNewResponse> changeName(@Header("Content-Type") String header, @Query("key") String API_KEY, @Body JsonNewNameRequest request);
//
//    @POST("setAccountInfo")
//    Call<JsonNewResponse> changeName(@Header("Content-Type") String header, @Query("key") String API_KEY, @Body JsonNewPassRequest request);
//
//
//
//    /**
//     * Users actions with table of users in database
//     */
//    @PUT(USERS+"/{uid}.json")
//    Call<JsonResponse> pushUser(@Path("uid") String uid, @Body JsonResponse jsonBody, @Query("auth") String idToken);
//
//    @GET(USERS+"/{uid}.json")
//    Call<JsonResponse> getUser(@Path("uid") String uid, @Body JsonResponse jsonBody, @Query("auth") String idToken);
//
//    @GET(USERS+".json")
//    Call<Map<String, JsonResponse>> loadUsers(@Query("auth") String idToken);

}
