package com.devsenses.minebea.model.partmodel;

import com.devsenses.minebea.model.MetaDatum;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pong.p on 3/31/2016.
 */
public class LotModel {
    private static final String FIELD_META_DATA = "meta_data";
    private static final String FIELD_DATA = "data";

    @SerializedName(FIELD_META_DATA)
    private MetaDatum mMetaDatum;
    @SerializedName(FIELD_DATA)
    private Data mData;

    public LotModel() {
    }

    public void setMetaDatum(MetaDatum metaDatum) {
        mMetaDatum = metaDatum;
    }

    public MetaDatum getMetaDatum() {
        return mMetaDatum;
    }

    public void setData(Data data) {
        mData = data;
    }

    public Data getLotData() {
        return mData;
    }

    public class Data{
        private static final String FIELD_LOT = "lots";

        @SerializedName(FIELD_LOT)
        private LotDataModel mLotDataModel;


        public LotDataModel getLotDataModel() {
            return mLotDataModel;
        }

        public void setLotDataModel(LotDataModel lotDataModel) {
            this.mLotDataModel = lotDataModel;
        }
    }
}
