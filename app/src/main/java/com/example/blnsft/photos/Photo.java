package com.example.blnsft.photos;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Photo {
    @PrimaryKey
    @NonNull
    private String id;
    private String base64Image;
    private String date;
    private String lat;
    private String lng;

    public Photo(String id, String base64Image, String date, String lat, String lng) {
        this.id = id;
        this.base64Image = base64Image;
        this.date = date;
        this.lat = lat;
        this.lng = lng;
    }

    public String getId() {
        return id;
    }

    public String getBase64Image() {
        return base64Image;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
