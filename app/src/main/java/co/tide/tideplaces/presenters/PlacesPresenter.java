package co.tide.tideplaces.presenters;

import java.util.List;

import javax.inject.Inject;

import co.tide.tideplaces.R;
import co.tide.tideplaces.data.interactors.PlacesRepository;
import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.data.models.RxException;
import co.tide.tideplaces.di.scopes.ActivityScope;
import co.tide.tideplaces.rxscheduler.BaseSchedulerProvider;
import co.tide.tideplaces.ui.screens.Screen;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;

@ActivityScope
public class PlacesPresenter implements Observer<List<Place>> {

    final PlacesRepository placesRepository;
    final Screen placesScreen;
    final BaseSchedulerProvider provider;

    ConnectableObservable<List<Place>> observable;

    @Inject
    public PlacesPresenter(Screen placesScreen, PlacesRepository placesRepository, BaseSchedulerProvider provider) {
        this.placesRepository = placesRepository;
        this.placesScreen = placesScreen;
        this.provider = provider;
    }


    public void retrievePlaces() {
        placesScreen.showProgress();
        observable = placesRepository.data().subscribeOn(provider.io()).observeOn(provider.ui(), true).share().replay();
        observable.subscribe(this);
        observable.connect();

    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(List<Place> places) {
        if (places.size() >= 1 && !places.get(0).isMyLocation())
            placesScreen.hideProgress();
    }


    @Override
    public void onError(Throwable t) {
        if (t instanceof RxException) {
            if (((RxException) t).isUnAuthorizedPermissionError()) {
                placesScreen.requestLocationPermission();
            } else {
                placesScreen.onErrorRetrievingPlaces(((RxException) t).message());
            }
        } else
            placesScreen.onErrorRetrievingPlaces(R.string.general_error_retrieving_places);

        placesScreen.hideProgress();
    }

    @Override
    public void onComplete() {

    }

    public void subscribeUiObserver(Observer<List<Place>> observer) {
        observable.subscribe(observer);
    }
}
