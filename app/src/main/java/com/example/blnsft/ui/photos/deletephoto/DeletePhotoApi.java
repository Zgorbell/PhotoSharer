package com.example.blnsft.ui.photos.deletephoto;

import com.example.blnsft.pojos.PhotoResponseOk;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface DeletePhotoApi {
    @DELETE("/api/image/{id}")
    Call<PhotoResponseOk> deletePhoto(@Header("Access-token") String token,
                                      @Path("id") String id);
}
