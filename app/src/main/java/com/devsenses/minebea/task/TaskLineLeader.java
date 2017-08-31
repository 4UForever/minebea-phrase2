package com.devsenses.minebea.task;

import android.content.Context;

import com.devsenses.minebea.dialog.LoadingDialog;
import com.devsenses.minebea.listener.OnBaseApi;
import com.devsenses.minebea.manager.LineLeaderManager;
import com.devsenses.minebea.model.BaseModel;
import com.devsenses.minebea.model.lineleadermodel.LineLeaderModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by pong.p on 1/15/2016.
 */
public class TaskLineLeader extends Task {

    public static void getLineLeader(final Context context, String qrCode,
                                     final LineLeaderManager.OnLoadLineLeaderListener listener) {
        final LoadingDialog dialog = new LoadingDialog(context);
        dialog.show();

        Call<LineLeaderModel> call = getService().requestLineLeader(qrCode);

        call.enqueue(new Callback<LineLeaderModel>() {
            @Override
            public void onResponse(Response<LineLeaderModel> response, Retrofit retrofit) {
                if (response.isSuccess()) {
//                    Log.d(TAG, "requestLineLeader onSuccess() returned " + response.body());
                    LineLeaderModel modelData = response.body();
//                    Log.d(TAG, "onResponse " + modelData.getLineLeaderList().toString());
                    listener.onSuccess(modelData.getLineLeaderList());
                } else {
                    String error;
                    try {
                        Gson gson = new GsonBuilder().create();
                        LineLeaderModel modelData = gson.fromJson(response.errorBody().string(), LineLeaderModel.class);
                        error = modelData.getMetaDatum().getError();
                    } catch (Exception ex) {
                        error = "Unexpected error found.";
                        reportException("TaskLineLeader/getLineLeader",response.errorBody());
                    }
                    listener.onFailure(error);
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                logAPIFailure("getLineLeader Fail", t.getMessage());
                listener.onFailure("");
                dialog.dismiss();
            }
        });
    }

    public static void sendLineLeader(final Context context, String qrCode, long lineLeader, String serial,
                                      String lotNo, Long lotId, final OnBaseApi listener) {
        final LoadingDialog dialog = new LoadingDialog(context);
        dialog.show();

        Call<BaseModel> call = getService().sendLineLeader(qrCode, lineLeader, serial, lotNo, lotId);

        call.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Response<BaseModel> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    listener.onSuccess();
                } else {
                    String error;
                    try {
                        Gson gson = new GsonBuilder().create();
                        LineLeaderModel modelData = gson.fromJson(response.errorBody().string(), LineLeaderModel.class);
                        error = modelData.getMetaDatum().getError();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        error = "Unexpected error found.";
                        reportException("TaskLineLeader/sendLineLeader",response.errorBody());
                    }
                    listener.onFailure(error);
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                logAPIFailure("sendLineLeader Fail", t.getMessage());
                listener.onFailure("Please check your connection and try again");
                dialog.dismiss();
            }
        });
    }
}
