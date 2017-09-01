package com.devsenses.minebea.model.documentmodel;

import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import android.os.Parcel;


public class Document implements Parcelable{

    private static final String FIELD_ID = "id";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_DOWNLOAD_URL = "download_url";
    private static final String FIELD_CREATED_AT = "created_at";
    private static final String FIELD_UPDATED_AT = "updated_at";


    @SerializedName(FIELD_ID)
    private long mId;
    @SerializedName(FIELD_TITLE)
    private String mTitle;
    @SerializedName(FIELD_DOWNLOAD_URL)
    private String mDownloadUrl;
    @SerializedName(FIELD_CREATED_AT)
    private String mCreatedAt;
    @SerializedName(FIELD_UPDATED_AT)
    private String mUpdatedAt;


    public Document(){

    }

    public void setId(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
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
        if(obj instanceof Document){
            return ((Document) obj).getId() == mId;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return ((Long)mId).hashCode();
    }

    public Document(Parcel in) {
        mId = in.readLong();
        mTitle = in.readString();
        mDownloadUrl = in.readString();
        mCreatedAt = in.readString();
        mUpdatedAt = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Document> CREATOR = new Creator<Document>() {
        public Document createFromParcel(Parcel in) {
            return new Document(in);
        }

        public Document[] newArray(int size) {
        return new Document[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mTitle);
        dest.writeString(mDownloadUrl);
        dest.writeString(mCreatedAt);
        dest.writeString(mUpdatedAt);
    }

    @Override
    public String toString(){
        return "id = " + mId + ", title = " + mTitle + ", downloadUrl = " + mDownloadUrl + ", createdAt = " + mCreatedAt + ", updatedAt = " + mUpdatedAt;
    }


}