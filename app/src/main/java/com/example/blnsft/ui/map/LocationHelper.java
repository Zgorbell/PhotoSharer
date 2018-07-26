package com.example.blnsft.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.List;

import static com.example.blnsft.ui.main.MainActivity.TAG;

public class LocationHelper {

    public static Location getCurrentLocation(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);
        Location bestLocation = null;
        if (checkLocationPermission(context))
            for (String provider : providers) {
                Log.e(TAG, "providers " + provider);
                Location l = lm.getLastKnownLocation(provider);
                if (l == null) continue;
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    bestLocation = l;
                }
            }
        return bestLocation;
    }

    public static boolean checkLocationPermission(Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) &&
                        checkPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);

    }

    private static boolean checkPermission(Context context, String permission) {
        if (ActivityCompat.checkSelfPermission(context, permission) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.e("Location", permission + "is granted");
            return true;
        } else return false;
    }
}
