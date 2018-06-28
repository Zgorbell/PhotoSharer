package com.example.blnsft.retrofit;


import com.example.blnsft.models.AccountRequest;
import com.example.blnsft.models.AccountResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignInApi {
    @POST("/api/account/signin")
    Call<AccountResponse> signIn(@Body AccountRequest body);
}
