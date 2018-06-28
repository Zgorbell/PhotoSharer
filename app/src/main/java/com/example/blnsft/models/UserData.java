package com.example.blnsft.models;

import java.io.Serializable;

public class UserData implements Serializable
{
    private String token;

    private String userId;

    private String login;

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
}