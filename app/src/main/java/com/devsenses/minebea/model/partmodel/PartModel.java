package com.devsenses.minebea.model.partmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.devsenses.minebea.model.MetaDatum;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pong.p on 1/11/2016.
 */
public class PartModel implements Parcelable {
    private static final String FIELD_META_DATA = "meta_data";
    private static final String FIELD_DATA = "data";


    @SerializedName(FIELD_META_DATA)
    private MetaDatum mMetaDatum;
    @SerializedName(FIELD_DATA)
    private PartData mPartData;


    public PartModel() {
    }

    public void setMetaDatum(MetaDatum metaDatum) {
        mMetaDatum = metaDatum;
    }

    public MetaDatum getMetaDatum() {
        return mMetaDatum;
    }

    public void setPartData(PartData data) {
        mPartData = data;
    }

    public PartData getPartData() {
        return mPartData;
    }

    public PartModel(Parcel in) {
        mMetaDatum = in.readParcelable(MetaDatum.class.getClassLoader());
        mPartData = in.readParcelable(PartData.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PartModel> CREATOR = new Creator<PartModel>() {
        public PartModel createFromParcel(Parcel in) {
            return new PartModel(in);
        }

        public PartModel[] newArray(int size) {
            return new PartModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mMetaDatum, flags);
        dest.writeParcelable(mPartData, flags);
    }

    @Override
    public String toString() {
        return "metaDatum = " + mMetaDatum + "// mPartData = " + mPartData;
    }

}
