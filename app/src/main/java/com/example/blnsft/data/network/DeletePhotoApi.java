package com.example.blnsft.data.network;



import com.example.blnsft.data.network.models.PhotoArrayResponseOk;

import io.reactivex.Single;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface DeletePhotoApi {
    @DELETE("/api/image/{id}")
    Single<PhotoArrayResponseOk> deletePhoto(@Header("Access-token") String token,
                                             @Path("id") String id);
}
