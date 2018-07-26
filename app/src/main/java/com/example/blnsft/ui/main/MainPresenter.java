package com.example.blnsft.ui.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Base64;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.blnsft.App;
import com.example.blnsft.R;
import com.example.blnsft.data.network.models.PhotoRequest;
import com.example.blnsft.data.prefs.PreferencesHelper;
import com.example.blnsft.ui.base.BaseRepository;
import com.example.blnsft.ui.map.LocationHelper;
import com.example.blnsft.ui.map.MapFragment;
import com.example.blnsft.ui.photos.PhotosFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.inject.Inject;


@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> implements MainPresenterInterface {
    private final String TAG = MainPresenter.class.getSimpleName();
    private final static int MAP_FRAGMENT = 1;
    private final static int PHOTOS_FRAGMENT = 0;
    @Inject
    BaseRepository baseRepository;

    MainPresenter() {
        App.getInstance().getNetComponent().injectMvpPresenter(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.e(TAG, "on first view attaching");
        PreferencesHelper preferencesHelper = App.getPreferencesHelper();
        if (preferencesHelper != null) {
            Log.e(TAG, " name " + preferencesHelper.getCurrentUserName() + " id " + preferencesHelper.getCurrentUserId() +
                    " token " + preferencesHelper.getAccessToken());
            setFragment(R.id.nav_photos);
        } else getViewState().startLoginActivity();
    }

    @Override
    public void attachView(MainView view) {
        Log.e(TAG, "on attach view");
        super.attachView(view);
        if (App.getPreferencesHelper().getCurrentUserName() != null) {
            getViewState().setHeader(App.getPreferencesHelper().getCurrentUserName());
        }
    }

    @Override
    public void onNavigationItemSelected(int id) {
        setFragment(id);
    }

    @Override
    public void onLogOutSelected() {
        Log.e(TAG, "log out");
        deleteSession();
        getViewState().startLoginActivity();
    }

    @Override
    public void onFabClicked() {
        getViewState().createTemporaryFile();
    }

    @Override
    public void onLoginActivityResult(boolean result) {
        if (result) setFragment(R.id.nav_photos);
        Log.e(TAG, "login activity return " + result);
    }

    @Override
    public void onPhotoActivityResult(boolean result, File file) {
        if(file != null) Log.e(TAG, "on photo activity result, file is not null");
        else Log.e(TAG, "on photo activity result, file is null");
        if (result && file != null)
            uploadPhoto(createPhotoRequest(file), App.getPreferencesHelper().getAccessToken());
        Log.e(TAG, "temp photo file is delete: " +
                String.valueOf(deleteTemporaryFile(file)));
    }

    @Override
    public void onTempFileCreated(File file) {
        getViewState().takeFile(file);
        getViewState().startCameraActivity(file);
    }

    private boolean deleteTemporaryFile(File file) {
        Log.e(TAG, "delete temporary file");
        return file != null && file.delete();
    }

    private void setFragment(int itemId) {
        Log.e(TAG, "set fragment");
        switch (itemId) {
            case R.id.nav_photos:
                String token = App.getPreferencesHelper().getAccessToken();
                if (token == null) Log.e(TAG, "token is null");
                getViewState().changeFragment(PhotosFragment.newInstance(), PHOTOS_FRAGMENT);
                break;
            case R.id.nav_map:
                getViewState().changeFragment(MapFragment.newInstance(), MAP_FRAGMENT);
                break;
        }
        getViewState().setNavigationItem(itemId);
    }

    private void deleteSession() {
        App.getPreferencesHelper().setCurrentUserName(null);
        App.getPreferencesHelper().setCurrentUserId(0L);
        App.getPreferencesHelper().setAccessToken(null);
    }

    private void uploadPhoto(PhotoRequest photoRequest, String token) {
        Log.e(TAG, "start upload photo");
        baseRepository.uploadPhoto(photoRequest, token);
    }

    private PhotoRequest createPhotoRequest(File mTempPhotoFile) {
        Log.e(TAG, "create photo request");
        Location location = LocationHelper.getCurrentLocation(App.getInstance());
        getViewState().notifyAboutLocation(location);
        int width = 120;
        int height = 120;
        if (location != null) {
            String base64 = Base64.encodeToString(
                    encodeFileToBase64Binary(mTempPhotoFile, width, height),
                    Base64.DEFAULT);
            return new PhotoRequest(
                    String.valueOf(System.currentTimeMillis() / 1000),
                    base64,
                    String.valueOf(location.getLatitude()),
                    String.valueOf(location.getLongitude()));
        }
        return null;
    }

    private byte[] encodeFileToBase64Binary(File file, int width, int height) {
        Log.e(TAG, "encode file to base 64 ");
        Bitmap bm = resizeBitmap(decodeFile(file), width, height);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    private Bitmap resizeBitmap(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    private Bitmap decodeFile(File file) {
        Log.e(TAG, "decode file");
        if (file == null) Log.e("Bitmap decoder", "can not decode, file is null");
        else return BitmapFactory.decodeFile(file.toString());
        return null;
    }
}
