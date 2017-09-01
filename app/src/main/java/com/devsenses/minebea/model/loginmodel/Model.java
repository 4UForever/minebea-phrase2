package com.devsenses.minebea.model.loginmodel;

import java.util.ArrayList;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import android.os.Parcel;


public class Model implements Parcelable{

    private static final String FIELD_ID = "id";
    private static final String FIELD_LINES = "lines";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_CREATED_AT = "created_at";
    private static final String FIELD_UPDATED_AT = "updated_at";


    @SerializedName(FIELD_ID)
    private long mId;
    @SerializedName(FIELD_LINES)
    private List<Line> mLines;
    @SerializedName(FIELD_TITLE)
    private String mTitle;
    @SerializedName(FIELD_CREATED_AT)
    private String mCreatedAt;
    @SerializedName(FIELD_UPDATED_AT)
    private String mUpdatedAt;


    public Model(){

    }

    public void setId(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public void setLines(List<Line> lines) {
        mLines = lines;
    }

    public List<Line> getLines() {
        return mLines;
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
        if(obj instanceof Model){
            return ((Model) obj).getId() == mId;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return ((Long)mId).hashCode();
    }

    public Model(Parcel in) {
        mId = in.readLong();
        mLines = new ArrayList<>();
        in.readTypedList(mLines, Line.CREATOR);
        mTitle = in.readString();
        mCreatedAt = in.readString();
        mUpdatedAt = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        public Model[] newArray(int size) {
        return new Model[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeTypedList(mLines);
        dest.writeString(mTitle);
        dest.writeString(mCreatedAt);
        dest.writeString(mUpdatedAt);
    }

    @Override
    public String toString(){
        return "id = " + mId + ", lines = " + mLines + ", title = " + mTitle + ", createdAt = " + mCreatedAt + ", updatedAt = " + mUpdatedAt;
    }


}