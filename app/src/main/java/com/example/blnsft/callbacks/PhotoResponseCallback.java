package com.example.blnsft.callbacks;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.blnsft.models.PhotoResponseError;
import com.example.blnsft.models.PhotoResponseOk;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        Log.e("PhotoResponseCallback", "Response failure");
    }
}
