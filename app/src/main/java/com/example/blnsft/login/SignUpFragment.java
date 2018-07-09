package com.example.blnsft.login;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.blnsft.R;
import com.example.blnsft.presenters.SignUpPresenter;
import com.example.blnsft.views.SignUpView;

import butterknife.BindView;

public class SignUpFragment extends BaseSignFragment implements SignUpView {

    public static SignUpFragment newInstance() {
        Bundle args = new Bundle();
        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @InjectPresenter
    SignUpPresenter signUpPresenter;
    @BindView(R.id.passwordRepeat)
    EditText mRepeatPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_signup;
    }

    private boolean isRepeatPasswordValid(String password) {
        return mRepeatPassword.getText().toString().equals(password);
    }

    protected boolean attemptLogin() {
        setError(mRepeatPassword, null);
        String password = mPasswordView.getText().toString();
        if (!super.attemptLogin()) return false;
        if (!isRepeatPasswordValid(password)) {
            setError(mRepeatPassword, getString(R.string.password_not_match)).requestFocus();
            return false;
        }
        return true;
    }

    @Override
    protected boolean authorizationStart() {
        if(super.authorizationStart()){
            signUpPresenter.checkSign(mEmailView.getText().toString(),
                                                mPasswordView.getText().toString());
            return true;
        }
        return false;
    }

    public void authorizationAlreadyExistLogin(){
        authorizationEnd();
        setError(mRepeatPassword, "Login already exist").requestFocus();
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    protected void showProgress(final boolean show) {
        super.showProgress(show);
        TextInputLayout inputLayout = getActivity().findViewById(R.id.passwordRepeatInputLayout);
        inputLayout.setVisibility(show ? View.GONE : View.VISIBLE);
    }
}
