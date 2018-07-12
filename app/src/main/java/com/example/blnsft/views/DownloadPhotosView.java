package com.example.blnsft.views;

import com.arellomobile.mvp.MvpView;
import com.example.blnsft.pojos.PhotoResponseModel;
import java.util.List;

public interface DownloadPhotosView extends MvpView{
    void addPhotos(List<PhotoResponseModel> photoResponseModelList);
}
