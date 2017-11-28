package com.devsenses.minebea.model.ngmodel;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by User on 8/9/2560.
 */

@Parcel(Parcel.Serialization.BEAN)
public class NGDetail {
    private static final String FIELD_NG = "ng";
    private static final String FIELD_SERIAL_NO = "serial_no";
    private static final String FIELD_IS_LOCK_AT_FINISH = "is_lock_at_finish";

    @SerializedName(FIELD_NG)
    private NG ng;
    @SerializedName(FIELD_SERIAL_NO)
    private String serialNo;
    @SerializedName(FIELD_IS_LOCK_AT_FINISH)
    private boolean isLockAtFinish;

    public NG getNg() {
        return ng;
    }

    public void setNg(NG ng) {
        this.ng = ng;
    }

    public String getSerialNo() {
        return serialNo != null ? serialNo : "";
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public boolean isLockAtFinish() {
        return isLockAtFinish;
    }

    public void setLockAtFinish(boolean lockAtFinish) {
        this.isLockAtFinish = lockAtFinish;
    }

    @Override
    public String toString() {
        return "NGDetail{" +
                "ng=" + getNg() +
                "serialNo=" + getSerialNo() +
                ", isLockAtFinish='" + isLockAtFinish + '\'' +
                '}';
    }
}
