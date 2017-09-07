package com.devsenses.minebea.task;

import android.content.Context;

import com.devsenses.minebea.dialog.LoadingDialog;
import com.devsenses.minebea.listener.OnBaseApi;
import com.devsenses.minebea.model.BaseModel;
import com.devsenses.minebea.model.loginmodel.SelectedModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by pong.p on 1/15/2016.
 */
public class TaskModel extends Task {

    public static void sendModel(final Context context, String qrCode, SelectedModel selectedModel,
                                 final OnBaseApi listener) {
        final LoadingDialog dialog = new LoadingDialog(context);
        dialog.show();

        Call<BaseModel> call = getService().sendModel(qrCode, selectedModel.getWorkingDate(),
                selectedModel.getShiftID(), selectedModel.getID(),
                selectedModel.getLineID(), selectedModel.getProcessID());

        call.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Response<BaseModel> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    listener.onSuccess();
                }else {
                    String error;
                    try {
                        Gson gson = new GsonBuilder().create();
                        BaseModel modelData = gson.fromJson(response.errorBody().string(), BaseModel.class);
                        error = modelData.getMetaDatum().getError();
                    }catch (Exception ex) {
                        error = "Unexpected error found.";
                        reportException("TaskModel/sendModel",response.errorBody());
                        ex.printStackTrace();
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
