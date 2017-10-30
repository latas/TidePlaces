package co.tide.tideplaces.presenters;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

import co.tide.tideplaces.data.interactors.MyLocationRepository;
import co.tide.tideplaces.data.interactors.PlacesRepository;
import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.rxscheduler.BaseSchedulerProvider;
import co.tide.tideplaces.ui.screens.PlacesScreen;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PlacesPresenter implements Observer<List<Place>> {
    final PlacesRepository placesRepository;
    final MyLocationRepository locationRepository;
    final PlacesScreen placesScreen;
    final BaseSchedulerProvider provider;

    @Inject
    public PlacesPresenter(PlacesScreen placesScreen, PlacesRepository placesRepository, MyLocationRepository locationRepository, BaseSchedulerProvider provider) {
        this.placesRepository = placesRepository;
        this.placesScreen = placesScreen;
        this.locationRepository = locationRepository;
        this.provider = provider;
    }


    public void showPlaces() {
        locationRepository.data().subscribeOn(provider.io()).observeOn(provider.ui()).subscribe(new Observer<LatLng>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(LatLng latLng) {
                placesRepository.nearby(latLng).data().subscribeOn(provider.io()).observeOn(provider.ui()).subscribe(PlacesPresenter.this);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(List<Place> places) {
        placesScreen.showPlaces(places);
    }


    @Override
    public void onError(Throwable t) {
        Log.i("error", "thr " + t.toString());
        placesScreen.onErrorRetrievingPlaces();
    }

    @Override
    public void onComplete() {

    }
}
