package com.devsenses.minebea.task;

import android.content.Context;

import com.devsenses.minebea.dialog.LoadingDialog;
import com.devsenses.minebea.listener.OnApiGetNGListener;
import com.devsenses.minebea.model.BaseModel;
import com.devsenses.minebea.model.ngmodel.NGModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by pong.p on 2/4/2016.
 */
public class TaskNG extends Task {
    public static void getNGList(final Context context, String qrCode, final OnApiGetNGListener listener) {
        final LoadingDialog dialog = new LoadingDialog(context);
        dialog.show();

        Call<NGModel> call = getService().getNGList(qrCode);

        call.enqueue(new Callback<NGModel>() {
            @Override
            public void onResponse(Response<NGModel> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.body().getNGList());
                } else {
                    String error;
                    try {
                        Gson gson = new GsonBuilder().create();
                        BaseModel modelData = gson.fromJson(response.errorBody().string(), BaseModel.class);
                        error = modelData.getMetaDatum().getError();
                    }catch (Exception ex) {
                        error = "Unexpected error found.";
                        reportException("TaskNG/getNGList",response.errorBody());
                        ex.printStackTrace();
                    }
                    listener.onFailure(error);
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                logAPIFailure("getNGList Fail", t.getMessage());
                listener.onFailure("Please check your connection and try again");
                dialog.dismiss();
            }
        });
    }
}
