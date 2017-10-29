package co.tide.tideplaces.presenters;

import javax.inject.Inject;

import co.tide.tideplaces.data.interactors.MyLocationRepository;
import co.tide.tideplaces.ui.screens.LocationScreen;

public class LocationPresenter {


    final MyLocationRepository repository;

    final LocationScreen locationScreen;

    @Inject
    public LocationPresenter(MyLocationRepository repository, LocationScreen screen) {
        this.repository = repository;
        this.locationScreen = screen;
    }

    public void locateMe() {
        repository.data();
    }

}
