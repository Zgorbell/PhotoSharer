package com.example.blnsft.retrofit;

import com.example.blnsft.models.PhotoResponseOk;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface DeletePhoto {
    @DELETE("/api/image/{id}")
    Call<PhotoResponseOk> deletePhoto(@Header("Access-token") String token,
                                      @Path("id") String id);
}
