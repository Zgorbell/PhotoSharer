package com.example.blnsft.ui.main;

import android.location.Location;
import android.support.v4.app.Fragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.io.File;

public interface MainView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void changeFragment(Fragment fragment, int currentFragmentId);

    @StateStrategyType(SkipStrategy.class)
    void startLoginActivity();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setHeader(String name);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setNavigationItem(int item);

    @StateStrategyType(SkipStrategy.class)
    void startCameraActivity(File photoFile);

    @StateStrategyType(SkipStrategy.class)
    void notifyAboutLocation(Location location);

    @StateStrategyType(SingleStateStrategy.class)
    void takeFile(File file);

    @StateStrategyType(SkipStrategy.class)
    void createTemporaryFile();
}
