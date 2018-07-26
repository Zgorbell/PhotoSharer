package com.example.blnsft.data.network;

import com.example.blnsft.data.network.models.PhotoArrayResponseOk;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface DownloadPhotosApi {
    @GET("/api/image")
    Single<PhotoArrayResponseOk> downloadPhotos(@Header("Access-Token") String token,
                                                @Query("page") int page);
}
