package com.example.blnsft.pojos;

import android.os.Parcel;
import android.os.Parcelable;

public class UserData implements Parcelable
{
    public final static String USER_DATA = "User data";
    private String token;
    private String userId;
    private String login;

    public UserData(Parcel in) {
        this.token = in.readString();
        this.userId = in.readString();
        this.login = in.readString();
    }

    public String getToken ()
    {
        return token;
    }

    public void setToken (String token)
    {
        this.token = token;
    }

    public String getUserId ()
    {
        return userId;
    }

    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    public String getLogin ()
    {
        return login;
    }

    public void setLogin (String login)
    {
        this.login = login;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [token = "+token+", userId = "+userId+", login = "+login+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
        dest.writeString(userId);
        dest.writeString(login);
    }

    public static final Parcelable.Creator<UserData> CREATOR = new Parcelable.Creator<UserData>() {
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };
}