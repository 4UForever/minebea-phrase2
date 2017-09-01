package com.devsenses.minebea.model.ngmodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pong.p on 2/3/2016.
 */
public class NG {
    private static final String FIELD_ID = "id";
    private static final String FIELD_PROCESS_ID = "process_id";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_CREATED_AT = "created_at";
    private static final String FIELD_UPDATED_AT = "updated_at";


    @SerializedName(FIELD_ID)
    private long id;
    @SerializedName(FIELD_PROCESS_ID)
    private String mProcessId;
    @SerializedName(FIELD_TITLE)
    private String mTitle;
    @SerializedName(FIELD_CREATED_AT)
    private String mCreatedAt;
    @SerializedName(FIELD_UPDATED_AT)
    private String mUpdatedAt;

    public NG() {}

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public String getProcessId() {return mProcessId;}
    public void setProcessId(String processId) {this.mProcessId = processId;}

    public String getTitle() {return mTitle;}
    public void setTitle(String title) {this.mTitle = title;}

    public String getCreatedAt() {return mCreatedAt;}
    public void setCreatedAt(String createdAt) {this.mCreatedAt = createdAt;}

    public String getUpdatedAt() {return mUpdatedAt;}
    public void setUpdatedAt(String updatedAt) {this.mUpdatedAt = updatedAt;}
}
