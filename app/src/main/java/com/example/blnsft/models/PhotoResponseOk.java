package com.example.blnsft.models;

import com.google.gson.annotations.SerializedName;

public class PhotoResponseOk
{
    private String status;
    @SerializedName("data")
    private Photo photo;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+ status +", data = "+photo.toString()+"]";
    }
}
