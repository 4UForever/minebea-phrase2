package com.devsenses.minebea.model.partmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pong.p on 1/11/2016.
 */
public class PartData implements Parcelable {
    private static final String FIELD_PROCESS_LOG_ID = "process_log_id";
    private static final String FIELD_PARTS = "parts";


    @SerializedName(FIELD_PROCESS_LOG_ID)
    private long mProcessLogId;
    @SerializedName(FIELD_PARTS)
    private List<Part> mPart;


    public PartData(){}

    public void setProcessLogID(long id) {
        mProcessLogId = id;
    }

    public long getProcessLogID() {
        return mProcessLogId;
    }

    public void setPartList(List<Part> parts) {
        mPart = parts;
    }

    public List<Part> getPartList() {
        return mPart;
    }

    public PartData(Parcel in) {
        mProcessLogId = in.readLong();
        mPart = new ArrayList<>();
        in.readTypedList(mPart, Part.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PartData> CREATOR = new Creator<PartData>() {
        public PartData createFromParcel(Parcel in) {
            return new PartData(in);
        }

        public PartData[] newArray(int size) {
            return new PartData[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mProcessLogId);
        dest.writeTypedList(mPart);
    }

    @Override
    public String toString(){
        return "mProcessLogId = " + mProcessLogId + ", mPart = " + Arrays.toString(mPart.toArray());
    }

}
