package com.example.blnsft.ui.base;


import android.support.annotation.NonNull;
import android.util.Log;

import com.example.blnsft.App;
import com.example.blnsft.callbacks.PhotoResponseAdapter;
import com.example.blnsft.data.db.model.Photo;
import com.example.blnsft.data.network.DeletePhotoApi;
import com.example.blnsft.data.network.DownloadPhotosApi;
import com.example.blnsft.data.network.UploadPhotoApi;
import com.example.blnsft.data.network.models.PhotoArrayResponseOk;
import com.example.blnsft.data.network.models.PhotoRequest;
import com.example.blnsft.data.network.models.PhotoResponseError;
import com.example.blnsft.data.network.models.PhotoResponseModel;
import com.example.blnsft.data.network.models.PhotoResponseOk;
import com.example.blnsft.ui.photos.PhotosPresenter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;


import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BaseRepository {
    private static final String TAG = BaseRepository.class.getSimpleName();

    private Retrofit retrofit;
    private PhotosPresenter photosPresenter;

    public BaseRepository(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public void setPhotosPresenter(PhotosPresenter photosPresenter) {
        this.photosPresenter = photosPresenter;
    }

    private void invalidatePhotoDataSource() {
        if (photosPresenter != null) photosPresenter.invalidate();
        else Log.e(TAG, "photo presenter is " + null);
    }

    public void uploadPhoto(PhotoRequest photoRequest, String token) {
        Log.e(TAG, "start execute photo request");
        UploadPhotoApi uploadPhotoApi = retrofit.create(UploadPhotoApi.class);
        Call<PhotoResponseOk> call = uploadPhotoApi.uploadImage(photoRequest, token);
        call.enqueue(new PhotoResponseUploadCallback());
    }

    public void deletePhoto(String id) {
        Log.e(TAG, "start deleting");
        DeletePhotoApi deletePhotoApi = retrofit.create(DeletePhotoApi.class);
        deletePhotoApi.deletePhoto(App.getPreferencesHelper().getAccessToken(), id)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<PhotoArrayResponseOk>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(PhotoArrayResponseOk photoArrayResponseOk) {
                        Log.e(TAG, "deleting ter photo response model " +
                                photoArrayResponseOk.toString());
                        App.getDatabase().photoDao().deletePhoto(id);
                        invalidatePhotoDataSource();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Deleting the photo return error " + e.toString());
                    }
                });
    }

    public void downloadPhotoPage(int page) {
        DownloadPhotosApi downloadPhotosApi = retrofit.create(DownloadPhotosApi.class);
        Log.e(TAG, "start download page " + page);
        downloadPhotosApi.downloadPhotos(App.getPreferencesHelper().getAccessToken(), page)
                .subscribe(new SingleObserver<PhotoArrayResponseOk>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(PhotoArrayResponseOk photoArrayResponseOk) {
                        Log.e(TAG, "downloading photos return ok");
                        Log.e(TAG, "photo insert database run");
                        List<PhotoResponseModel> photoResponseModels =
                                Arrays.asList(photoArrayResponseOk.getPhotoResponseModels());
                        List<Photo> photos = new PhotoResponseAdapter().adapt(photoResponseModels, page);
                        App.getDatabase().photoDao().insertPage(page, photos);
                        photosPresenter.setPositionalDataSourceResult(photos, page);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    public List<Photo> downloadPhotoPageFromDb(int page) {
        return App.getDatabase().photoDao().getPageList(page);
    }

    public class PhotoResponseUploadCallback implements Callback<PhotoResponseOk> {

        @Override
        public void onResponse(@NonNull Call<PhotoResponseOk> call,
                               @NonNull Response<PhotoResponseOk> response) {
            Log.e(TAG, "on response");
            if (response.body() != null) {
                PhotoResponseOk photoResponseOk = response.body();
                PhotoResponseModel photoResponseModel = photoResponseOk.getPhotoResponseModel();
                saveDb(photoResponseModel);
                Log.e(TAG, "Response upload PhotoResponseModel is ok");
                Log.e(TAG, "upload photo response " + photoResponseOk.toString());
            } else {
                try {
                    Gson gson = new GsonBuilder().create();
                    Log.e(TAG, "upload photo response error " + response.errorBody().string());
                    PhotoResponseError accountResponseError =
                            gson.fromJson(response.errorBody().string(), PhotoResponseError.class);
                } catch (IOException e) {

                }
            }
        }


        @Override
        public void onFailure(@NonNull Call<PhotoResponseOk> call, @NonNull Throwable t) {
            Log.e(TAG, "on failure");
            Log.e(TAG, t.toString());
            for (StackTraceElement stackTraceElement : t.getStackTrace()) {
                Log.e(TAG, stackTraceElement.toString());
            }
            Log.e(TAG, t.getMessage());
            Log.e(TAG, t.getLocalizedMessage());
            Log.e(TAG, call.request().body().toString());
        }

        void saveDb(PhotoResponseModel photoResponseModel) {
            Executors.newSingleThreadExecutor().execute(() -> {
                Log.e(TAG, "save photo in db");
                App.getDatabase().photoDao().insert(new PhotoResponseAdapter().adapt(photoResponseModel, 0));
                invalidatePhotoDataSource();
            });
        }
    }
}
