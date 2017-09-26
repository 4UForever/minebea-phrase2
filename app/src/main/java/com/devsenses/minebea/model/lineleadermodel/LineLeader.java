package com.devsenses.minebea.model.lineleadermodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pong.p on 12/24/2015.
 */
public class LineLeader {
    private static final String FIELD_ID = "id";
    private static final String FIELD_EMAIL = "email";
    private static final String FIELD_LAST_LOGIN = "last_login";
    private static final String FIELD_LAST_LOGOUT = "last_logout";
    private static final String FIELD_FIRST_NAME = "first_name";
    private static final String FIELD_LAST_NAME = "last_name";
    private static final String FIELD_CREATED_AT = "created_at";
    private static final String FIELD_UPDATED_AT = "updated_at";


    @SerializedName(FIELD_ID)
    private long mId;
    @SerializedName(FIELD_EMAIL)
    private String mEmail;
    @SerializedName(FIELD_LAST_LOGIN)
    private String mLastLogin;
    @SerializedName(FIELD_LAST_LOGOUT)
    private String mLastLogout;
    @SerializedName(FIELD_FIRST_NAME)
    private String mFirstName;
    @SerializedName(FIELD_LAST_NAME)
    private String mLastName;
    @SerializedName(FIELD_CREATED_AT)
    private String mCreatedAt;
    @SerializedName(FIELD_UPDATED_AT)
    private String mUpdatedAt;

    public LineLeader(){

    }

    public void setId(long id)  {   mId = id;   }
    public long getId() {   return mId; }

    public void setEmail(String email)  {   mEmail = email;   }
    public String getEmail() { return mEmail; }

    public void setLastLogin(String lastLogin)  {   mLastLogin = lastLogin;   }
    public String getLastLogin() { return mLastLogin; }

    public void setLastLogout(String lastLogout)  {   mLastLogout = lastLogout;   }
    public String getLastLogout() { return mLastLogout; }

    public void setFirstName(String firstName)  {   mFirstName = firstName;   }
    public String getFirstName() { return mFirstName; }

    public void setLastName(String lastName)  {   mLastName = lastName;   }
    public String getLastName() { return mLastName; }

    public String getFullName(){
        return mFirstName+" "+mLastName;
    }

    public void setCreatedAt(String createdAt)  {   mCreatedAt = createdAt;   }
    public String getCreatedAt() { return mCreatedAt; }

    public void setUpdatedAt(String updatedAt)  {   mUpdatedAt = updatedAt;   }
    public String getUpdatedAt() { return mUpdatedAt; }

    @Override
    public String toString() {
        return "[id="+mId+" name="+mFirstName+" "+mLastName+" email="+mEmail+"]";
    }
}
