package com.example.blnsft.data.db.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

@Entity
public class Photo {

    @PrimaryKey(autoGenerate = true)
    private long localId;
    private String id;
    private String path;
    private String date;
    private String lat;
    private String lng;
    private int page;

    public Photo(String id, String path, String date, String lat, String lng, int page) {
        this.id = id;
        this.path = path;
        this.date = date;
        this.lat = lat;
        this.lng = lng;
        this.page = page;
    }

    public long getLocalId() {
        return localId;
    }

    public void setLocalId(long localId) {
        this.localId = localId;
    }

    public String getId() {
        return id;
    }

    public int getPage() {
        return page;
    }

    public String getPath(){return path;
    }

    public String getDate() {
        return date;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }
}
