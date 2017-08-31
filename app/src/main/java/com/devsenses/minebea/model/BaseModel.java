package com.devsenses.minebea.model;

import com.devsenses.minebea.model.MetaDatum;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pong.p on 1/15/2016.
 */
public class BaseModel {
    private static final String FIELD_META_DATA = "meta_data";


    @SerializedName(FIELD_META_DATA)
    private MetaDatum mMetaDatum;


    public BaseModel(){

    }

    public void setMetaDatum(MetaDatum metaDatum) {
        mMetaDatum = metaDatum;
    }

    public MetaDatum getMetaDatum() {
        return mMetaDatum;
    }

}
