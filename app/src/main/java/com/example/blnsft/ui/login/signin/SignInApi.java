package com.example.blnsft.ui.login.signin;


import com.example.blnsft.pojos.AccountRequest;
import com.example.blnsft.pojos.AccountResponseOk;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignInApi {
    @POST("/api/account/signin")
    Call<AccountResponseOk> signIn(@Body AccountRequest body);
}
