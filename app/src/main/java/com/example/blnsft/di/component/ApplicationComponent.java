package com.example.blnsft.di.component;

import android.app.Application;
import android.content.Context;

import com.example.blnsft.App;
import com.example.blnsft.data.db.AppDatabase;
import com.example.blnsft.data.prefs.PreferencesHelper;
import com.example.blnsft.di.module.ApplicationModule;
import com.example.blnsft.di.module.NetModule;
import com.example.blnsft.di.module.StorageModule;
import com.example.blnsft.ui.main.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, StorageModule.class})
public interface ApplicationComponent {
    void inject(App app);

    Application application();

    Context context();

    AppDatabase database();

    PreferencesHelper preferencesHelper();

}
