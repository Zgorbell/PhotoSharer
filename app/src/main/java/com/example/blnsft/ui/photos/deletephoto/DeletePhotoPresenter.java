package com.example.blnsft.ui.photos.deletephoto;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.blnsft.App;
import com.example.blnsft.ui.base.BaseRepository;

import javax.inject.Inject;

@InjectViewState
public class DeletePhotoPresenter extends MvpPresenter<DeletePhotoView>
        implements DeletePhotoPresenterInterface{
    private static final String TAG = DeletePhotoPresenter.class.getSimpleName();
    @Inject
    BaseRepository baseRepository;

    DeletePhotoPresenter() {
        App.getInstance().getNetComponent().injectMvpPresenter(this);
    }

    @Override
    public void deleteOnClicked(String id) {
        baseRepository.deletePhoto(id);
    }
}
