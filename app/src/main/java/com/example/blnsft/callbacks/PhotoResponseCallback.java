package com.example.blnsft.callbacks;

import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.blnsft.pojos.PhotoResponseError;
import com.example.blnsft.pojos.PhotoResponseOk;
import com.example.blnsft.pojos.PhotoResponseModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoResponseCallback implements Callback<PhotoResponseOk> {
    private PositionalDataSource.LoadInitialCallback<PhotoResponseModel> callback;
    public static final String TAG = "PhotoResponseCallback";

    public PhotoResponseCallback(PositionalDataSource.LoadInitialCallback<PhotoResponseModel> callback){
        this.callback = callback;
    }

    @Override
    public void onResponse(@NonNull Call<PhotoResponseOk> call,
                           @NonNull Response<PhotoResponseOk> response) {
        if (response.body() != null) {
            PhotoResponseOk photoResponseOk = response.body();
            Log.e(TAG, "downloading photos return ok");
            callback.onResult(Arrays.asList(photoResponseOk.getPhotoResponseModels()), 0);
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
    public void onFailure(@NonNull Call<PhotoResponseOk> call, @NonNull Throwable t) {
        Log.e(TAG, "response failure");
    }
}
