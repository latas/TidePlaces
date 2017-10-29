package co.tide.tideplaces.data.interactors;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;
import javax.inject.Named;

import co.tide.tideplaces.data.models.LocationPermission;
import co.tide.tideplaces.data.models.ResultListener;
import co.tide.tideplaces.data.models.error.LocationError;
import co.tide.tideplaces.di.scopes.ActivityScope;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@ActivityScope
public class MyLocationRepository implements Repository<Location>, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    Context context;
    GoogleApiClient googleApiClient;
    ResultListener<Location> listener;
    LocationPermission locationPermission;
    Consumer<LatLng> locationConsumer;


    @Inject
    public MyLocationRepository(@Named("activity_context") Context context, GoogleApiClient apiClient, LocationPermission locationPermission, Consumer<LatLng> locationConsumer) {
        this.context = context;
        this.googleApiClient = apiClient;
        this.locationPermission = locationPermission;
        this.locationConsumer = locationConsumer;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

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
     /*   this.listener = listener;
        googleApiClient.registerConnectionCallbacks(this);
        googleApiClient.registerConnectionFailedListener(this);
        this.listener.start();
        googleApiClient.connect();
*/
        Observable.just(new LatLng(10, 10)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(locationConsumer);
    }
}
