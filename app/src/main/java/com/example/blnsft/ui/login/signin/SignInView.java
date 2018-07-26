package com.example.blnsft.ui.login.signin;

import com.arellomobile.mvp.MvpView;
import com.example.blnsft.data.network.models.UserData;

public interface SignInView extends MvpView {
    void authorizationOk(UserData userData);
    void authorizationIncorrectPassword();
    void authorizationBadPassword(String message);
    void authorizationBadLogin(String message);
    void authorizationFail(String message);
}
