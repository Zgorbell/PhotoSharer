package com.example.blnsft;


import android.content.SharedPreferences;
import android.util.Log;

import com.example.blnsft.models.UserData;
import com.google.gson.Gson;

public class UserSessionHelper {
    private final static String AUTH_USER = "Auth user";
    private SharedPreferences preferences;

    UserSessionHelper(SharedPreferences sharedPreferences){
        this.preferences = sharedPreferences;
    }

    public void saveSession(UserData userData){
        Log.e("Save session", userData.toString());
        preferences.edit().putString(AUTH_USER, new Gson().toJson(userData)).apply();
    }

    public UserData getSession(){
        Log.e("Get session", preferences.getString(AUTH_USER, ""));
        return new Gson().fromJson(preferences.getString(AUTH_USER, ""), UserData.class);
    }

    public void deleteSession(){
        preferences.edit().remove(AUTH_USER).apply();
    }

    public boolean checkSession(){
        return preferences.contains(AUTH_USER);
    }

}
