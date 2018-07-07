package com.example.blnsft;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.Layout;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.blnsft.models.PhotoRequest;
import com.example.blnsft.models.UserData;
import com.example.blnsft.photos.PhotosFragment;
import com.example.blnsft.presenters.UploadPhotoPresenter;
import com.example.blnsft.views.UploadPhotoView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UploadPhotoView {
    private final static int MAP_FRAGMENT = 0;
    private final static int PHOTOS_FRAGMENT = 1;
    private final int REQUEST_CODE_LOGIN = 0;
    private final int REQUEST_CODE_IMAGE_CAPTURE = 1;
    @InjectPresenter
    UploadPhotoPresenter uploadPhotoPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    TextView userNameHeader;
    private UserData userData;
    private UserSessionHelper sessionHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        fab.setOnClickListener(fabClickListener());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        userNameHeader = navigationView.getHeaderView(0).findViewById(R.id.username);
    }

    @Override
    protected void onStart() {
        super.onStart();
        sessionHelper = new UserSessionHelper(
                PreferenceManager.getDefaultSharedPreferences(this));
        if (sessionHelper.checkSession()) userData = sessionHelper.getSession();
        else startLoginActivity();
        userNameHeader.setText(userData.getLogin());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_exit:
                sessionHelper.deleteSession();
                startLoginActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_photos:
                changeFragment(new PhotosFragment(), PHOTOS_FRAGMENT);
                break;
            case R.id.nav_map:
                changeFragment(new MapFragment(), MAP_FRAGMENT);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeFragment(Fragment fragment, int id) {
        Fragment exist = getSupportFragmentManager().findFragmentById(id);
        if (exist == null) exist = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_fragment_main, exist).commit();
    }

    public void startLoginActivity() {
        Intent intent = new Intent(this, SignActivity.class);
        startActivityForResult(intent, REQUEST_CODE_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_LOGIN:
                    userData = data.getParcelableExtra(UserData.USER_DATA);
                    sessionHelper.saveSession(userData);
                    break;
                case REQUEST_CODE_IMAGE_CAPTURE:
                    Log.e("ResultActivity", "Camera activity return ok");
                    Location location = getCurrentLocation();
                    if(location != null){
                        String base64 = Base64.encodeToString(
                                encodeFileToBase64Binary(mTempPhotoFile, 120, 120),
                                Base64.DEFAULT);
                        PhotoRequest photoRequest = new PhotoRequest(
                                String.valueOf(System.currentTimeMillis() / 1000),
                                base64,
                                String.valueOf(location.getLatitude()),
                                String.valueOf(location.getLongitude()));
                        uploadPhotoPresenter.uploadPhoto(photoRequest, userData.getToken());
                    }
                    break;

            }
        } else Log.e("ResultActivity", "Activity is canceled");
        boolean b = mTempPhotoFile.delete();
        Log.e("ResultActivity"," Temp photo is deleted " + String.valueOf(b));
    }

    private View.OnClickListener fabClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    try {
                        dispatchTakePictureIntent();
                    } catch (IOException e) {
                        Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        };
    }

    private void dispatchTakePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile;
            try {
                photoFile = createTemporaryFile();
            } catch (IOException e) {
                throw new IOException("Creating file problems");
            }
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.example.blnsft.fileprovider",
                    photoFile);
            Log.e("DispatchTakePicture","Temp photo path" + photoURI.toString());
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE_CAPTURE);
        }
    }

    File mTempPhotoFile;
    private File createTemporaryFile() throws IOException {
        String imageFileName = "JPEG_TEMP_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        mTempPhotoFile = File.createTempFile(imageFileName, ".jpg", storageDir
        );
        return mTempPhotoFile;
    }

    private Location getCurrentLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);
        Location bestLocation = null;
        Log.e("Location","Permissions grated" +  String.valueOf(MapFragment.checkLocationPermission(this)));
        if (MapFragment.checkLocationPermission(this))
        for (String provider : providers) {
            Log.e("Providers", provider);
            Location l = lm.getLastKnownLocation(provider);
            if (l == null) continue;
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        notifyAboutLocation(bestLocation);
        return bestLocation;
    }

    private byte[] encodeFileToBase64Binary(File file, int width, int height) {
        Bitmap bm = resizeBitmap(decodeFile(file), width, height);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        Log.e("Base64","Size" + String.valueOf(baos.size()));
        return baos.toByteArray();
    }

    private void notifyAboutLocation(@NonNull Location location){
        Snackbar.make(fab, "Location accuracy :" + location.getAccuracy(),Snackbar.LENGTH_LONG)
                .show();
    }

    private Bitmap resizeBitmap(Bitmap bitmap, int width, int height){
        return Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    private Bitmap decodeFile(File file){
        return BitmapFactory.decodeFile(file.toString());
    }
}
