package com.devsenses.minebea.model.ngmodel;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by pong.p on 2/3/2016.
 */
@Parcel(Parcel.Serialization.BEAN)
public class NGListData {

    private static final String FIELD_BREAKS = "ngList";

    @SerializedName(FIELD_BREAKS)
    private List<NG> mNGList;

    public NGListData() {}

    public void setNGList(List<NG> list){mNGList = list;}
    public List<NG> getNGList(){ return mNGList;}

    @Override
    public String toString() {
        return "NGListData{" +
                "mNGList=" + mNGList +
                '}';
    }
}
