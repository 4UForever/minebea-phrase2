package com.devsenses.minebea.task;

import android.content.Context;

import com.devsenses.minebea.dialog.LoadingDialog;
import com.devsenses.minebea.listener.OnApiGetReasonListener;
import com.devsenses.minebea.listener.OnBaseApi;
import com.devsenses.minebea.model.breakmodel.BreakReasonModel;
import com.devsenses.minebea.model.BaseModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by pong.p on 2/2/2016.
 */
public class TaskBreak extends Task {

    public static void getBreakReasonList(final Context context, String qrCode, final OnApiGetReasonListener listener) {
        final LoadingDialog dialog = new LoadingDialog(context);
        dialog.show();

        Call<BreakReasonModel> call = getService().getBreakReasonList(qrCode);

        call.enqueue(new Callback<BreakReasonModel>() {
            @Override
            public void onResponse(Response<BreakReasonModel> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.body().getBreakReasonData());
                } else {
                    String error;
                    try {
                        Gson gson = new GsonBuilder().create();
                        BaseModel modelData = gson.fromJson(response.errorBody().string(), BaseModel.class);
                        error = modelData.getMetaDatum().getError();
                    }catch (Exception ex) {
                        error = "Unexpected error found.";
                        reportException("TaskBreak/getBreakReasonList",response.errorBody());
                    }
                    listener.onFailure(error);
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                logAPIFailure("getBreakReasonList Fail", t.getMessage());
                listener.onFailure("Please check your connection and try again");
                dialog.dismiss();
            }
        });
    }

    public static void startBreak(final Context context, String qrCode, long reasonId, final OnBaseApi listener) {
        final LoadingDialog dialog = new LoadingDialog(context);
        dialog.show();

        Call<BaseModel> call = getService().startBreak(qrCode, reasonId);

        call.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Response<BaseModel> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    listener.onSuccess();
                } else {
                    String error;
                    try {
                        Gson gson = new GsonBuilder().create();
                        BaseModel modelData = gson.fromJson(response.errorBody().string(), BaseModel.class);
                        error = modelData.getMetaDatum().getError();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        error = "Unexpected error found.";
                        reportException("TaskBreak/startBreak",response.errorBody());
                    }
                    listener.onFailure(error);
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                logAPIFailure("startBreak Fail", t.getMessage());
                listener.onFailure("Please check your connection and try again");
                dialog.dismiss();
            }
        });
    }
}
