package com.devsenses.minebea.model.lineleadermodel;

import com.devsenses.minebea.model.MetaDatum;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pong.p on 1/5/2016.
 */
public class LineLeaderModel {

    private static final String FIELD_META_DATA = "meta_data";
    private static final String FIELD_DATA = "data";


    @SerializedName(FIELD_META_DATA)
    private MetaDatum mMetaDatum;
    @SerializedName(FIELD_DATA)
    private List<LineLeader> mLineLeaderList;


    public LineLeaderModel(){

    }

    public void setMetaDatum(MetaDatum metaDatum) {
        mMetaDatum = metaDatum;
    }

    public MetaDatum getMetaDatum() {
        return mMetaDatum;
    }

    public void setLineLeaderList(List<LineLeader> datum) {
        mLineLeaderList = datum;
    }

    public List<LineLeader> getLineLeaderList() {
        return mLineLeaderList;
    }
}
