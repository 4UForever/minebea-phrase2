package com.devsenses.minebea.model.breakmodel;

import com.devsenses.minebea.model.MetaDatum;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pong.p on 2/2/2016.
 */
public class BreakReasonModel {
    private static final String FIELD_META_DATA = "meta_data";
    private static final String FIELD_DATA = "data";


    @SerializedName(FIELD_META_DATA)
    private MetaDatum mMetaDatum;
    @SerializedName(FIELD_DATA)
    private BreakReasonData mBreakReasonData;


    public BreakReasonModel() {
    }

    public void setMetaDatum(MetaDatum metaDatum) {
        mMetaDatum = metaDatum;
    }

    public MetaDatum getMetaDatum() {
        return mMetaDatum;
    }

    public void setBreakReasonData(BreakReasonData breakReasonData) {
        mBreakReasonData = breakReasonData;
    }

    public BreakReasonData getBreakReasonData() {
        return mBreakReasonData;
    }

}
