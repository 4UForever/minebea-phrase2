package com.devsenses.minebea.task;

import android.content.Context;

import com.devsenses.minebea.dialog.LoadingDialog;
import com.devsenses.minebea.listener.OnBaseApiFailure;
import com.devsenses.minebea.model.BaseModel;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by pong.p on 1/15/2016.
 */
public class TaskProcess extends Task {

    public static void startProcess(final Context context, String qrCode, long modelID, long lineID,
                                    long processID, final StartProcessListener listener) {
        final LoadingDialog dialog = new LoadingDialog(context);
        dialog.show();

        Call<BaseModel> call = getService().startProcess(qrCode);

        call.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Response<BaseModel> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    listener.onStartSuccess();
                } else {
                    logAPIFailure("startProcess", response.errorBody());
                    listener.onFailure("Failed to start process.");
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                logAPIFailure("startProcess", t.getMessage());
                listener.onFailure("Please check your connection and try again");
                dialog.dismiss();
            }
        });
    }

    public interface StartProcessListener extends OnBaseApiFailure {
        void onStartSuccess();
    }
}
