package com.example.blnsft.data.network;


import com.example.blnsft.data.network.models.AccountRequest;
import com.example.blnsft.data.network.models.AccountResponseOk;


import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignInApi {
    @POST("/api/account/signin")
    Call<AccountResponseOk> signIn(@Body AccountRequest body);
}
