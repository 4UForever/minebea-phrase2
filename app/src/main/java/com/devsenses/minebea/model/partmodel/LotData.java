package com.devsenses.minebea.model.partmodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pong.p on 3/31/2016.
 */
public class LotData {
    private static final String FIELD_ID = "id";
    private static final String FIELD_NUMBER = "number";
    private static final String FIELD_FIRST_SERIAL_NO = "first_serial_no";

    @SerializedName(FIELD_ID)
    private long id;
    @SerializedName(FIELD_NUMBER)
    private String number;
    @SerializedName(FIELD_FIRST_SERIAL_NO)
    private String firstSerialNo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFirstSerialNo() {
        return firstSerialNo != null ? firstSerialNo : "";
    }

    public void setFirstSerialNo(String firstSerialNo) {
        this.firstSerialNo = firstSerialNo;
    }
}
