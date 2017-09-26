package com.devsenses.minebea.model.loginmodel;

import com.devsenses.minebea.model.MetaDatum;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pong.p on 1/13/2016.
 */
public class OnProcessModel {
    private static final String FIELD_META_DATA = "meta_data";
    private static final String FIELD_DATA = "data";


    @SerializedName(FIELD_META_DATA)
    private MetaDatum mMetaDatum;
    @SerializedName(FIELD_DATA)
    private ProcessLog mProcessLog;


    public OnProcessModel(){

    }

    public void setMetaDatum(MetaDatum metaDatum) {
        mMetaDatum = metaDatum;
    }

    public MetaDatum getMetaDatum() {
        return mMetaDatum;
    }

    public void setOnProcess(ProcessLog datum) {
        mProcessLog = datum;
    }

    public ProcessLog getOnProcess() {
        return mProcessLog;
    }
}
