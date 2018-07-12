package com.example.blnsft.callbacks;

import android.support.v7.util.DiffUtil;
import com.example.blnsft.pojos.PhotoResponseModel;

public class PhotosDiffUtilCallback extends DiffUtil.ItemCallback<PhotoResponseModel>{

    @Override
    public boolean areItemsTheSame(PhotoResponseModel oldPhotoResponseModel, PhotoResponseModel newPhotoResponseModel) {
        return oldPhotoResponseModel.getId().equals(newPhotoResponseModel.getId());
    }

    @Override
    public boolean areContentsTheSame(PhotoResponseModel oldPhotoResponseModel, PhotoResponseModel newPhotoResponseModel) {
        return oldPhotoResponseModel.getDate().equals(newPhotoResponseModel.getDate())
                && oldPhotoResponseModel.getUrl().equals(newPhotoResponseModel.getUrl());
    }
}
