package com.example.blnsft.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.blnsft.photos.Photo;

@Database(entities = {Photo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PhotoDao photoDao();
}