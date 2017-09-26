package com.devsenses.minebea.model.partmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pong.p on 3/31/2016.
 */
public class LotDataModel {
    private static final String FIELD_INPUT_LOT_NUMBER = "input_lot_number";
    private static final String FIELD_FIRST_SERIAL_NO = "first_serial_no";
    private static final String FIELD_LOT_DATA = "lot_data";

    @SerializedName(FIELD_INPUT_LOT_NUMBER)
    private boolean isTypingLot;
    @SerializedName(FIELD_FIRST_SERIAL_NO)
    private String firstSerialNo;
    @SerializedName(FIELD_LOT_DATA)
    private List<LotData> lotDataList;

    public boolean isTypingLot() {
        return isTypingLot;
    }

    public void setIsTypingLot(boolean isTypingLot) {
        this.isTypingLot = isTypingLot;
    }

    public String getFirstSerialNo() {
        return firstSerialNo != null ? firstSerialNo : "";
    }

    public void setFirstSerialNo(String firstSerialNo) {
        this.firstSerialNo = firstSerialNo;
    }

    public List<LotData> getLotDataList() {
        return lotDataList;
    }

    public void setLotDataList(List<LotData> lotDataList) {
        this.lotDataList = lotDataList;
    }
}
