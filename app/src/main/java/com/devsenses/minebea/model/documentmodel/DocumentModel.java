package com.devsenses.minebea.model.documentmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.devsenses.minebea.model.MetaDatum;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class DocumentModel implements Parcelable{

    private static final String FIELD_META_DATA = "meta_data";
    private static final String FIELD_DATA = "data";


    @SerializedName(FIELD_META_DATA)
    private MetaDatum mMetaDatum;
    @SerializedName(FIELD_DATA)
    private List<DocumentData> mData;


    public DocumentModel(){

    }

    public void setMetaDatum(MetaDatum metaDatum) {
        mMetaDatum = metaDatum;
    }

    public MetaDatum getMetaDatum() {
        return mMetaDatum;
    }

    public void setData(List<DocumentData> data) {
        mData = data;
    }

    public List<DocumentData> getData() {
        return mData;
    }

    public DocumentModel(Parcel in) {
        mMetaDatum = in.readParcelable(MetaDatum.class.getClassLoader());
        mData = new ArrayList<>();
        in.readTypedList(mData, DocumentData.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DocumentModel> CREATOR = new Creator<DocumentModel>() {
        public DocumentModel createFromParcel(Parcel in) {
            return new DocumentModel(in);
        }

        public DocumentModel[] newArray(int size) {
        return new DocumentModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mMetaDatum, flags);
        dest.writeTypedList(mData);
    }

    @Override
    public String toString(){
        return "metaDatum = " + mMetaDatum + ", data = " + mData;
    }


}