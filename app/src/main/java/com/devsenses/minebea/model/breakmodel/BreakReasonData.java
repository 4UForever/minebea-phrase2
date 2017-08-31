package com.devsenses.minebea.model.breakmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pong.p on 2/2/2016.
 */
public class BreakReasonData {

    private static final String FIELD_BREAKS = "breaks";

    @SerializedName(FIELD_BREAKS)
    private List<BreakReason> mBreakReasonList;

    public BreakReasonData() {}

    public void setBreakReason(List<BreakReason> list){mBreakReasonList = list;}
    public List<BreakReason> getBreakReason(){ return mBreakReasonList;}

}
