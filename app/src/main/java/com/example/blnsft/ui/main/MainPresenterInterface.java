package com.example.blnsft.ui.main;

import java.io.File;

public interface MainPresenterInterface {

    void onNavigationItemSelected(int id);

    void onLogOutSelected();

    void onFabClicked();

    void onPhotoActivityResult(boolean result, File file);

    void onLoginActivityResult(boolean result);

    void onTempFileCreated(File file);
}
