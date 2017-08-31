package com.devsenses.minebea.model.partmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pong.p on 12/18/2015.
 */
public class Part implements Parcelable {
    private static final String FIELD_ID = "id";
    private static final String FIELD_PRODUCT_ID = "product_id";
    private static final String FIELD_PROCESS_ID = "process_id";
    private static final String FIELD_NUMBER = "number";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_CREATED_AT = "created_at";
    private static final String FIELD_UPDATED_AT = "updated_at";
    private static final String FIELD_IQC_LOT = "iqc_lots";

    @SerializedName(FIELD_ID)
    private long mId;
    @SerializedName(FIELD_PRODUCT_ID)
    private long mProductId;
    @SerializedName(FIELD_PROCESS_ID)
    private long mProcessId;
    @SerializedName(FIELD_NUMBER)
    private String mNumber;
    @SerializedName(FIELD_NAME)
    private String mName;
    @SerializedName(FIELD_CREATED_AT)
    private String mCreatedAt;
    @SerializedName(FIELD_UPDATED_AT)
    private String mUpdatedAt;
    @SerializedName(FIELD_IQC_LOT)
    private List<LotNo> mIQC;

    public void setId(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public void setProductId(long productID) {
        mProductId = productID;
    }

    public long getProductId() {
        return mProductId;
    }

    public void setProcessId(long processID) {
        mProcessId = processID;
    }

    public long getProcessId() {
        return mProcessId;
    }

    public void setNumber(String number) {
        mNumber = number;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setCreateAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getCreateAt() {
        return mCreatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setIQC(List<LotNo> iqc) {
        mIQC = iqc;
    }

    public List<LotNo> getIQC() {
        return mIQC;
    }

    public Part() {
    }

    public Part(Parcel in) {
        mId = in.readLong();
        mProductId = in.readLong();
        mProcessId = in.readLong();
        mNumber = in.readString();
        mName = in.readString();
        mCreatedAt = in.readString();
        mUpdatedAt = in.readString();
        mIQC = new ArrayList<>();
        in.readTypedList(mIQC, LotNo.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Part> CREATOR = new Parcelable.Creator<Part>() {
        public Part createFromParcel(Parcel in) {
            return new Part(in);
        }

        public Part[] newArray(int size) {
            return new Part[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeLong(mProductId);
        dest.writeLong(mProcessId);
        dest.writeString(mNumber);
        dest.writeString(mName);
        dest.writeString(mCreatedAt);
        dest.writeString(mUpdatedAt);
        dest.writeTypedList(mIQC);
    }

    @Override
    public String toString() {
        return toJSON().toString();
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", getId());
            jsonObject.put("number", getNumber());

            JSONArray array = new JSONArray();
            List<LotNo> iqc = getIQC();
            if (iqc != null && iqc.size() > 0) {
                for (int i = 0; i < iqc.size(); i++) {
                    array.put(iqc.get(i).toJSON());
                }
            }

            jsonObject.put("iqc_lots", array);
            return jsonObject;
        } catch (JSONException e) {
            return jsonObject;
        }
    }

}
