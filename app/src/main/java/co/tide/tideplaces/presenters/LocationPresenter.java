package co.tide.tideplaces.presenters;

import android.location.Location;

import javax.inject.Inject;

import co.tide.tideplaces.data.interactors.MyLocationRepository;
import co.tide.tideplaces.data.models.ResultListener;
import co.tide.tideplaces.data.models.error.Error;
import co.tide.tideplaces.data.models.error.ErrorCodes;
import co.tide.tideplaces.ui.screens.LocationScreen;

public class LocationPresenter implements ResultListener<Location> {


    final MyLocationRepository repository;

    final LocationScreen locationScreen;

    @Inject
    public LocationPresenter(MyLocationRepository repository, LocationScreen screen) {
        this.repository = repository;
        this.locationScreen = screen;
    }

    public void locateMe() {
        repository.data(this);
    }


    @Override
    public void start() {
        locationScreen.startLocating();
    }

    @Override
    public void success(Location l) {
        locationScreen.onLocationRetrieved(l);
    }

    @Override
    public void failure(Error error) {
        if (error.code() != ErrorCodes.unAthorizedLocationError)
            locationScreen.onLocationSetFailed(error.message());
        else {
            locationScreen.requestLocationPermission();
        }
    }
}
