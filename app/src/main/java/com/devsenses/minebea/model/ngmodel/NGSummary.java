package com.devsenses.minebea.model.ngmodel;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by USER on 9/9/2560.
 */

@Parcel(Parcel.Serialization.BEAN)
public class NGSummary {
    private static final String FIELD_NG = "ng";
    private static final String FIELD_SERIAL_NO = "serial_no";
    private static final String FIELD_NG_1 = "ng1";
    private static final String FIELD_NG_2 = "ng2";

    @SerializedName(FIELD_NG)
    private NG ng;
    @SerializedName(FIELD_SERIAL_NO)
    private String serialNo;
    @SerializedName(FIELD_NG_1)
    private boolean ng1;
    @SerializedName(FIELD_NG_2)
    private boolean ng2;

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

    public boolean getNg1() {
        return ng1;
    }

    public void setNg1(boolean ng1) {
        this.ng1 = ng1;
    }

    public boolean getNg2() {
        return ng2;
    }

    public void setNg2(boolean ng2) {
        this.ng2 = ng2;
    }

    @Override
    public String toString() {
        return "NGSummary{" +
                "ng=" + ng.getTitle() +
                ", serial=" + getSerialNo() +
                ", ng1='" + ng1 +
                ", ng2='" + ng2 + '\'' +
                '}';
    }
}
