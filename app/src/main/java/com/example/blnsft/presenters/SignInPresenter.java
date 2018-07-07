package com.example.blnsft.presenters;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.blnsft.models.AccountRequest;
import com.example.blnsft.models.AccountResponseOk;
import com.example.blnsft.models.AccountResponseError;
import com.example.blnsft.models.ValidError;
import com.example.blnsft.retrofit.SignInApi;
import com.example.blnsft.views.SignInView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@InjectViewState(view = SignInView.class)
public class SignInPresenter extends MvpPresenter<SignInView>{
    private static final String BASEURL = "http://junior.balinasoft.com";
    private static final String PASSWORD_INCORRECT = "security.signin.incorrect";
    private static final String VALIDATION_ERROR = "validation-error";
    private static final String PASSWORD = "password";
    private static final String LOGIN = "login";

    public void checkSign(String login, String password) {
        SignInApi signInApi = buildRetrofit().create(SignInApi.class);
        Call<AccountResponseOk> call = signInApi.signIn(new AccountRequest(login, password));
        call.enqueue(new AccountResponseCallback());
        if(call.isCanceled()) {
            getViewState().authorizationFail("TimeOut");
        }
    }

    protected Retrofit buildRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(buildOkHttp())
                .build();
    }
    protected OkHttpClient buildOkHttp(){
        return new OkHttpClient().newBuilder()
                .connectTimeout(2500, TimeUnit.MILLISECONDS)
                .readTimeout(2500, TimeUnit.MILLISECONDS)
                .build();
    }
    protected void checkError(AccountResponseError response) {
        if (response.getError().equals(PASSWORD_INCORRECT)) {
            getViewState().authorizationIncorrectPassword();
        } else if (response.getError().equals(VALIDATION_ERROR)) {
            checkValidationError(response.getValidError());
        }
    }

    private void checkValidationError(ValidError[] validErrors) {
        for(ValidError error: validErrors) {
            if (error.getField().equals(LOGIN)) {
                getViewState().authorizationBadLogin(error.getMessage());
            }
            if (error.getField().equals(PASSWORD)) {
                getViewState().authorizationBadPassword(error.getMessage());
            }
        }
    }

    public class AccountResponseCallback implements Callback<AccountResponseOk> {
        @Override
        public void onResponse(@NonNull Call<AccountResponseOk> call, @NonNull Response<AccountResponseOk> response) {
            if (response.body() != null) {
                AccountResponseOk accountResponseOk = response.body();
                getViewState().authorizationOk(accountResponseOk.getUserData());
            } else {
                try {
                    Gson gson = new GsonBuilder().create();
                    AccountResponseError accountResponseError =
                            gson.fromJson(response.errorBody().string(), AccountResponseError.class);
                    checkError(accountResponseError);
                } catch (IOException e) {
                }
            }
        }

        @Override
        public void onFailure(Call<AccountResponseOk> call, Throwable t) {
            getViewState().authorizationFail(t.getMessage());
        }
    }
}

