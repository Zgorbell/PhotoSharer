package com.example.blnsft.callbacks;

import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.blnsft.App;
import com.example.blnsft.data.db.model.Photo;
import com.example.blnsft.data.network.models.PhotoResponseError;
import com.example.blnsft.data.network.models.PhotoResponseModel;
import com.example.blnsft.data.network.models.PhotoArrayResponseOk;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoResponseCallback implements Callback<PhotoArrayResponseOk> {
    public static final String TAG = PhotoResponseCallback.class.getSimpleName();
    private int page;
    private PositionalDataSource.LoadInitialCallback<Photo> loadInitialCallback;
    private PositionalDataSource.LoadRangeCallback<Photo> loadRangeCallback;

    public PhotoResponseCallback(int page, PositionalDataSource.LoadInitialCallback<Photo> callback) {
        this.page = page;
        loadInitialCallback = callback;
    }

    public PhotoResponseCallback(int page, PositionalDataSource.LoadRangeCallback<Photo> callback) {
        this.page = page;
        loadRangeCallback = callback;
    }

    public PhotoResponseCallback(int page) {
        this.page = page;
    }

    @Override
    public void onResponse(@NonNull Call<PhotoArrayResponseOk> call,
                           @NonNull Response<PhotoArrayResponseOk> response) {
        if (response.body() != null) {
            PhotoArrayResponseOk photoArrayResponseOk = response.body();
            Log.e(TAG, "downloading photos return ok");
            Executors.newFixedThreadPool(4).execute(new PhotoInsertDatabaseRunnable(
                    Arrays.asList(photoArrayResponseOk.getPhotoResponseModels()),
                    page));

        } else {
            try {
                Gson gson = new GsonBuilder().create();
                Log.e(TAG, "downloading photos return error");
                Log.e(TAG, response.errorBody().string());
                PhotoResponseError accountResponseError =
                        gson.fromJson(response.errorBody().string(), PhotoResponseError.class);
            } catch (IOException e) {
                Log.e(TAG, "downloading returns null");
            }
        }
    }

    @Override
    public void onFailure(@NonNull Call<PhotoArrayResponseOk> call, @NonNull Throwable t) {
        Log.e(TAG, "response failure");
    }

    public class PhotoInsertDatabaseRunnable implements Runnable{
        final String TAG = PhotoInsertDatabaseRunnable.class.getSimpleName();
        private List<PhotoResponseModel> list;
        private int page;

        PhotoInsertDatabaseRunnable(List<PhotoResponseModel> list, int page) {
            Log.e(TAG, "photo insert database started");
            this.list = list;
            this.page = page;
        }

        @Override
        public void run() {
            Log.e(TAG, "photo insert database run");
            App.getInstance().getDatabase().photoDao()
                    .insertPage(page, new PhotoResponseAdapter()
                            .adapt(list, page));
            List<Photo> photos = App.getInstance().getDatabase().photoDao().getPageList(page);
            if(loadInitialCallback != null) loadInitialCallback.onResult(photos, 0);
            else if(loadRangeCallback != null) loadRangeCallback.onResult(photos);
            if(photos != null) Log.e(TAG, "Photos page " + page + " return true" );
        }
    }
}
