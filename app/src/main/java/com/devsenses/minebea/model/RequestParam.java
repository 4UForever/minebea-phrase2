package com.devsenses.minebea.model;

import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import android.os.Parcel;


public class RequestParam implements Parcelable{

    private static final String FIELD_QR_CODE = "qr_code";


    @SerializedName(FIELD_QR_CODE)
    private String mQrCode;


    public RequestParam(){

    }

    public void setQrCode(String qrCode) {
        mQrCode = qrCode;
    }

    public String getQrCode() {
        return mQrCode;
    }

    public RequestParam(Parcel in) {
        mQrCode = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RequestParam> CREATOR = new Creator<RequestParam>() {
        public RequestParam createFromParcel(Parcel in) {
            return new RequestParam(in);
        }

        public RequestParam[] newArray(int size) {
        return new RequestParam[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mQrCode);
    }

    @Override
    public String toString(){
        return "qrCode = " + mQrCode;
    }


}