package com.example.blnsft.data.db.room;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.util.Log;

import com.example.blnsft.data.db.model.Photo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;


@Dao
public abstract class PhotoDao {
    static final String TAG = PhotoDao.class.getSimpleName();

    @Query("Select * from photo ")
    public abstract List<Photo> getList();

    @Query("Select * from photo where page = :page order by date desc")
    public abstract List<Photo> getPageList(int page);

    @Query("Select * from photo ")
    public abstract LiveData<List<Photo>> getLiveList();

    @Query("Select * from photo where id = :photoId")
    public abstract Photo getPhoto(String photoId);

    @Insert
    public abstract void insert(List<Photo> photos);

    @Insert
    public abstract void insert(Photo photo);

    @Delete
    abstract void deleteList(List<Photo> photos);

    @Delete
    public abstract void delete(Photo photo);

    @Transaction
    public void deleteAllPhotos(){
        deleteTempFiles(getList());
        deleteList(getList());
        Log.e(TAG, "delete all from db, db size " + getList().size());
    }

    @Transaction
    public void deletePhoto(String id){
        Photo photo = getPhoto(id);
        if(photo == null) Log.e(TAG, "photo from db is null");
        else {
            new File(photo.getPath()).delete();
            delete(photo);
        }
    }

    @Transaction
    public void insertPage(int page, List<Photo> photos){
        deleteTempFiles(getPageList(page));
        deleteList(getPageList(page));
        insert(photos);
    }

    private void deleteTempFiles(List<Photo> photos){
        for(Photo photo: photos) new File(photo.getPath()).delete();
    }

}
