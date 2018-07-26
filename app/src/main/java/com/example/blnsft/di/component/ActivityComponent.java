package com.example.blnsft.di.component;

import com.example.blnsft.ui.main.MainActivity;
import com.example.blnsft.ui.main.MainPresenter;
import com.example.blnsft.ui.photos.PhotosPresenter;
import com.example.blnsft.ui.photos.deletephoto.DeletePhotoPresenter;

interface ActivityComponent {

    void injectMvpPresenter(PhotosPresenter presenter);

    void injectMvpPresenter(MainPresenter presenter);

    void injectMvpPresenter(DeletePhotoPresenter presenter);

    void injectMvpActivity(MainActivity mainActivity);
}
