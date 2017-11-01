package co.tide.tideplaces.presenters;

import java.util.List;

import co.tide.tideplaces.data.models.Place;

public interface PlacesViewPresenter {

    void onPlaces(List<Place> places);
}
