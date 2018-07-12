package com.example.blnsft.ui.login.signup;

import com.example.blnsft.pojos.AccountRequest;
import com.example.blnsft.pojos.AccountResponseOk;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUpApi {
    @POST("/api/account/signup")
    Call<AccountResponseOk> signUp(@Body AccountRequest body);
}
