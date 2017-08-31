package com.devsenses.minebea.model.loginmodel;

import com.devsenses.minebea.model.partmodel.Part;
import com.devsenses.minebea.model.partmodel.WIP;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pong.p on 12/30/2015.
 */
public class ProcessLog {
    private static final String FIELD_PROCESS_LOG = "process_log";
    private static final String FIELD_WORKING_PAGE = "working_page";
    private static final String FIELD_PARTS = "parts";
    private static final String FIELD_WIP_LOT = "wip_lots";

    @SerializedName(FIELD_PROCESS_LOG)
    private Data mData;
    @SerializedName(FIELD_WORKING_PAGE)
    private boolean mIsOnWorkingPage;
    @SerializedName(FIELD_PARTS)
    private List<Part> mPart;
    @SerializedName(FIELD_WIP_LOT)
    private List<WIP> mWipLot;

    public void setData(Data data){mData = data;}
    public Data getData(){ return mData;}

    public boolean isOnWorkingPage() {return mIsOnWorkingPage;}
    public void setIsOnWorkingPage(boolean isOnWorkingPage) {this.mIsOnWorkingPage = isOnWorkingPage;}

    public List<Part> getPart() {return mPart;}
    public void setPart(List<Part> part) {this.mPart = part;}

    public List<WIP> getWipLot() {return mWipLot;}
    public void setWipLot(List<WIP> wipLot) {this.mWipLot = wipLot;}


    public static class Data{
        private static final String FIELD_ID = "id";
        private static final String FIELD_USER_ID = "user_id";
        private static final String FIELD_USER_EMAIL = "user_email";
        private static final String FIELD_MODEL_ID = "model_id";
        private static final String FIELD_MODEL_TITLE = "model_title";
        private static final String FIELD_LINE_ID = "line_id";
        private static final String FIELD_LINE_TITLE = "line_title";
        private static final String FIELD_PROCESS_ID = "process_id";
        private static final String FIELD_PROCESS_TITLE = "process_title";
        private static final String FIELD_PROCESS_NUMBER = "process_number";
        private static final String FIELD_LOT_NUMBER = "lot_number";
        private static final String FIELD_ON_BREAK = "on_break";

        @SerializedName(FIELD_ID)
        private long mId;
        @SerializedName(FIELD_USER_ID)
        private long mUserId;
        @SerializedName(FIELD_USER_EMAIL)
        private String mUserEmail;
        @SerializedName(FIELD_MODEL_ID)
        private long mModelId;
        @SerializedName(FIELD_MODEL_TITLE)
        private String mModelTitle;
        @SerializedName(FIELD_LINE_ID)
        private long mLineId;
        @SerializedName(FIELD_LINE_TITLE)
        private String mLineTitle;
        @SerializedName(FIELD_PROCESS_ID)
        private long mProcessId;
        @SerializedName(FIELD_PROCESS_TITLE)
        private String mProcessTitle;
        @SerializedName(FIELD_PROCESS_NUMBER)
        private String mProcessNumber;
        @SerializedName(FIELD_LOT_NUMBER)
        private String mLotNumber;
        @SerializedName(FIELD_ON_BREAK)
        private Long mOnBreak;

        public void setId(long id){mId = id;}
        public long getId(){ return mId;}

        public void setUserId(long userId){mUserId = userId;}
        public long getUserId(){ return mUserId;}

        public void setUserEmail(String userEmail){mUserEmail = userEmail;}
        public String getUserEmail(){ return mUserEmail;}

        public void setModelId(long modelId){mModelId = modelId;}
        public long getModelId(){ return mModelId;}

        public void setModelTitle(String title){mModelTitle = title;}
        public String getModelTitle(){ return mModelTitle;}

        public void setLineId(long lineId){mLineId = lineId;}
        public long getLineId(){ return mLineId;}

        public void setLineTitle(String title){mLineTitle = title;}
        public String getLineTitle(){ return mLineTitle;}

        public void setProcessId(long processId){mProcessId = processId;}
        public long getProcessId(){ return mProcessId;}

        public void setProcessTitle(String title){mProcessTitle = title;}
        public String getProcessTitle(){ return mProcessTitle;}

        public void setProcessNumber(String number){mProcessNumber = number;}
        public String getProcessNumber(){ return mProcessNumber;}

        public void setLotNumber(String lotNumber){mLotNumber = lotNumber;}
        public String getLotNumber(){ return mLotNumber;}

        public void setOnBreak(Long onBreak){mOnBreak = onBreak;}
        public Long getOnBreak(){return mOnBreak;}

    }
}
