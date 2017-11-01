package co.tide.tideplaces.presenters;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import java.util.ArrayList;
import java.util.List;

import co.tide.tideplaces.data.interactors.MapRepository;
import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.rxscheduler.BaseSchedulerProvider;
import co.tide.tideplaces.ui.screens.UiMap;
import io.reactivex.functions.Consumer;

public class MapPresenter implements Consumer<GoogleMap> {


    final MapView mapView;
    final UiMap map;
    final BaseSchedulerProvider provider;

    List<Place> places = new ArrayList<>();

    public MapPresenter(MapView mapView, UiMap map, BaseSchedulerProvider provider) {
        this.mapView = mapView;
        this.map = map;
        this.provider = provider;
    }

    public void loadMap() {
        new MapRepository(mapView).data().subscribeOn(provider.ui()).subscribeOn(provider.ui()).subscribe(this);
    }

    @Override
    public void accept(GoogleMap googleMap) throws Exception {
        map.onMapLoaded(googleMap);
        for (Place place : places) {
            map.addPlace(place);
        }
    }

    public void onPlace(Place place) {
        places.add(place);
        if (map.isLoaded())
            map.addPlace(place);
    }
}
