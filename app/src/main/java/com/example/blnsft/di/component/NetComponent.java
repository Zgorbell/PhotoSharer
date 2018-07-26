package com.example.blnsft.di.component;

import com.example.blnsft.di.module.NetModule;
import com.example.blnsft.ui.base.BaseRepository;
import com.example.blnsft.ui.login.signin.SignInPresenter;
import com.example.blnsft.ui.login.signup.SignUpPresenter;
import com.example.blnsft.ui.main.MainPresenter;
import com.example.blnsft.ui.photos.PhotosPresenter;
import com.example.blnsft.ui.photos.deletephoto.DeletePhotoPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetModule.class})
public interface NetComponent {
    void injectMvpPresenter(PhotosPresenter presenter);

    void injectMvpPresenter(MainPresenter presenter);

    void injectMvpPresenter(DeletePhotoPresenter presenter);

    void injectSignInPresenter(SignInPresenter presenter);

    void injectSignUpPresenter(SignUpPresenter presenter);

    void injectRepository(BaseRepository baseRepository);
}
