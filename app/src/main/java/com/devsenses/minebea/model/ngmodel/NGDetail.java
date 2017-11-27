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
    private static final String FIELD_QUANTITY = "quantity";

    @SerializedName(FIELD_NG)
    private NG ng;
    @SerializedName(FIELD_SERIAL_NO)
    private String serialNo;
    @SerializedName(FIELD_QUANTITY)
    private String quantity;

    public NG getNg() {
        return ng;
    }

    public void setNg(NG ng) {
        this.ng = ng;
    }

    public String getQuantity() {
        return quantity != null ? quantity : "1";
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSerialNo() {
        return serialNo != null ? serialNo : "";
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    @Override
    public String toString() {
        return "NGDetail{" +
                "ng=" + getNg() +
                "serialNo=" + getSerialNo() +
                ", quantity='" + getQuantity() + '\'' +
                '}';
    }
}
