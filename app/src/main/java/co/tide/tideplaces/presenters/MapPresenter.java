package co.tide.tideplaces.presenters;

import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;

import javax.inject.Inject;

import co.tide.tideplaces.data.interactors.PlacesRepository;
import co.tide.tideplaces.data.models.MapItem;
import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.rxscheduler.BaseSchedulerProvider;
import co.tide.tideplaces.ui.screens.UiMap;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MapPresenter implements Observer<List<Place>> {


    final UiMap map;
    final BaseSchedulerProvider provider;
    final CompositeDisposable disposables = new CompositeDisposable();
    final PlacesRepository placesRepository;

    @Inject
    public MapPresenter(UiMap map, BaseSchedulerProvider provider, PlacesRepository placesRepository) {
        this.map = map;
        this.provider = provider;
        this.placesRepository = placesRepository;

    }


    private LatLngBounds bounds(List<Place> places) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Place place : places) {
            builder.include(place.location());
        }

        return builder.build();
    }


    public void drain() {
        disposables.clear();
    }


    public void data() {
        placesRepository.data().subscribe(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposables.add(d);
    }

    @Override
    public void onNext(List<Place> places) {
        for (Place place : places) {
            if (place.isMyLocation())
                map.addMyPoi(place.location());
            else
                map.addPoi(new MapItem(place.location(), place.name(), place.distanceFromAnchor() + "m"));
        }
        if (places.size() > 0)
            map.zoomInBounds(bounds(places));
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
