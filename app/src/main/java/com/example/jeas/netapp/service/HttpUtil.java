package com.example.jeas.netapp.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Jeas on 2021/2/2.
 */

public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
