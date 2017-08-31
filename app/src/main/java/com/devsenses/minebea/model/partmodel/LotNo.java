package com.devsenses.minebea.model.partmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pong.p on 1/12/2016.
 */
public class LotNo implements Parcelable {
    private static final String FIELD_NUMBER = "number";
    private static final String FIELD_QUANTITY = "quantity";

    @SerializedName(FIELD_NUMBER)
    private String mNumber;
    @SerializedName(FIELD_QUANTITY)
    private long mQuantity;

    public void setQuantity(long quantity)  {   mQuantity = quantity;   }
    public long getQuantity() {   return mQuantity; }

    public String getNumber() { return mNumber; }
    public void setNumber(String mNumber) { this.mNumber = mNumber; }

    public LotNo(Parcel in) {
        mNumber = in.readString();
        mQuantity = in.readLong();
    }

    public LotNo() {}

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LotNo> CREATOR = new Creator<LotNo>() {
        public LotNo createFromParcel(Parcel in) {
            return new LotNo(in);
        }

        public LotNo[] newArray(int size) {
            return new LotNo[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mNumber);
        dest.writeLong(mQuantity);
    }

    @Override
    public String toString(){
        return toJSON().toString();
    }

    public JSONObject toJSON(){
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("number",getNumber());
            jsonObject.put("quantity",getQuantity());
            return jsonObject;
        }catch (JSONException e){
            return jsonObject;
        }
    }
}
