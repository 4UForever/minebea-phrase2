package com.devsenses.minebea.model.partmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by pong.p on 3/31/2016.
 */
public class WIP implements Parcelable {
    private static final String FIELD_NUMBER = "number";
    private static final String FIELD_QUANTITY = "quantity";

    @SerializedName(FIELD_NUMBER)
    private String mNumber;
    @SerializedName(FIELD_QUANTITY)
    private long mQuantity;

    public WIP() {
    }

    public void setQuantity(long quantity) {
        mQuantity = quantity;
    }

    public long getQuantity() {
        return mQuantity;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String mNumber) {
        this.mNumber = mNumber;
    }

    public WIP(Parcel in) {
        mNumber = in.readString();
        mQuantity = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WIP> CREATOR = new Creator<WIP>() {
        public WIP createFromParcel(Parcel in) {
            return new WIP(in);
        }

        public WIP[] newArray(int size) {
            return new WIP[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mNumber);
        dest.writeLong(mQuantity);
    }

    @Override
    public String toString() {
        return "{number:" + mNumber + ",quantity:" + mQuantity + "}";
    }
}
