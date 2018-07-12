package com.example.blnsft.retrofit;

import com.example.blnsft.pojos.PhotoRequest;
import com.example.blnsft.pojos.PhotoResponseOk;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UploadPhoto {
    @POST("/api/image")
    Call<PhotoResponseOk> uploadImage(@Body PhotoRequest body, @Header("Access-Token") String token);
}
