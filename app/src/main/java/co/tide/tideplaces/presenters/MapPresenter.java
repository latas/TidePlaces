package co.tide.tideplaces.presenters;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;

import co.tide.tideplaces.data.interactors.MapRepository;
import co.tide.tideplaces.data.models.MapItem;
import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.rxscheduler.BaseSchedulerProvider;
import co.tide.tideplaces.ui.screens.UiMap;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MapPresenter implements Consumer<GoogleMap>, Observer<List<Place>> {


    final MapView mapView;
    final UiMap map;
    final BaseSchedulerProvider provider;


    public MapPresenter(MapView mapView, UiMap map, BaseSchedulerProvider provider) {
        this.mapView = mapView;
        this.map = map;
        this.provider = provider;
    }

    public void loadMap() {
        new MapRepository(mapView).data().subscribe(this);
    }

    @Override
    public void accept(GoogleMap googleMap) throws Exception {
        map.onMapLoaded(googleMap);

    }


    private LatLngBounds bounds(List<Place> places) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Place place : places) {
            builder.include(place.location());
        }

        return builder.build();
    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(List<Place> places) {
        for (Place place : places) {
            if (place.isMyLocation())
                map.addMyPoi(place.location());
            else
                map.addPoi(new MapItem(place.location(), place.name(), place.distanceFromAnchor() + "m"));
        }
        map.zoomInBounds(bounds(places));
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
