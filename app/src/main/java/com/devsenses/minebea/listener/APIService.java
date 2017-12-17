package com.devsenses.minebea.listener;

import com.devsenses.minebea.model.BaseModel;
import com.devsenses.minebea.model.breakmodel.BreakReasonModel;
import com.devsenses.minebea.model.documentmodel.DocumentModel;
import com.devsenses.minebea.model.lineleadermodel.LineLeaderModel;
import com.devsenses.minebea.model.loginmodel.ContinueModel;
import com.devsenses.minebea.model.loginmodel.LoginModel;
import com.devsenses.minebea.model.loginmodel.OnProcessModel;
import com.devsenses.minebea.model.ngmodel.NGModel;
import com.devsenses.minebea.model.partmodel.LotModel;
import com.devsenses.minebea.model.partmodel.PartModel;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by pong.p on 1/14/2016.
 */
public interface APIService {
    @FormUrlEncoded
    @POST("api/user/login")
    Call<LoginModel> login(@Field("qr_code") String qrCode);

    @FormUrlEncoded
    @POST("api/process/get-continue-process")
    Call<ContinueModel> loadContinueProcess(@Field("qr_code") String qrCode);

    @FormUrlEncoded
    @POST("api/process/recover-work-status")
    Call<OnProcessModel> loadRecoverModel(@Field("qr_code") String qrCode, @Field("on_process") long onProcessID);

    @FormUrlEncoded
    @POST("api/process/model-data")
    Call<BaseModel> sendModel(@Field("qr_code") String qrCode, @Field("working_date") String workingDate,
                              @Field("shift_id") long shiftID, @Field("model_id") long modelID,
                              @Field("line_id") long lineID, @Field("process_id") long processID,
                              @Field("process_log_from") String processLoFrom);

    @FormUrlEncoded
    @POST("api/process/request-part")
    Call<PartModel> requestPart(@Field("qr_code") String qrCode, @Field("model_id") long modelID,
                                @Field("process_id") long processID);

    @FormUrlEncoded
    @POST("api/process/check-input-lot")
    Call<LotModel> checkPartAvailable(@Field("qr_code") String qrCode, @Field("parts") String parts,
                                      @Field("wip_lots") String wipLots);

    @GET("api/user/line-leader")
    Call<LineLeaderModel> requestLineLeader(@Query("qr_code") String qrCode);

    @FormUrlEncoded
    @POST("api/process/keep-first-serial")
    Call<BaseModel> sendLineLeader(@Field("qr_code") String qrCode, @Field("line_leader") long lineLeader,
                                   @Field("first_serial_no") String firstSerialNo, @Field("lot_number") String lotNumber,
                                   @Field("lot_id") long lotId);

    @FormUrlEncoded
    @POST("api/process/process-start")
    Call<BaseModel> startProcess(@Field("qr_code") String qrCode);

    @GET("api/document-category")
    Call<DocumentModel> getDocumentList(@Query("qr_code") String qrCode, @Query("product_id") long productID,
                                        @Query("line_id") long lineID, @Query("process_id") long processID);

    @GET("api/process/break-list")
    Call<BreakReasonModel> getBreakReasonList(@Query("qr_code") String qrCode);

    @FormUrlEncoded
    @POST("api/process/process-break")
    Call<BaseModel> startBreak(@Field("qr_code") String qrCode, @Field("break_id") long breakReasonId);

    @GET("api/process/ng-list")
    Call<NGModel> getNGList(@Query("qr_code") String qrCode);

    @FormUrlEncoded
    @POST("api/process/process-finish")
    Call<BaseModel> finishProcess(@Field("qr_code") String qrCode, @Field("ok_qty") int okQuantity,
                                  @Field("last_serial_no") String lastSerial, @Field("setup") int setup,
                                  @Field("dt") int dt, @Field("ngs") String ngListJson,
                                  @Field("breaks") String breakListJson, @Field("remark") String remark,
                                  @Field("start_time") String startDate, @Field("end_time") String endDate,
                                  @Field("wip_qty") String wipQty);
}
