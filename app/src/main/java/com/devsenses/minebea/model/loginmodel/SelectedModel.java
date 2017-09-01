package com.devsenses.minebea.model.loginmodel;

/**
 * Created by pong.p on 1/11/2016.
 */
public class SelectedModel {
    private long mId;
    private String mTitles;
    private long mLineID;
    private String mLineTitle;
    private long mProcessID;
    private String mProcessTitle;
    private String mProcessNumber;
    private Long mOnBreak;
    private String mLotNumber;
    private String mTime;

    public void setID(long id) {
        mId = id;
    }

    public long getID() {
        return mId;
    }

    public void setTitle(String title) {
        mTitles = title;
    }

    public String getTitle() {
        return mTitles;
    }

    public void setLineID(long lineID) {
        mLineID = lineID;
    }

    public long getLineID() {
        return mLineID;
    }

    public void setLineTitle(String lineTitle) {
        mLineTitle = lineTitle;
    }

    public String getLineTitle() {
        return mLineTitle;
    }

    public void setProcessID(long processID) {
        mProcessID = processID;
    }

    public long getProcessID() {
        return mProcessID;
    }

    public void setProcessTitle(String processTitle) {
        mProcessTitle = processTitle;
    }

    public String getProcessTitle() {
        return mProcessTitle;
    }

    public void setProcessNumber(String processNumber) {
        mProcessNumber = processNumber;
    }

    public String getProcessNumber() {
        return mProcessNumber;
    }

    public void setOnBreak(Long onBreak) {
        mOnBreak = onBreak;
    }

    public Long getOnBreak() {
        return mOnBreak;
    }

    public String getLotNumber() {
        return mLotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.mLotNumber = lotNumber;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getTime() {
        return mTime;
    }

    public SelectedModel initShift(Shift shift) {
        setID(shift.getId());
        setTime(shift.getTime());
        return this;
    }

    public SelectedModel initModel(Model model) {
        setID(model.getId());
        setTitle(model.getTitle());
        return this;
    }

    public SelectedModel initLine(Line line) {
        setLineID(line.getId());
        setLineTitle(line.getTitle());
        return this;
    }

    public SelectedModel initProcess(Process process) {
        setProcessID(process.getId());
        setProcessTitle(process.getTitle());
        setProcessNumber(process.getNumber());
        return this;
    }

    public SelectedModel initProcessLog(ProcessLog processLog) {
        setID(processLog.getData().getModelId());
        setTitle(processLog.getData().getModelTitle());

        setLineID(processLog.getData().getLineId());
        setLineTitle(processLog.getData().getLineTitle());

        setProcessID(processLog.getData().getProcessId());
        setProcessTitle(processLog.getData().getProcessTitle());
        setProcessNumber(processLog.getData().getProcessNumber());
        setLotNumber(processLog.getData().getLotNumber());

        setOnBreak(processLog.getData().getOnBreak());
        return this;
    }
}
