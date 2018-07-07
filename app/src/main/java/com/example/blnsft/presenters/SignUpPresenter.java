package com.example.blnsft.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.example.blnsft.models.AccountResponseError;
import com.example.blnsft.models.AccountRequest;
import com.example.blnsft.models.AccountResponseOk;
import com.example.blnsft.retrofit.SignUpApi;
import com.example.blnsft.views.SignUpView;

import retrofit2.Call;

@InjectViewState(view = SignUpView.class)
public class SignUpPresenter extends SignInPresenter {
    private static final String LOGIN_ALREADY_USE = "security.signup.login-already-use";

    @Override
    public void checkSign(String login, String password) {
        SignUpApi api = buildRetrofit().create(SignUpApi.class);
        Call<AccountResponseOk> call = api.signUp(new AccountRequest(login, password));
        call.enqueue(new AccountResponseCallback());
    }

    @Override
    protected void checkError(AccountResponseError response) {
        super.checkError(response);
        if(response.getError().equals(LOGIN_ALREADY_USE)){
            ((SignUpView)getViewState()).authorizationAlreadyExistLogin();
        }
    }
}
