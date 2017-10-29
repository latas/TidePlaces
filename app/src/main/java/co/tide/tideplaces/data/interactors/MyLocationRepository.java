package co.tide.tideplaces.data.interactors;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import javax.inject.Inject;
import javax.inject.Named;

import co.tide.tideplaces.data.models.LocationPermission;
import co.tide.tideplaces.data.models.ResultListener;
import co.tide.tideplaces.data.models.error.LocationError;
import co.tide.tideplaces.data.models.error.UnAuthorizedLocationError;


public class MyLocationRepository implements Repository<Location>, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    Context context;
    GoogleApiClient googleApiClient;
    ResultListener<Location> listener;
    LocationPermission locationPermission;

    @Inject
    public MyLocationRepository(@Named("activity_context") Context context, GoogleApiClient apiClient, LocationPermission locationPermission) {
        this.context = context;
        this.googleApiClient = apiClient;
        this.locationPermission = locationPermission;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (locationPermission.granted()) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location != null) listener.success(location);
            else listener.failure(new LocationError());
        } else {
            listener.failure(new UnAuthorizedLocationError());
        }
        googleApiClient.disconnect();

    }

    @Override
    public void onConnectionSuspended(int i) {
        listener.failure(new LocationError());
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        listener.failure(new LocationError());
    }


    @Override
    public void data(ResultListener<Location> listener) {
        listener.start();
        googleApiClient.registerConnectionCallbacks(this);
        googleApiClient.registerConnectionFailedListener(this);
        googleApiClient.connect();
    }
}
