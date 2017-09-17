package com.devsenses.minebea.model.breakmodel;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by USER on 14/9/2560.
 */

@Parcel(Parcel.Serialization.BEAN)
public class BreakStep {
    private static final String FIELD_BREAK_ID = "break_id";
    private static final String FIELD_BREAK_FLAG = "break_flag";
    private static final String FIELD_START_BREAK = "start_break";
    private static final String FIELD_END_BREAK = "end_break";

    @SerializedName(FIELD_BREAK_ID)
    private long breakId;
    @SerializedName(FIELD_BREAK_FLAG)
    private String breakFlag;
    @SerializedName(FIELD_START_BREAK)
    private String startBreak;
    @SerializedName(FIELD_END_BREAK)
    private String endBreak;

    public long getBreakId() {
        return breakId;
    }

    public void setBreakId(long breakId) {
        this.breakId = breakId;
    }

    public String getBreakFlag() {
        return breakFlag;
    }

    public void setBreakFlag(String breakFlag) {
        this.breakFlag = breakFlag;
    }

    public String getStartBreak() {
        return startBreak != null ? startBreak : "";
    }

    public void setStartBreak(String startBreak) {
        this.startBreak = startBreak;
    }

    public String getEndBreak() {
        return endBreak != null ? endBreak : "";
    }

    public void setEndBreak(String endBreak) {
        this.endBreak = endBreak;
    }
}
