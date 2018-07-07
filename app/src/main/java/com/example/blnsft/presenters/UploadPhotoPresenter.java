package com.example.blnsft.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.MvpPresenter;
import com.example.blnsft.models.AccountResponseError;
import com.example.blnsft.models.AccountResponseOk;
import com.example.blnsft.models.PhotoRequest;
import com.example.blnsft.models.PhotoResponseError;
import com.example.blnsft.models.PhotoResponseOk;
import com.example.blnsft.retrofit.UploadPhoto;
import com.example.blnsft.views.UploadPhotoView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UploadPhotoPresenter extends MvpPresenter<UploadPhotoView> {
    private static final String BASEURL = "http://junior.balinasoft.com";

    public void uploadPhoto(PhotoRequest photoRequest, String token){
        Log.e("UploadPhotoPresenter", "Start upload photo");
        UploadPhoto uploadPhoto = buildRetrofit().create(UploadPhoto.class);
        Call<PhotoResponseOk> call = uploadPhoto.uploadImage(photoRequest, token);
        call.enqueue(new PhotoResponseCallback());
    }

    protected Retrofit buildRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(buildOkHttp())
                .build();
    }

    protected OkHttpClient buildOkHttp(){
        return new OkHttpClient().newBuilder()
                .connectTimeout(2500, TimeUnit.MILLISECONDS)
                .readTimeout(2500, TimeUnit.MILLISECONDS)
                .build();
    }

    public class PhotoResponseCallback implements Callback<PhotoResponseOk> {

        @Override
        public void onResponse(@NonNull Call<PhotoResponseOk> call,
                               @NonNull Response<PhotoResponseOk> response) {
            Log.e("UploadPhotoPresenter", "Response upload photo");
            if (response.body() != null) {
                PhotoResponseOk photoResponseOk = response.body();
                Log.e("UploadPhotoPresenter", "Response upload photo is ok");
                Log.e("PhotoResponse", photoResponseOk.toString());
            } else {
                try {
                    Gson gson = new GsonBuilder().create();
                    Log.e("Photo response error", response.errorBody().string());
                    PhotoResponseError accountResponseError =
                            gson.fromJson(response.errorBody().string(), PhotoResponseError.class);
                }
                catch (IOException e) {

                }
            }
        }

        @Override
        public void onFailure(@NonNull Call<PhotoResponseOk> call, @NonNull Throwable t) {

        }
    }
}
