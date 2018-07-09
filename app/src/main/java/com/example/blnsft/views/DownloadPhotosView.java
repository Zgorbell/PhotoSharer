package com.example.blnsft.views;

import com.arellomobile.mvp.MvpView;
import com.example.blnsft.models.Photo;
import java.util.List;

public interface DownloadPhotosView extends MvpView{
    void addPhotos(List<Photo> photoList);
}
