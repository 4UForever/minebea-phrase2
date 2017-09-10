package com.devsenses.minebea.model.breakmodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pong.p on 2/2/2016.
 */
public class BreakReason{
    private static final String FIELD_ID = "id";
    private static final String FIELD_CODE = "code";
    private static final String FIELD_REASON = "reason";
    private static final String FIELD_FLAG = "flag";
    private static final String FIELD_CREATED_AT = "created_at";
    private static final String FIELD_UPDATED_AT = "updated_at";


    @SerializedName(FIELD_ID)
    private long mId;
    @SerializedName(FIELD_CODE)
    private String mCode;
    @SerializedName(FIELD_REASON)
    private String mReason;
    @SerializedName(FIELD_CREATED_AT)
    private String mCreatedAt;
    @SerializedName(FIELD_UPDATED_AT)
    private String mUpdatedAt;
    @SerializedName(FIELD_FLAG)
    private int mFlag;

    public long getId() {return mId;}
    public void setId(long id) {this.mId = id;}

    public String getCode() {return mCode;}
    public void setCode(String code) {this.mCode = code;}

    public String getReason() {return mReason;}
    public void setReason(String reason) {this.mReason = reason;}

    public String getCreatedAt() {return mCreatedAt;}

    public void setCreatedAt(String createdAt) {this.mCreatedAt = createdAt;}
    public String getUpdatedAt() {return mUpdatedAt;}

    public void setUpdatedAt(String updatedAt) {this.mUpdatedAt = updatedAt;}

    public int getFlag() {return mFlag;}
    public void setFlag(int flag) {this.mFlag = flag;}
}
