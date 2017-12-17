package com.devsenses.minebea.model.loginmodel;

import com.devsenses.minebea.model.MetaDatum;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by USER on 16/12/2560.
 */

public class ContinueModel {
    private static final String FIELD_META_DATA = "meta_data";
    private static final String FIELD_DATA = "data";


    @SerializedName(FIELD_META_DATA)
    private MetaDatum mMetaDatum;
    @SerializedName(FIELD_DATA)
    private List<ContinueData> continueDataList;

    public MetaDatum getMetaDatum() {
        return mMetaDatum;
    }

    public void setMetaDatum(MetaDatum metaDatum) {
        this.mMetaDatum = metaDatum;
    }

    public List<ContinueData>  getContinueDataList() {
        return continueDataList;
    }

    public void setContinueDataList(List<ContinueData>  continueDataList) {
        this.continueDataList = continueDataList;
    }
}
