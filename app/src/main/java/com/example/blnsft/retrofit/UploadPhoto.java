package com.example.blnsft.retrofit;

import com.example.blnsft.models.Photo;
import com.example.blnsft.models.PhotoRequest;
import com.example.blnsft.models.PhotoResponseOk;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UploadPhoto {
    @Headers({
            "Accept: application/json;charset=UTF-8",
            })
    @POST("/api/image")
    Call<PhotoResponseOk> uploadImage(@Body PhotoRequest body, @Header("Access-Token") String token);
}
