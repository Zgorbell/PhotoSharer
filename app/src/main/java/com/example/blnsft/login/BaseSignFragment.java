package com.example.blnsft.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.example.blnsft.MainActivity;
import com.example.blnsft.R;
import com.example.blnsft.models.UserData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A login screen that offers login via email/password.
 */
public abstract class BaseSignFragment extends MvpAppCompatFragment implements TextView.OnEditorActionListener {
    public final static String USER_DATA = "UserData";

    @BindView(R.id.email)
    AutoCompleteTextView mEmailView;
    @BindView(R.id.password)
    EditText mPasswordView;
    @BindView(R.id.login_form)
    View mLoginFormView;
    @BindView(R.id.login_progress)
    View mProgressView;
    @BindView(R.id.sign_button)
    Button buttonSign;

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(getLayoutResource(),
                                            container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return  rootView;
    }

    protected abstract int getLayoutResource();

    @OnClick(R.id.sign_button)
    public void signButtonOnClick() {
        authorizationStart();
    }

    @Override
    public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
            authorizationStart();
            return true;
        }
        return false;
    }

    protected boolean authorizationStart() {
        if(attemptLogin()) {
            showProgress(true);
            return true;
        }
        return false;
    }

    public void authorizationOk(UserData userData) {
        authorizationEnd();
        startMainActivity(userData);
    }

    public void authorizationIncorrectPassword() {
        authorizationEnd();
        setError(mPasswordView, "Password is incorrect").requestFocus();
    }

    public void authorizationBadLogin(String message) {
        authorizationEnd();
        setError(mEmailView, message).requestFocus();
    }

    public void authorizationBadPassword(String message) {
        authorizationEnd();
        setError(mPasswordView, message).requestFocus();
    }

    public void authorizationFail(String message){
        showProgress(false);
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    public void authorizationEnd(){
        showProgress(false);
        setError(mEmailView, null);
        setError(mPasswordView,null);
    }

    protected View setError(@NonNull TextView textView, String error) {
        textView.setError(error);
        return textView;
    }

    private boolean isLoginValid(String login){
        return login.length() > 3;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 7;
    }

    protected boolean attemptLogin() {
        setError(mEmailView, null);
        setError(mPasswordView, null);
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            focusView = setError(mPasswordView, getString(R.string.error_invalid_password));
            cancel = true;
        }
        if (TextUtils.isEmpty(email) && isLoginValid(email)) {
            focusView = setError(mEmailView, getString(R.string.error_invalid_login));
            cancel = true;
        }
        if (!cancel) return true;
        else focusView.requestFocus();
        return false;
    }

    protected void startMainActivity(UserData userData){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra(USER_DATA, userData).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    protected void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override public void onDestroyView(){
        super.onDestroyView();
        unbinder.unbind();
    }

}

