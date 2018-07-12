package com.example.blnsft.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.blnsft.photos.Photo;

import java.util.List;

@Dao
public interface PhotoDao {

    @Query("Select * from photo where id > :idStart order by id limit :count ")
    List<Photo> get(String idStart, int count);

    @Insert
    void insert(Photo photo);
}
