package com.devsenses.minebea.task;

import android.content.Context;
import android.util.Log;

import com.devsenses.minebea.dialog.LoadingDialog;
import com.devsenses.minebea.listener.OnApiGetPartWIPListener;
import com.devsenses.minebea.listener.OnBaseApi;
import com.devsenses.minebea.listener.OnBaseApiFailure;
import com.devsenses.minebea.model.BaseModel;
import com.devsenses.minebea.model.partmodel.LotDataModel;
import com.devsenses.minebea.model.partmodel.LotModel;
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
public class TaskPartAndWIP extends Task {

    public static void getPart(final Context context, String qrCode, long modelId, long processId,
                               final OnApiGetPartWIPListener listener) {
        final LoadingDialog dialog = new LoadingDialog(context);
        dialog.show();

        Call<PartModel> call = getService().requestPart(qrCode, modelId, processId);

        call.enqueue(new Callback<PartModel>() {
            @Override
            public void onResponse(Response<PartModel> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    PartModel modelData = response.body();
//                    if (modelData.getPartData().getPartList().isEmpty()) {
//                        //TODO for test only
//                        Part part1 = new Part();
//                        part1.setId(10);
//                        part1.setNumber("7215-637 mock");
//                        part1.setName("Insulator 1 mock");
//                        Part part2 = new Part();
//                        part2.setId(11);
//                        part2.setNumber("1315001827 mock");
//                        part2.setName("Magnet wire mock");
//                        List<Part> list = new ArrayList<>();
//                        list.add(part1);
//                        list.add(part2);
//                        modelData.getPartData().setPartList(list);
//                    }
                    listener.onSuccess(modelData);
                } else {
                    String error;
                    try {
                        Gson gson = new GsonBuilder().create();
                        PartModel modelData = gson.fromJson(response.errorBody().string(), PartModel.class);
                        error = modelData.getMetaDatum().getError();
                    }catch (RuntimeException re){
                        error = "Unexpected error found.";
                        reportException("TaskPartAndWIP/getPart",response.errorBody());
                    } catch (Exception ex) {
                        error = "Unexpected error found.";
                        ex.printStackTrace();
                        reportException("TaskPartAndWIP/getPart",response.errorBody());
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

    public static void checkPartAvailable(final Context context, String qrCode, String partLot, String wipLot,
                                          final OnCheckPartListener listener) {
        final LoadingDialog dialog = new LoadingDialog(context);
        dialog.show();
        Log.d("Task", "partLot : " + partLot);
        Log.d("Task", "wipLot : " + wipLot);
        Call<LotModel> call = getService().checkPartAvailable(qrCode, partLot, wipLot);

        call.enqueue(new Callback<LotModel>() {
            @Override
            public void onResponse(Response<LotModel> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.body().getLotData().getLotDataModel());
                } else {
                    String error;
                    try {
                        Gson gson = new GsonBuilder().create();
                        BaseModel modelData = gson.fromJson(response.errorBody().string(), BaseModel.class);
                        error = modelData.getMetaDatum().getError();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        error = "Unexpected error found.";
                        reportException("TaskPartAndWIP/checkPartAvailable",response.errorBody());
                    }
                    logAPIFailure("sendModel Fail", error);
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

    public interface OnCheckPartListener extends OnBaseApiFailure {
        void onSuccess(LotDataModel lotDataModel);
    }
}
