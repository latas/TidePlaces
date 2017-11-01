package co.tide.tideplaces.ui.screens;

import java.util.List;

import co.tide.tideplaces.data.models.Place;

public interface PlacesScreen {
    void showPlace(List<Place> places);

    void onErrorRetrievingPlaces(int message);

    void requestLocationPermission();

    void showProgress();

    void hideProgress();

}
