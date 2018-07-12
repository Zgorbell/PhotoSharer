package com.example.blnsft.retrofit;

import com.example.blnsft.pojos.PhotoResponseOk;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface DownloadPhotos{
    @GET("/api/image")
    Call<PhotoResponseOk> downloadPhotos(@Header("Access-Token") String token,
                                         @Query("page") int page);
}
