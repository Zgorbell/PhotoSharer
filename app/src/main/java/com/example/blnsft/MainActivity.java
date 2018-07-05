package com.example.blnsft;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
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
import android.widget.TextView;

import com.example.blnsft.models.UserData;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final static String AUTH_USER = "Auth user";
    private final static int MAP_FRAGMENT = 0;
    private final static int PHOTOS_FRAGMENT = 1;
    private final int REQUEST_CODE_LOGIN = 0;
    private final int REQUEST_CODE_IMAGE_CAPTURE = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    TextView userNameText = findViewById(R.id.username);
    private UserData userData;
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkSession(PreferenceManager.getDefaultSharedPreferences(this));
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
        switch (item.getItemId()){
            case R.id.item_exit: createSession(); return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_photos: changeFragment(new PhotosFragment(), PHOTOS_FRAGMENT); break;
            case R.id.nav_map: changeFragment(new MapFragment(), MAP_FRAGMENT); break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeFragment(Fragment fragment, int id){
        Fragment exist = getSupportFragmentManager().findFragmentById(id);
        if(exist == null) exist = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_fragment_main, exist).commit();
    }

    public void saveSession(UserData userData, SharedPreferences preferences){
        //Log.e("Save session", userData.toString());
        preferences.edit().putString(AUTH_USER, new Gson().toJson(userData)).apply();
    }

    public UserData getSession(SharedPreferences preferences){
        //Log.e("Get session", preferences.getString(AUTH_USER, ""));
        return new Gson().fromJson(preferences.getString(AUTH_USER, ""), UserData.class);
    }

    public void checkSession(SharedPreferences preferences){
        if(preferences.contains(AUTH_USER)) userData = getSession(preferences);
        else createSession();
        userNameText.setText(userData.getLogin());
    }

    public void deleteSession(SharedPreferences preferences){
        preferences.edit().remove(AUTH_USER).apply();
    }


    public void createSession(){
        deleteSession(PreferenceManager.getDefaultSharedPreferences(this));
        startActivityForResult(new Intent(this, SignActivity.class), REQUEST_CODE_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_LOGIN:
                    userData = data.getParcelableExtra(UserData.USER_DATA);
                    saveSession(userData, PreferenceManager.getDefaultSharedPreferences(this));
                    break;
                case REQUEST_CODE_IMAGE_CAPTURE:
                    Log.e("GKGK", "here");
                    galleryAddPic();
                    break;
            }
        }
    }

    private View.OnClickListener fabClickListener(){
       return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
                    try {
                        dispatchTakePictureIntent(userData.getLogin());
                    }catch (IOException e){
                        Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        };
    }

    private void dispatchTakePictureIntent(String login) throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try{
                photoFile = createImageFile(login);
            }catch (IOException e){
                throw new IOException("Creating file problems");
            }
            if(photoFile != null) {
                Log.e("Photo temp path", mCurrentPhotoPath);
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.blnsft.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE_CAPTURE);
            }
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile(String login) throws IOException{
        String imageFileName = String.format("JPEG_%s", login);
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

}
