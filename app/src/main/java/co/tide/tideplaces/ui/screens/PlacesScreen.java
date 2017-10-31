package co.tide.tideplaces.ui.screens;

import co.tide.tideplaces.data.models.Place;

public interface PlacesScreen {
    void showPlace(Place places);

    void onErrorRetrievingPlaces(int message);

    void requestLocationPermission();

    void showProgress();

    void hideProgress();

}
