package com.example.blnsft.photos;

public class Photo {
    private String date;
    private int imageUrl;

    public Photo(String date, int imageUrl) {
        this.date = date;
        this.imageUrl = imageUrl;
    }

    public String getDate() {
        return date;
    }

    public int getImageUrl() {
        return imageUrl;
    }
}
