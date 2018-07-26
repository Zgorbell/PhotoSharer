package com.example.blnsft.data.network;

import com.example.blnsft.data.network.models.AccountRequest;
import com.example.blnsft.data.network.models.AccountResponseOk;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUpApi {
    @POST("/api/account/signup")
    Call<AccountResponseOk> signUp(@Body AccountRequest body);
}
