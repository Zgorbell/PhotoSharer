package com.example.blnsft.di.module;

import android.app.Application;
import android.content.Context;

import com.example.blnsft.data.prefs.PreferencesHelper;
import com.example.blnsft.data.prefs.AppPreferencesHelper;
import com.example.blnsft.ui.base.BaseRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import retrofit2.Retrofit;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Context provideContext(){ return mApplication;}

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(Context context) {
        return new AppPreferencesHelper(context);
    }
}
