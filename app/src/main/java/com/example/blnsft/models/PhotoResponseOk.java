package com.example.blnsft.models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class PhotoResponseOk
{
    private String status;
    @SerializedName("data")
    private Photo[] photos;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public Photo[] getPhotos() {
        return photos;
    }

    public void setPhotos(Photo[] photos) {
        this.photos = photos;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+ status +", data = "+ Arrays.toString(photos) +"]";
    }
}
