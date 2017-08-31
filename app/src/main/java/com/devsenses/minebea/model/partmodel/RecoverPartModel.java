package com.devsenses.minebea.model.partmodel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pong.p on 3/31/2016.
 */
public class RecoverPartModel implements Parcelable {

    private List<Part> parts;
    private List<WIP> wips;

    public RecoverPartModel(Parcel in) {
        parts = new ArrayList<>();
        in.readTypedList(parts, Part.CREATOR);
        wips = new ArrayList<>();
        in.readTypedList(wips, WIP.CREATOR);
    }

    public RecoverPartModel(List<Part> parts, List<WIP> wips) {
        this.parts = parts;
        this.wips = wips;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    public List<WIP> getWips() {
        return wips;
    }

    public void setWips(List<WIP> wips) {
        this.wips = wips;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecoverPartModel> CREATOR = new Creator<RecoverPartModel>() {
        public RecoverPartModel createFromParcel(Parcel in) {
            return new RecoverPartModel(in);
        }

        public RecoverPartModel[] newArray(int size) {
            return new RecoverPartModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(parts);
        dest.writeTypedList(wips);
    }

    @Override
    public String toString() {
        return "part : "+parts.toString()+" wip : "+wips.toString();
    }
}
