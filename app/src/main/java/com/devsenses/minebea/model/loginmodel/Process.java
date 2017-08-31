package com.devsenses.minebea.model.loginmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Process implements Parcelable{

    private static final String FIELD_ID = "id";
    private static final String FIELD_NUMBER = "number";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_CREATED_AT = "created_at";
    private static final String FIELD_UPDATED_AT = "updated_at";


    @SerializedName(FIELD_ID)
    private long mId;
    @SerializedName(FIELD_NUMBER)
    private String mNumber;
    @SerializedName(FIELD_TITLE)
    private String mTitle;
    @SerializedName(FIELD_CREATED_AT)
    private String mCreatedAt;
    @SerializedName(FIELD_UPDATED_AT)
    private String mUpdatedAt;


    public Process(){

    }

    public void setId(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public void setNumber(String number) {
        mNumber = number;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Process){
            return ((Process) obj).getId() == mId;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return ((Long)mId).hashCode();
    }

    public Process(Parcel in) {
        mId = in.readLong();
        mNumber = in.readString();
        mTitle = in.readString();
        mCreatedAt = in.readString();
        mUpdatedAt = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Process> CREATOR = new Creator<Process>() {
        public Process createFromParcel(Parcel in) {
            return new Process(in);
        }

        public Process[] newArray(int size) {
        return new Process[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mNumber);
        dest.writeString(mTitle);
        dest.writeString(mCreatedAt);
        dest.writeString(mUpdatedAt);
    }

    @Override
    public String toString(){
        return "id = " + mId + ", number = " + mNumber + ", title = " + mTitle + ", createdAt = " + mCreatedAt + ", updatedAt = " + mUpdatedAt;
    }


}