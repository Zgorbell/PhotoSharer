package com.example.blnsft;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.blnsft.room.AppDatabase;

public class App extends Application {
    public static App instance;
    private static final String DATABASE_NAME = "database";
    public static final String BASEURL = "http://junior.balinasoft.com";

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
