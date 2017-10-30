package co.tide.tideplaces.data.interactors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;
import javax.inject.Named;

import co.tide.tideplaces.data.models.LocationPermission;
import co.tide.tideplaces.data.models.RxException;
import co.tide.tideplaces.data.models.error.LocationError;
import co.tide.tideplaces.data.models.error.UnAuthorizedLocationError;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class MyLocationRepository implements Repository<LatLng> {
    final LocationPermission locationPermission;
    Context context;


    @Inject
    public MyLocationRepository(@Named("activity_context") Context context, LocationPermission locationPermission) {
        this.context = context;
        this.locationPermission = locationPermission;

    }


    @Override
    public Observable<LatLng> data() {
        return Observable.create(new ObservableOnSubscribe<LatLng>() {
            @SuppressLint("MissingPermission")
            @Override
            public void subscribe(final ObservableEmitter<LatLng> e) throws Exception {
                if (!locationPermission.granted()) {
                    e.onError(new RxException(new UnAuthorizedLocationError()));
                    return;
                }
                LocationServices.getFusedLocationProviderClient(context).getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            e.onNext(new LatLng(task.getResult().getLatitude(), task.getResult().getLongitude()));
                        } else {
                            e.onError(new Throwable(
                                    new RxException(new LocationError())));
                        }
                    }
                });
            }
        });
    }
}
