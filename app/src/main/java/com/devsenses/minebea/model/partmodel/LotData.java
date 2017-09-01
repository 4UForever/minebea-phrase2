package com.devsenses.minebea.model.partmodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pong.p on 3/31/2016.
 */
public class LotData {
    private static final String FIELD_ID = "id";
    private static final String FIELD_NUMBER = "number";

    @SerializedName(FIELD_ID)
    private long id;
    @SerializedName(FIELD_NUMBER)
    private String number;

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
}
