package com.devsenses.minebea.model.loginmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pong.p on 2/9/2016.
 */
public class UserPermission implements Parcelable {
    private static final String FIELD_WORK = "work_on_model_processes";
    private static final String FIELD_VIEW = "view_documents";

    @SerializedName(FIELD_WORK)
    private int isWork;
    @SerializedName(FIELD_VIEW)
    private int isView;

    public boolean getIsWork() {
        return isWork == 1;
    }
    public void setIsWork(int isWork) {
        this.isWork = isWork;
    }

    public boolean getIsView() {
        return isView == 1;
    }
    public void setIsView(int isView) {
        this.isView = isView;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(isWork );
        dest.writeInt(isView);
    }

    public UserPermission() {
    }

    protected UserPermission(Parcel in) {
        this.isWork = in.readInt();
        this.isView = in.readInt();
    }

    public static final Parcelable.Creator<UserPermission> CREATOR = new Parcelable.Creator<UserPermission>() {
        public UserPermission createFromParcel(Parcel source) {
            return new UserPermission(source);
        }

        public UserPermission[] newArray(int size) {
            return new UserPermission[size];
        }
    };
}
