package com.example.myapplication.extras;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


//https://blog.csdn.net/uu13567/article/details/78532170 resource
public class HttpUtils {
    public static final MediaType JSON= MediaType.parse("application/json; charset=utf-8");

    public static void sendHttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendHttpRequestPost(String path, String json, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .build();

        RequestBody requestBody = RequestBody.create(JSON ,json);

        Request request = new Request.Builder()
                .url(path)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

}
