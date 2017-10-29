package co.tide.tideplaces.presenters;

import java.util.List;

import javax.inject.Inject;

import co.tide.tideplaces.data.interactors.PlacesRepository;
import co.tide.tideplaces.data.models.ResultListener;
import co.tide.tideplaces.data.models.error.Error;
import co.tide.tideplaces.data.responses.PlaceResponse;

public class PlacesPresenter {
    PlacesRepository placesRepository;


    @Inject
    public PlacesPresenter(PlacesRepository placesRepository) {
        this.placesRepository = placesRepository;
    }


    public void showPlaces() {
        placesRepository.data(new ResultListener<List<PlaceResponse>>() {
            @Override
            public void start() {

            }

            @Override
            public void success(List<PlaceResponse> placeResponses) {

            }

            @Override
            public void failure(Error error) {

            }
        });
    }
}
