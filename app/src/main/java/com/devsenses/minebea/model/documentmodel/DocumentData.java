package com.devsenses.minebea.model.documentmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class DocumentData implements Parcelable{

    private static final String FIELD_ID = "id";
    private static final String FIELD_DOCUMENTS = "documents";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_CREATED_AT = "created_at";
    private static final String FIELD_UPDATED_AT = "updated_at";


    @SerializedName(FIELD_ID)
    private long mId;
    @SerializedName(FIELD_DOCUMENTS)
    private List<Document> mDocuments;
    @SerializedName(FIELD_TITLE)
    private String mTitle;
    @SerializedName(FIELD_CREATED_AT)
    private String mCreatedAt;
    @SerializedName(FIELD_UPDATED_AT)
    private String mUpdatedAt;


    public DocumentData(){

    }

    public void setId(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public void setDocuments(List<Document> documents) {
        mDocuments = documents;
    }

    public List<Document> getDocuments() {
        return mDocuments;
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
        if(obj instanceof DocumentData){
            return ((DocumentData) obj).getId() == mId;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return ((Long)mId).hashCode();
    }

    public DocumentData(Parcel in) {
        mId = in.readLong();
        mDocuments = new ArrayList<>();
        in.readTypedList(mDocuments, Document.CREATOR);
        mTitle = in.readString();
        mCreatedAt = in.readString();
        mUpdatedAt = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DocumentData> CREATOR = new Creator<DocumentData>() {
        public DocumentData createFromParcel(Parcel in) {
            return new DocumentData(in);
        }

        public DocumentData[] newArray(int size) {
        return new DocumentData[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeTypedList(mDocuments);
        dest.writeString(mTitle);
        dest.writeString(mCreatedAt);
        dest.writeString(mUpdatedAt);
    }

    @Override
    public String toString(){
        return "id = " + mId + ", documents = " + mDocuments + ", title = " + mTitle + ", createdAt = " + mCreatedAt + ", updatedAt = " + mUpdatedAt;
    }


}