package com.example.blnsft.data.network;

import com.example.blnsft.data.network.models.PhotoArrayResponseOk;
import com.example.blnsft.data.network.models.PhotoRequest;
import com.example.blnsft.data.network.models.PhotoResponseOk;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UploadPhotoApi {
    @POST("/api/image")
    Call<PhotoResponseOk> uploadImage(@Body PhotoRequest body, @Header("Access-Token") String token);
}
