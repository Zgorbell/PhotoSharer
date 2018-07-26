package com.example.blnsft.data.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class PhotoArrayResponseOk
{
    private String status;

    @SerializedName("data")
    private PhotoResponseModel[] photoResponseModels;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public PhotoResponseModel[] getPhotoResponseModels() {
        return photoResponseModels;
    }

    public void setPhotoResponseModels(PhotoResponseModel[] photoResponseModels) {
        this.photoResponseModels = photoResponseModels;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+ status +", data = "+ Arrays.toString(photoResponseModels) +"]";
    }
}
