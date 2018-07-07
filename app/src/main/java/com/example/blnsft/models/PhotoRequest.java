package com.example.blnsft.models;

public class PhotoRequest {
    private String base64Image;
    private String date;
    private String lat;
    private String lng;

    public PhotoRequest(String date, String base64Image, String lat, String lng) {
        this.date = date;
        this.base64Image = base64Image;
        this.lat = lat;
        this.lng = lng;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "ClassPojo [lng = " + lng + ", date = " + date + ", base64Image = " + base64Image + ", lat = " + lat + "]";
    }
}