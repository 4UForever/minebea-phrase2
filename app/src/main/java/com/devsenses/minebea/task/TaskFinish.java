package com.devsenses.minebea.task;

import android.content.Context;
import android.util.Log;

import com.devsenses.minebea.dialog.LoadingDialog;
import com.devsenses.minebea.listener.OnBaseApi;
import com.devsenses.minebea.model.BaseModel;
import com.devsenses.minebea.model.FinishModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by pong.p on 2/4/2016.
 */
public class TaskFinish extends Task {
    public static void finishProcess(final Context context, FinishModel model, final OnBaseApi listener) {
        final LoadingDialog dialog = new LoadingDialog(context);
        dialog.show();

        Log.d("MineBea", model.toString());

        Call<BaseModel> call = getService().finishProcess(model.getQrCode(), model.getOkQty(),
                model.getLastSerialNo(), model.getSetup(), model.getDt(), model.getNgs(),
                model.getBreaks(), model.getRemark(), model.getStartDate(), model.getEndDate());

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
                    } catch (RuntimeException re) {
                        error = "Unexpected error found.";
                        reportException("TaskFinish/finishProcess", response.errorBody());
                    } catch (Exception ex) {
                        error = "Unexpected error found.";
                        reportException("TaskFinish/finishProcess", response.errorBody());
                        ex.printStackTrace();
                    }
                    listener.onFailure(error);
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                logAPIFailure("finishProcess Fail", t.getMessage());
                listener.onFailure("Please check your connection and try again");
                dialog.dismiss();
            }
        });
    }
}
