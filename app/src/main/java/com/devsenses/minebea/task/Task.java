package com.devsenses.minebea.task;

import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.devsenses.minebea.constant.Constant;
import com.devsenses.minebea.listener.APIService;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Horus on 2/12/2015.
 */
public class Task {
    private static final String TAG = "Task";

    protected static APIService getService() {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                //for show response content as String before convert to Gson
                com.squareup.okhttp.Response response = chain.proceed(chain.request());
                final String content = response.body().string();
                Log.d(TAG, content);
                return response.newBuilder()
                        .body(ResponseBody.create(response.body().contentType(), content))
                        .build();
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(APIService.class);
    }

    protected static String logAPIFailure(String tag, ResponseBody responseBody) {
        try {
            Log.e(TAG, tag + " : " + responseBody.string());
            return responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    protected static String logAPIFailure(String tag, String message) {
        Log.e(TAG, tag + " : " + message);
        return message;
    }

    public static void reportException(String msg, ResponseBody rb){
        try {
            Crashlytics.log(msg+"\n"+rb.string());
            Crashlytics.logException(new Exception(msg+"\n"+rb.string()));
        } catch (Exception e) {
            e.printStackTrace();
            Crashlytics.log(msg+"\ncan't get ResponseBody");
            Crashlytics.logException(new Exception(msg+"\ncan't get ResponseBody"));
        }

    }
}
