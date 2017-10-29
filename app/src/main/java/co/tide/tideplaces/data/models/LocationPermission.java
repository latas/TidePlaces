package co.tide.tideplaces.data.models;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * BeatTestProject-Android
 * <p>
 * Created by Antonis Latas on 9/15/2017
 */

public class LocationPermission {
    final Context context;

    @Inject
    public LocationPermission(@Named("activity_context") Context context) {
        this.context = context;
    }


    public boolean granted() {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

    }
}
