package com.devsenses.minebea.model.loginmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class LoginData implements Parcelable{

    private static final String FIELD_ID = "id";
    private static final String FIELD_LAST_LOGIN = "last_login";
    private static final String FIELD_LAST_LOGOUT = "last_logout";
    private static final String FIELD_FIRST_NAME = "first_name";
    private static final String FIELD_LAST_NAME = "last_name";
    private static final String FIELD_EMAIL = "email";
    private static final String FIELD_LEADER = "leader";
    private static final String FIELD_ON_PROCESS_ID = "on_process";
    private static final String FIELD_CREATED_AT = "created_at";
    private static final String FIELD_UPDATED_AT = "updated_at";
    private static final String FIELD_GROUPS = "groups";
    private static final String FIELD_PERMISSION = "permissions";
    private static final String FIELD_MODELS = "models";


    @SerializedName(FIELD_LAST_LOGIN)
    private String mLastLogin;
    @SerializedName(FIELD_ID)
    private long mId;
    @SerializedName(FIELD_FIRST_NAME)
    private String mFirstName;
    @SerializedName(FIELD_CREATED_AT)
    private String mCreatedAt;
    @SerializedName(FIELD_GROUPS)
    private List<Group> mGroups;
    @SerializedName(FIELD_MODELS)
    private List<Model> mModels;
    @SerializedName(FIELD_LAST_LOGOUT)
    private String mLastLogout;
    @SerializedName(FIELD_LAST_NAME)
    private String mLastName;
    @SerializedName(FIELD_UPDATED_AT)
    private String mUpdatedAt;
    @SerializedName(FIELD_EMAIL)
    private String mEmail;
    @SerializedName(FIELD_LEADER)
    private int mLeader;
    @SerializedName(FIELD_ON_PROCESS_ID)
    private Long mOnProcessId;
    @SerializedName(FIELD_PERMISSION)
    private UserPermission mUserPermission;



    public LoginData(){

    }

    public void setLastLogin(String lastLogin) {
        mLastLogin = lastLogin;
    }

    public String getLastLogin() {
        return mLastLogin;
    }

    public void setId(long id) {
        mId = id;
    }
    public long getId() {
        return mId;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }
    public String getFirstName() {
        return mFirstName;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }
    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setGroups(List<Group> groups) {
        mGroups = groups;
    }
    public List<Group> getGroups() {
        return mGroups;
    }

    public void setModels(List<Model> models) {
        mModels = models;
    }
    public List<Model> getModels() {
        return mModels;
    }

    public void setLastLogout(String lastLogout) {
        mLastLogout = lastLogout;
    }
    public String getLastLogout() {
        return mLastLogout;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }
    public String getLastName() {
        return mLastName;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }
    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setEmail(String email) {
        mEmail = email;
    }
    public String getEmail() {
        return mEmail;
    }

    public void setLeader(int isLeader){mLeader = isLeader;}
    public int getLeader(){return mLeader;}

    public void setOnProcess(Long onProcessId){mOnProcessId = onProcessId;}
    public Long getOnProcess(){return mOnProcessId;}

    public void setUserPermission(UserPermission userPermission){mUserPermission = userPermission;}

    public UserPermission getUserPermission(){return mUserPermission;}

    @Override
    public boolean equals(Object obj){
        if(obj instanceof LoginData){
            return ((LoginData) obj).getId() == mId;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return ((Long)mId).hashCode();
    }

    public LoginData(Parcel in) {
        mLastLogin = in.readString();
        mId = in.readLong();
        mFirstName = in.readString();
        mCreatedAt = in.readString();
        mGroups = new ArrayList<>();
        in.readTypedList(mGroups, Group.CREATOR);
        mModels = new ArrayList<>();
        in.readTypedList(mModels, Model.CREATOR);
        mLastLogout = in.readString();
        mLastName = in.readString();
        mUpdatedAt = in.readString();
        mEmail = in.readString();
        mLeader = in.readInt();
        mUserPermission = in.readParcelable(UserPermission.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoginData> CREATOR = new Creator<LoginData>() {
        public LoginData createFromParcel(Parcel in) {
            return new LoginData(in);
        }

        public LoginData[] newArray(int size) {
        return new LoginData[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mLastLogin);
        dest.writeLong(mId);
        dest.writeString(mFirstName);
        dest.writeString(mCreatedAt);
        dest.writeTypedList(mGroups);
        dest.writeTypedList(mModels);
        dest.writeString(mLastLogout);
        dest.writeString(mLastName);
        dest.writeString(mUpdatedAt);
        dest.writeString(mEmail);
        dest.writeInt(mLeader);
    }

    @Override
    public String toString(){
        return "lastLogin = " + mLastLogin + ", id = " + mId + ", firstName = " + mFirstName + ", createdAt = " + mCreatedAt + ", groups = " + mGroups + ", models = " + mModels + ", lastLogout = " + mLastLogout + ", lastName = " + mLastName + ", updatedAt = " + mUpdatedAt + ", email = " + mEmail;
    }


}