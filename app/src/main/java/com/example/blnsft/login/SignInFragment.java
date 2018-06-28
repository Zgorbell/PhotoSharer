package com.example.blnsft.login;

import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.blnsft.R;
import com.example.blnsft.presenters.SignInPresenter;
import com.example.blnsft.views.SignInView;

/**
 * A login screen that offers login via email/password.
 */
public class SignInFragment extends BaseSignFragment implements SignInView {

    public static SignInFragment newInstance() {
        Bundle args = new Bundle();
        SignInFragment fragment = new SignInFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @InjectPresenter()
    SignInPresenter signInPresenter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_signin;
    }

    @Override
    protected boolean authorizationStart() {
        if(super.authorizationStart()) {
            signInPresenter.checkSign(mEmailView.getText().toString(),
                    mPasswordView.getText().toString());
            return true;
        }
        return false;
    }
}

