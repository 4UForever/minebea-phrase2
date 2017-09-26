package com.devsenses.minebea.model.loginmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Shift implements Parcelable {

    private static final String FIELD_ID = "id";
    private static final String FIELD_LABEL = "label";
    private static final String FIELD_TIME = "time_string";

    @SerializedName(FIELD_ID)
    private long mId;
    @SerializedName(FIELD_LABEL)
    private String mLabel;
    @SerializedName(FIELD_TIME)
    private String mTime;

    public Shift() {

    }

    public void setId(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        this.mLabel = label;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        this.mTime = time;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Shift) {
            return ((Shift) obj).getId() == mId;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ((Long) mId).hashCode();
    }

    public Shift(Parcel in) {
        mId = in.readLong();
        mLabel = in.readString();
        mTime = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Shift> CREATOR = new Creator<Shift>() {
        public Shift createFromParcel(Parcel in) {
            return new Shift(in);
        }

        public Shift[] newArray(int size) {
            return new Shift[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mLabel);
        dest.writeString(mTime);
    }

    @Override
    public String toString() {
        return "id = " + mId + ", label = " + mLabel + ", time = " + mTime;
    }


}