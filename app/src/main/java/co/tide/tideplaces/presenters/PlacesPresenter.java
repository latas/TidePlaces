package co.tide.tideplaces.presenters;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

import co.tide.tideplaces.data.interactors.MyLocationRepository;
import co.tide.tideplaces.data.interactors.PlacesRepository;
import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.ui.screens.PlacesScreen;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class PlacesPresenter implements Observer<List<Place>> {
    final PlacesRepository placesRepository;
    final MyLocationRepository locationRepository;
    final PlacesScreen placesScreen;


    @Inject
    public PlacesPresenter(PlacesScreen placesScreen, PlacesRepository placesRepository, MyLocationRepository locationRepository) {
        this.placesRepository = placesRepository;
        this.placesScreen = placesScreen;
        this.locationRepository = locationRepository;
    }


    public void showPlaces() {
        locationRepository.data().subscribe(new Observer<LatLng>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(LatLng latLng) {
                placesRepository.nearby(latLng).data().observeOn(AndroidSchedulers.mainThread()).subscribe(PlacesPresenter.this);
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
