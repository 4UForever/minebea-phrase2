package com.devsenses.minebea.task;

import android.content.Context;

import com.devsenses.minebea.dialog.LoadingDialog;
import com.devsenses.minebea.listener.OnLoadDocumentListenerFailure;
import com.devsenses.minebea.model.documentmodel.DocumentModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by pong.p on 1/15/2016.
 */
public class TaskDocument extends Task {

    public static void getDocumentList(Context context, String qrCode, long modelID, long lineID,
                                       long processID, final OnLoadDocumentListenerFailure listener) {

        final LoadingDialog dialog = new LoadingDialog(context);
        dialog.show();

        Call<DocumentModel> call = getService().getDocumentList(qrCode, modelID, lineID, processID);

        call.enqueue(new Callback<DocumentModel>() {
            @Override
            public void onResponse(Response<DocumentModel> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    listener.onLoadSuccess(response.body());
                } else {
                    String error;
                    try {
                        Gson gson = new GsonBuilder().create();
                        DocumentModel modelData = gson.fromJson(response.errorBody().string(), DocumentModel.class);
                        error = modelData.getMetaDatum().getError();
                    }catch (Exception ex) {
                        error = "Unexpected error found.";
                    }
                    logAPIFailure("getDocumentList Fail",response.errorBody());
                    listener.onFailure(error);
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                logAPIFailure("getDocumentList Fail", t.getMessage());
                listener.onFailure("Please check your connection and try again");
                dialog.dismiss();
            }
        });
    }
}
