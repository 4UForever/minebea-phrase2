package com.devsenses.minebea.model.loginmodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by USER on 16/12/2560.
 */

public class ContinueData {

    private static final String FIELD_PROCESS_LOG_FROM = "process_log_from";
    private static final String FIELD_LINE_ID = "line_id";
    private static final String FIELD_LINE_TITLE = "line_title";
    private static final String FIELD_PRODUCT_ID = "product_id";
    private static final String FIELD_PRODUCT_TITLE= "product_title";
    private static final String FIELD_PROCESS_ID = "process_id";
    private static final String FIELD_PROCESS_NUMBER = "process_number";
    private static final String FIELD_PROCESS_TITLE= "process_title";
    private static final String FIELD_WIP_ID = "wip_id";
    private static final String FIELD_WIP_SORT= "wip_sort";
    private static final String FIELD_LOT_ID = "lot_id";
    private static final String FIELD_LOT_NUMBER = "lot_number";
    private static final String FIELD_LINE_LEADER = "line_leader";

    @SerializedName(FIELD_PROCESS_LOG_FROM)
    private long processLogFrom;
    @SerializedName(FIELD_LINE_ID)
    private String lineId;
    @SerializedName(FIELD_LINE_TITLE)
    private String lineTitle;
    @SerializedName(FIELD_PRODUCT_ID)
    private String productId;
    @SerializedName(FIELD_PRODUCT_TITLE)
    private String productTitle;
    @SerializedName(FIELD_PROCESS_ID)
    private String processId;
    @SerializedName(FIELD_PROCESS_NUMBER)
    private String processNumber;
    @SerializedName(FIELD_PROCESS_TITLE)
    private String processTitle;
    @SerializedName(FIELD_WIP_ID)
    private String wipId;
    @SerializedName(FIELD_WIP_SORT)
    private String wipSort;
    @SerializedName(FIELD_LOT_ID)
    private String lotId;
    @SerializedName(FIELD_LOT_NUMBER)
    private String lotNumber;
    @SerializedName(FIELD_LINE_LEADER)
    private String lineLeader;

    public long getProcessLogFrom() {
        return processLogFrom;
    }

    public void setProcessLogFrom(long processLogFrom) {
        this.processLogFrom = processLogFrom;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getLineTitle() {
        return lineTitle;
    }

    public void setLineTitle(String lineTitle) {
        this.lineTitle = lineTitle;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessNumber() {
        return processNumber;
    }

    public void setProcessNumber(String processNumber) {
        this.processNumber = processNumber;
    }

    public String getProcessTitle() {
        return processTitle;
    }

    public void setProcessTitle(String processTitle) {
        this.processTitle = processTitle;
    }

    public String getWipId() {
        return wipId;
    }

    public void setWipId(String wipId) {
        this.wipId = wipId;
    }

    public String getWipSort() {
        return wipSort;
    }

    public void setWipSort(String wipSort) {
        this.wipSort = wipSort;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getLineLeader() {
        return lineLeader;
    }

    public void setLineLeader(String lineLeader) {
        this.lineLeader = lineLeader;
    }
}
