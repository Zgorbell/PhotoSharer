package com.example.blnsft.ui.photos;

import android.arch.paging.PositionalDataSource;

import com.example.blnsft.data.db.model.Photo;

public interface PhotosPresenterInterface {

    void onContextItemSelected(String id);

    void onLoadInitial(PositionalDataSource.LoadInitialCallback<Photo> callback);

    void onLoadRange(int page, PositionalDataSource.LoadRangeCallback<Photo> callback);

    void onRefreshed();
}
