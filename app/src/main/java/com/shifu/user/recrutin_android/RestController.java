package com.shifu.user.recrutin_android;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestController {

    private final static String URL_AUTH = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/";
    private final static String URL_REFRESH = "https://securetoken.googleapis.com/v1/";
    private final static String URL_DATABASE = "https://shifu-ad6cd.firebaseio.com/";

    private final static String API_KEY = "AIzaSyAfOcB4p-ewv5LQhtNGRRlcg3_S8Iip-CY";

    private final static String MAIL_DOMAIN = "@mail.ru";

    private final static String ERROR_TOKEN_EXPIRED = "Auth token is expired";
    private final static String ERROR_TOKEN_OLD = "CREDENTIAL_TOO_OLD_LOGIN_AGAIN";

    private final static String CLASS_TAG = "FC.";

//    private static JsonApi init(String baseUrl) {
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//
//        return retrofit.create(JsonApi.class);
//    }
//
//    private static RealmController rc() {
//        return ActivityMain.getRC();
//    }
//
//    public static void login(final String user, String pass, final Boolean newUser, final Handler h) {
//
//        JsonApi jsonApi = init(URL_AUTH);
//        String mail = user+MAIL_DOMAIN;
//        String action = (newUser)?"signupNewUser":"verifyPassword";
//
//        final String TAG = CLASS_TAG+"login";
//
//        final Request request = jsonApi.login(action, "application/json", API_KEY, new JsonLoginRequest(mail,pass)).request();
//        Log.d(TAG, "URL:"+request.url().toString());
//        Log.d(TAG, "Request:"+request.body().toString());
//
//        jsonApi.login(action,"application/json", API_KEY, new JsonLoginRequest(mail,pass)).enqueue(new Callback<JsonLoginResponse>() {
//            @Override
//            public void onResponse(@NotNull Call<JsonLoginResponse> call, @NotNull Response<JsonLoginResponse> response) {
//                if (response.isSuccessful()) {
//                    Log.d(TAG, "Success:" + response.body().toString() + " New User? " + Boolean.toString(newUser));
//
//                    Bundle obj = new Bundle();
//                    obj.putString("username", user);
//                    obj.putString("uid", response.body().getLocalId());
//                    obj.putString("idToken", response.body().getIdToken());
//                    obj.putString("refreshToken", response.body().getRefreshToken());
//
//                    if (newUser) {
//                        RestController.pushUser(obj, h);
//                    } else {
//                        rc().changeUser(obj, h);
//                        h.sendMessage(Message.obtain(h, 2, TAG));
//                    }
//                } else {
//                    try {
//                        ResponseError(TAG, response.errorBody().string(), h);
//                    } catch (Exception e) {
//                        Log.e(TAG, "Exception: "+e.toString());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<JsonLoginResponse> call, @NotNull Throwable t) {
//                Log.e(TAG, "Failure: "+t.toString());
//            }
//        });
//    }
//
//    /**
//     *
//     * @param obj - username, idToken
//     * @param h - handler
//     */
//    public static void updateName(final Bundle obj, final Handler h) {
//
//        JsonApi jsonApi = init(URL_AUTH);
//        String mail = obj.getString("username")+MAIL_DOMAIN;
//        final String TAG = "FC.updateName";
//
//        JsonNewNameRequest jRequest = new JsonNewNameRequest(obj.getString("idToken"),mail, false);
//
//        Log.d(TAG, jsonApi.changeName("application/json", API_KEY, jRequest).request().toString());
//        jsonApi.changeName("application/json", API_KEY, jRequest)
//                .enqueue(new Callback<JsonNewResponse>() {
//
//            @Override
//            public void onResponse(@NotNull Call<JsonNewResponse> call, @NotNull Response<JsonNewResponse> response) {
//                if (response.isSuccessful()) {
//                    Log.d(TAG, "Success");
//                    rc().changeUserName(obj, h);
//                } else {
//                    try {
//                        JSONObject jObj = new JSONObject(response.errorBody().string().replaceAll("\\\\", ""));
//                        Log.d(TAG, jObj.getString("error"));
//                        Log.d(TAG, jObj.getJSONObject("error").getString("message"));
//                        if (jObj.getJSONObject("error").getString("message").equals(ERROR_TOKEN_OLD)) {
//                            refresh(TAG, obj, h);
//                            h.sendMessage(Message.obtain(h,0,ERROR_TOKEN_OLD));
//                        }
//                    } catch (Exception e) {
//                        Log.e(TAG, "Response:"+response.toString());
//                        Log.e(TAG, "Exception: "+e.toString());
//                    }
//                }
//            }
//            @Override
//            public void onFailure(@NotNull Call<JsonNewResponse> call, @NotNull Throwable t) {
//                Log.e(TAG, "Failure: "+t.toString());
//                Log.e(TAG, Boolean.toString(t.toString().contains("Unable to resolve host")));
//                h.sendMessage(Message.obtain(h, 0, TAG));
//            }
//        });
//    }
//
//    /**
//     *
//     * @param obj - password, idToken
//     * @param h - handler
//     */
//    public static void updatePass(final Bundle obj, final Handler h) {
//
//        JsonApi jsonApi = init(URL_AUTH);
//        final String TAG = "FC.updatePass";
//
//        JsonNewPassRequest jRequest = new JsonNewPassRequest(obj.getString("idToken"),obj.getString("password"), false);
//        Log.d(TAG, jsonApi.changeName("application/json", API_KEY, jRequest).request().toString());
//
//        jsonApi.changeName("application/json", API_KEY, jRequest)
//                .enqueue(new Callback<JsonNewResponse>() {
//
//                    @Override
//                    public void onResponse(@NotNull Call<JsonNewResponse> call, @NotNull Response<JsonNewResponse> response) {
//                        if (response.isSuccessful()) {
//                            Log.d(TAG, "Success");
//                            rc().changeToken(obj, h);
//                            h.sendMessage(Message.obtain(h, 1, TAG));
//                        } else {
//                            try {
//                                JSONObject jObj = new JSONObject(response.errorBody().string().replaceAll("\\\\", ""));
//                                Log.d(TAG, jObj.getString("error"));
//                                Log.d(TAG, jObj.getJSONObject("error").getString("message"));
//                                if (jObj.getJSONObject("error").getString("message").equals(ERROR_TOKEN_OLD)) {
//                                    refresh(TAG, obj, h);
//                                    h.sendMessage(Message.obtain(h,0,ERROR_TOKEN_OLD));
//                                }                            } catch (Exception e) {
//                                Log.e(TAG, "Exception: "+e.toString());
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NotNull Call<JsonNewResponse> call, @NotNull Throwable t) {
//                        Log.e(TAG, "Failure: "+t.toString());
//                    }
//                });
//    }
//
//    /**
//     *
//     * @param source - Calling function name
//     * @param arg - refreshToken + function args
//     * @param h - handler
//     */
//    private static void refresh(final String source, final Bundle arg, final Handler h) {
//
//        JsonApi jsonApi = init(URL_REFRESH);
//
//        final String TAG = "FC.refresh";
//        Log.d(TAG, jsonApi
//                .refresh("application/json", API_KEY, new JsonRefreshRequest(arg.getString("refreshToken")))
//                .request()
//                .toString());
//
//        jsonApi.refresh("application/json", API_KEY, new JsonRefreshRequest(arg.getString("refreshToken")))
//                .enqueue(new Callback<JsonRefreshResponse>() {
//
//                    @Override
//                    public void onResponse(@NotNull Call<JsonRefreshResponse> call, @NotNull Response<JsonRefreshResponse> response) {
//                        if (response.isSuccessful()) {
//                            Log.d(TAG, "Success:" + response.body().toString());
//                            //Log.d(TAG, "NewToken:" + response.body().getIdToken());
//
//                            arg.putString("idToken", response.body().getIdToken());
//                            arg.putString("refreshToken", response.body().getRefreshToken());
//                            rc().refreshUser(source, arg, h);
//                        } else {
//                            try {
//                                Log.d(TAG, response.errorBody().string());
//                            } catch (Exception e) {
//                                Log.e(TAG, "Exception: "+e.toString());
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NotNull Call<JsonRefreshResponse> call, @NotNull Throwable t) {
//                        Log.e(TAG, "Failure: "+t.toString());
//                    }
//                });
//    }
//
////    // TODO: при подключении учесть создание событий в handler и обработку в функции, куда передаются ошибки
////    public static void loadUsers(final Auth auth, final Handler h) {
////
////        JsonApi jsonApi = init(URL_DATABASE);
////        final String TAG = "FC.loadUsers";
////        Log.d(TAG,  jsonApi.loadUsers(auth.getIdToken()).request().toString());
////        jsonApi.loadUsers(auth.getIdToken()).enqueue(new Callback<Map<String, JsonResponse>>() {
////            @Override
////            public void onResponse(@NotNull Call<Map<String, JsonResponse>> call, @NotNull Response<Map<String, JsonResponse>> response) {
////                if (response.isSuccessful()) {
////                    Log.d(TAG+" Success!", response.body().toString());
////                    Map<String, JsonResponse> data = response.body();
////                    data.remove(auth.getUid());
////                    rc().addUsers(data, h);
////                } else {
////                    Log.e(TAG+" Err", response.toString());
////                    h.sendMessage(Message.obtain(h, 0, response.errorBody().toString()));
////                }
////            }
////
////            @Override
////            public void onFailure(@NotNull Call<Map<String, JsonResponse>> call, @NotNull Throwable t) {
////                Log.e(TAG, "Failure: "+t.toString());
////            }
////        });
////    }
//
//    /**
//     *
//     * @param obj - uid, username, idToken, refreshToken
//     * @param h - handler
//     */
//    public static void pushUser(final Bundle obj, final Handler h) {
//
//        JsonApi jsonApi = init(URL_DATABASE);
//        final String TAG = "FC.pushUser";
//
//        Log.d(TAG, jsonApi.pushUser(obj.getString("uid"), new JsonResponse(obj.getString("username")), obj.getString("idToken")).request().toString());
//        jsonApi.pushUser(obj.getString("uid"), new JsonResponse(obj.getString("username")), obj.getString("idToken"))
//                .enqueue(new Callback<JsonResponse>() {
//
//            @Override
//            public void onResponse(@NotNull Call<JsonResponse> call, @NotNull Response<JsonResponse> response) {
//                if (response.isSuccessful()) {
//                    Log.d(TAG, "Success: "+response.body().toString());
//                    rc().changeUser(obj, h);
//                    h.sendMessage(Message.obtain(h, 2, obj));
//                } else {
//                    try {
//                        String error = new JSONObject(response.errorBody().string().replaceAll("\\\\", "")).getString("error");
//                        Log.d(TAG, "Error: " + error);
//                        if (error.equals(ERROR_TOKEN_EXPIRED)) {
//                            refresh(TAG, obj, h);
//                        }
//                    } catch (Exception e) {
//                        Log.e(TAG, "Exception: "+e.toString());
//                    }
//                }
//            }
//            @Override
//            public void onFailure(@NotNull Call<JsonResponse> call, @NotNull Throwable t) {
//                Log.e(TAG, "Failure: "+t.toString());
//            }
//        });
//    }
//
//    /**
//     *
//     * @param obj - uid, idToken, username, refreshToken
//     * @param h - handler
//     */
//    public static void loadMsgs(final Bundle obj, final Handler h) {
//
//        JsonApi jsonApi = init(URL_DATABASE);
//        final String TAG = "FC.loadMsgs";
//        String field = "\"uid\"";
//        String value = "\""+obj.getString("uid")+"\"";
//        Log.d(TAG, jsonApi.loadMessages(field, value, obj.getString("idToken")).request().url().toString());
//        jsonApi.loadMessages(field, value, obj.getString("idToken")).enqueue(new Callback<Map<String, JsonMsg>>() {
//            @Override
//            public void onResponse(@NotNull Call<Map<String, JsonMsg>> call, @NotNull Response<Map<String, JsonMsg>> response) {
//                if (response.isSuccessful()) {
//                    Log.d(TAG, "Success for : " + obj.getString("username") + "\n"+response.body().toString());
//                    rc().addMsgs(response.body(), h);
//                    h.sendMessage(Message.obtain(h, 1, TAG));
//                } else {
//                    try {
//                        String error = new JSONObject(response.errorBody().string().replaceAll("\\\\", "")).getString("error");
//                        Log.d(TAG, error);
//                        if (error.equals(ERROR_TOKEN_EXPIRED)) {
//                            refresh(TAG, obj, h);
//                        }
//                    } catch (Exception e) {
//                        Log.e(TAG, "Exception: "+e.toString());
//                    }
//                }
//            }
//            @Override
//            public void onFailure(@NotNull Call<Map<String, JsonMsg>> call, @NotNull Throwable t) {
//                Log.e(TAG, "Failure: "+t.toString());
//            }
//        });
//    }
//
//    /**
//     *
//     * @param obj - Msg: text, date, uuid; User: uid, idToken, refreshToken
//     * @param h - handler
//     */
//    public static void pushMsg(final Bundle obj, final Handler h) {
//
//        JsonApi jsonApi = init(URL_DATABASE);
//        final String TAG = "FC.pushMsg";
//
//        JsonMsg jsonMsg = new JsonMsg(obj.getString("text"), obj.getLong("date"), obj.getString("uid"));
//        Log.d(TAG, jsonApi.pushMessage(jsonMsg, obj.getString("idToken")).request().url().toString());
//        jsonApi.pushMessage(jsonMsg, obj.getString("idToken")).enqueue(new Callback<JsonResponse>() {
//
//            @Override
//            public void onResponse(@NotNull Call<JsonResponse> call, @NotNull Response<JsonResponse> response) {
//
//                if (response.isSuccessful()) {
//                    Log.d(TAG, "Success: "+response.body().toString());
//
//                    obj.putString("firebase_id", response.body().getName());
//                    rc().setMsgFid(obj, h);
//                    h.sendMessage(Message.obtain(h, 8, response.body().getName()));
//                } else {
//                    try {
//                        JSONObject jObj = new JSONObject(response.errorBody().string().replaceAll("\\\\", ""));
//                        Log.d(TAG, jObj.getString("error"));
//                        if (jObj.getString("error").equals(ERROR_TOKEN_EXPIRED)) {
//                            refresh(TAG, obj, h);
//                        }
//                    } catch (Exception e) {
//                        Log.e(TAG, "Exception: "+e.toString());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<JsonResponse> call, @NotNull Throwable t) {
//                Log.e(TAG, "Failure: "+t.toString());
//            }
//        });
//    }
//
//    /**
//     *
//     * @param obj - Msg: firebase_id; User: idToken, refreshToken
//     * @param h - handler
//     */
//    public static void delMsg(final Bundle obj, final Handler h) {
//
//        JsonApi jsonApi = init(URL_DATABASE);
//        final String TAG = "FC.delMsg";
//        Log.d(TAG, jsonApi.deleteMessage(obj.getString("firebase_id"), obj.getString("idToken")).request().url().toString());
//        jsonApi.deleteMessage(obj.getString("firebase_id"),obj.getString("idToken"))
//                .enqueue(new Callback<JsonResponse>() {
//
//            @Override
//            public void onResponse(@NotNull Call<JsonResponse> call, @NotNull Response<JsonResponse> response) {
//                if (response.isSuccessful()) {
//                    h.sendMessage(Message.obtain(h, 1, TAG+":Success"));
//                } else {
//                    try {
//                        JSONObject jObj = new JSONObject(response.errorBody().string().replaceAll("\\\\", ""));
//                        h.sendMessage(Message.obtain(h, 0, TAG+":"+jObj.getString("error")));
//                        if (jObj.getString("error").equals(ERROR_TOKEN_EXPIRED)) {
//                            refresh(TAG, obj, h);
//                        }
//                    } catch (Exception e) {
//                        Log.e(TAG, "Exception: "+e.toString());
//                    }
//                }
//
//            }
//            @Override
//            public void onFailure(@NotNull Call<JsonResponse> call, @NotNull Throwable t) {
//                Log.e(TAG, "Failure: "+t.toString());
//            }
//        });
//
//    }
//
//    /**
//     *
//     * @param obj - Msg: firebase_id, text, date; User: uid, idToken, refreshToken
//     * @param h - handler
//     */
//    public static void updateMsg(final Bundle obj, final Handler h) {
//
//        JsonApi jsonApi = init(URL_DATABASE);
//        final String TAG = "FC.updateMsg";
//        Log.d(TAG,obj.toString());
//
//        JsonMsg jsonMsg = new JsonMsg(obj.getString("text"),obj.getLong("date"), obj.getString("uid"));
//        jsonApi.putMessage(obj.getString("firebase_id"), jsonMsg, obj.getString("idToken"))
//                .enqueue(new Callback<JsonMsg>() {
//
//            @Override
//            public void onResponse(@NotNull Call<JsonMsg> call, @NotNull Response<JsonMsg> response) {
//                if (response.isSuccessful()) {
//                    Log.d(TAG+" Success", response.body().toString());
//                } else {
//                    try {
//                        JSONObject jObj = new JSONObject(response.errorBody().string().replaceAll("\\\\", ""));
//                        Log.d(TAG, jObj.getString("error"));
//                        if (jObj.getString("error").equals(ERROR_TOKEN_EXPIRED)) {
//                            refresh(TAG, obj, h);
//                        }
//                    } catch (Exception e) {
//                        Log.e(TAG, "Exception: "+e.toString());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<JsonMsg> call, @NotNull Throwable t) {
//                Log.e(TAG, "Failure: "+t.toString());
//            }
//        });
//
//    }
//
//    private  static void ResponseError(String TAG, String errorMessage, Handler h) throws JSONException {
//            String error = new JSONObject(errorMessage).getJSONObject("error").getString("message");
//            Log.e(TAG+"Error: ", error);
//            h.sendMessage(Message.obtain(h, 0, error));
//    }
}
