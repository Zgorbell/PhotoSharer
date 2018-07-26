package com.example.blnsft;

import android.app.Application;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;
import android.util.Log;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

import com.example.blnsft.data.db.AppDatabase;
import com.example.blnsft.data.prefs.PreferencesHelper;
import com.example.blnsft.di.component.ApplicationComponent;
import com.example.blnsft.di.component.DaggerApplicationComponent;
import com.example.blnsft.di.component.DaggerNetComponent;
import com.example.blnsft.di.component.NetComponent;
import com.example.blnsft.di.module.ApplicationModule;
import com.example.blnsft.di.module.NetModule;
import com.example.blnsft.di.module.StorageModule;
import com.google.android.gms.auth.api.signin.internal.Storage;

public class App extends Application {
    public static final String TAG = App.class.getSimpleName();
    public static App instance;
    private static final String DATABASE_NAME = "database";
    public static final String BASEURL = "http://junior.balinasoft.com";

    private static NetComponent netComponent;
    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Log.e(TAG, "creating an application");
        instance = this;
        Log.e(TAG, "creating a database");
        netComponent = DaggerNetComponent.builder()
                .netModule(new NetModule(BASEURL))
                .build();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .storageModule(new StorageModule(DATABASE_NAME)).build();

        applicationComponent.inject(this);

    }

    public static App getInstance() {
        return instance;
    }

    public static AppDatabase getDatabase() {
        return applicationComponent.database();
    }

    public NetComponent getNetComponent() {
        return netComponent;
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public static PreferencesHelper getPreferencesHelper(){return getApplicationComponent().preferencesHelper();}
}
