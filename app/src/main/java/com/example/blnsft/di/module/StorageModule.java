package com.example.blnsft.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.blnsft.data.db.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class StorageModule {
    private String databaseName;

    public StorageModule(String databaseName) {
        this.databaseName = databaseName;
    }

    @Provides
    @Singleton
    AppDatabase provideDatabase(Context context){
        return Room.databaseBuilder(context, AppDatabase.class, databaseName)
                .build();
    }


}
