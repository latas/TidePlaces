package co.tide.tideplaces.ui.screens;

import java.util.List;

import co.tide.tideplaces.data.models.Place;

public interface PlacesScreen {
    void showPlaces(List<Place> places);
    void onErrorRetrievingPlaces();

}
