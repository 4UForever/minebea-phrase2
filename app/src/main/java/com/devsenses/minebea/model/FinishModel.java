package com.devsenses.minebea.model;

/**
 * Created by USER on 10/9/2560.
 */

public class FinishModel {
    private String qrCode;
    private int okQty;
    private String lastSerialNo;
    private int setup;
    private int dt;
    private String ngs;
    private String breaks;
    private String remark;

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public int getOkQty() {
        return okQty;
    }

    public void setOkQty(int okQty) {
        this.okQty = okQty;
    }

    public String getLastSerialNo() {
        return lastSerialNo;
    }

    public void setLastSerialNo(String lastSerialNo) {
        this.lastSerialNo = lastSerialNo;
    }

    public int getSetup() {
        return setup;
    }

    public void setSetup(int setup) {
        this.setup = setup;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public String getNgs() {
        return ngs != null ? ngs : "[]";
    }

    public void setNgs(String ngs) {
        this.ngs = ngs;
    }

    public String getBreaks() {
        return breaks != null ? breaks : "[]";
    }

    public void setBreaks(String breaks) {
        this.breaks = breaks;
    }

    public String getRemark() {
        return remark != null ? remark : "";
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
