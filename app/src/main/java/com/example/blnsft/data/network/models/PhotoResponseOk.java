package com.example.blnsft.data.network.models;

import com.google.gson.annotations.SerializedName;

public class PhotoResponseOk {
    private String status;

    @SerializedName("data")
    private PhotoResponseModel photoResponseModel;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public PhotoResponseModel getPhotoResponseModel() {
        return photoResponseModel;
    }

    public void setPhotoResponseModel(PhotoResponseModel photoResponseModel) {
        this.photoResponseModel = photoResponseModel;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+ status +", data = "+ photoResponseModel.toString() +"]";
    }
}
