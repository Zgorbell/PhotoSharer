package com.example.blnsft.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.blnsft.data.db.model.Photo;
import com.example.blnsft.data.db.room.PhotoDao;

@Database(entities = {Photo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PhotoDao photoDao();

}