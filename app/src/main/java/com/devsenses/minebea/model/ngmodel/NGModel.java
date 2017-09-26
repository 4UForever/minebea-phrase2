package com.devsenses.minebea.model.ngmodel;

import com.devsenses.minebea.model.MetaDatum;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pong.p on 2/3/2016.
 */
public class NGModel {
    private static final String FIELD_META_DATA = "meta_data";
    private static final String FIELD_DATA = "data";


    @SerializedName(FIELD_META_DATA)
    private MetaDatum mMetaDatum;
    @SerializedName(FIELD_DATA)
    private NGListData mNGList;


    public NGModel() {
    }

    public MetaDatum getMetaDatum() {
        return mMetaDatum;
    }

    public void setMetaDatum(MetaDatum metaDatum) {
        this.mMetaDatum = metaDatum;
    }

    public NGListData getNGList() {
        return mNGList;
    }

    public void setNGList(NGListData ngListData) {
        this.mNGList = ngListData;
    }
}
