package co.tide.tideplaces.ui.screens;

import java.util.List;

import co.tide.tideplaces.data.models.Place;

public interface Screen {

    void onErrorRetrievingPlaces(int message);

    void requestLocationPermission();

    void showProgress();

    void hideProgress();

}
