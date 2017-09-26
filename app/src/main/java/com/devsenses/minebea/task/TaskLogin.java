package com.devsenses.minebea.task;

import android.content.Context;

import com.devsenses.minebea.dialog.LoadingDialog;
import com.devsenses.minebea.listener.OnApiFailureRecoverProcessListener;
import com.devsenses.minebea.manager.LoginManager;
import com.devsenses.minebea.model.loginmodel.LoginModel;
import com.devsenses.minebea.model.loginmodel.OnProcessModel;
import com.devsenses.minebea.model.partmodel.PartModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by pong.p on 1/15/2016.
 */
public class TaskLogin extends Task {

    public static void asyncLogin(final Context context, final String qrCode, final LoginManager.LoginListener listener) {
        final LoadingDialog dialog = new LoadingDialog(context);
        dialog.show();

        Call<LoginModel> call = getService().login(qrCode);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Response<LoginModel> response, Retrofit retrofit) {
                if (response.isSuccess() && response.body() != null) {
                    LoginModel modelData = response.body();
                    listener.onLoginSuccess(modelData);
                } else {
                    String error;
                    try {
                        String errorBody = response.errorBody().string();
                        if(errorBody.contains("Invalid QR code")){
                            error = "Invalid QR code";
                        }else{
                            error = "Unexpected error found.";
                        }
                    } catch (Exception ex) {
                        logAPIFailure("asyncLogin fail",ex.getMessage());
                        error = "Unexpected error found.";
                        reportException("TaskLogin/asyncLogin",response.errorBody());
                    }
                    listener.onFailure(error);
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                logAPIFailure("asyncLogin Fail", t.getMessage());
                listener.onFailure("Please check your connection and try again");
                dialog.dismiss();
            }
        });
    }

    public static void loadRecoverProcess(final Context context, String qrCode, long processLogId,
                                          final OnApiFailureRecoverProcessListener listener) {
        final LoadingDialog dialog = new LoadingDialog(context);
        dialog.show();

        Call<OnProcessModel> call = getService().loadRecoverModel(qrCode, processLogId);

        call.enqueue(new Callback<OnProcessModel>() {
            @Override
            public void onResponse(Response<OnProcessModel> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    OnProcessModel modelData = response.body();
                    listener.onSuccess(modelData.getOnProcess());
                } else {
                    String error;
                    try {
                        Gson gson = new GsonBuilder().create();
                        PartModel modelData = gson.fromJson(response.errorBody().string(), PartModel.class);
                        error = modelData.getMetaDatum().getError();
                    }catch (Exception ex) {
                        error = "Unexpected error found.";
                        reportException("TaskLogin/loadRecoverProcess",response.errorBody());
                    }
                    listener.onFailure(error);
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                logAPIFailure("sendModel Fail", t.getMessage());
                listener.onFailure("Please check your connection and try again");
                dialog.dismiss();
            }
        });
    }
}
