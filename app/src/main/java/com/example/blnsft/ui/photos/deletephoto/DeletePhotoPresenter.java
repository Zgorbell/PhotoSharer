package com.example.blnsft.ui.photos.deletephoto;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.blnsft.pojos.PhotoResponseError;
import com.example.blnsft.pojos.PhotoResponseOk;
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

@InjectViewState
public class DeletePhotoPresenter extends MvpPresenter<DeletePhotoView>{
    private static final String BASEURL = "http://junior.balinasoft.com";

    public void deletePhoto(String token, String id){
        Log.e("DeletePhotoPresenter", "Starting deleting a photo");
        DeletePhotoApi deletePhotoApi = buildRetrofit().create(DeletePhotoApi.class);
        Call<PhotoResponseOk> call = deletePhotoApi.deletePhoto(token, id);
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
            if (response.body() != null) {
                PhotoResponseOk photoResponseOk = response.body();
                Log.e("DeletePhotoPresenter", "Deleting the photo return ok");
            } else {
                try {
                    Gson gson = new GsonBuilder().create();
                    Log.e("DeletePhotoPresenter", "Deleting photo return error");
                    Log.e("DeletePhotoPresenter", response.errorBody().string());
                    PhotoResponseError accountResponseError =
                            gson.fromJson(response.errorBody().string(), PhotoResponseError.class);
                }
                catch (IOException e) {

                }
            }
        }

        @Override
        public void onFailure(@NonNull Call<PhotoResponseOk> call, @NonNull Throwable t) {
            Log.e("PhotoResponseCallback", "Response failure");
        }
    }
}
