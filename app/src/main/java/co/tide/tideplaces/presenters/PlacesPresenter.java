package co.tide.tideplaces.presenters;

import javax.inject.Inject;

import co.tide.tideplaces.data.interactors.PlacesRepository;
import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.data.models.RxException;
import co.tide.tideplaces.rxscheduler.BaseSchedulerProvider;
import co.tide.tideplaces.ui.screens.PlacesScreen;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PlacesPresenter implements Observer<Place> {

    final PlacesRepository placesRepository;

    final PlacesScreen placesScreen;
    final BaseSchedulerProvider provider;

    @Inject
    public PlacesPresenter(PlacesScreen placesScreen, PlacesRepository placesRepository, BaseSchedulerProvider provider) {
        this.placesRepository = placesRepository;
        this.placesScreen = placesScreen;

        this.provider = provider;
    }


    public void showPlaces() {
        placesScreen.showProgress();
        placesRepository.data().subscribeOn(provider.io()).observeOn(provider.ui(), true).subscribe(this);
    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Place place) {
            placesScreen.hideProgress();
            placesScreen.showPlace(place);
    }


    @Override
    public void onError(Throwable t) {
        if (((RxException) t).isUnAuthorizedPermissionError()) {
            placesScreen.requestLocationPermission();
        } else {
            placesScreen.onErrorRetrievingPlaces(((RxException) t).message());
        }
        placesScreen.hideProgress();
    }

    @Override
    public void onComplete() {

    }
}
