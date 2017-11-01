package co.tide.tideplaces.presenters;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

import co.tide.tideplaces.data.interactors.MapRepository;
import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.rxscheduler.BaseSchedulerProvider;
import co.tide.tideplaces.ui.screens.UiMap;
import io.reactivex.functions.Consumer;

public class MapPresenter implements Consumer<GoogleMap>, PlacesViewPresenter {


    final MapView mapView;
    final UiMap map;
    final BaseSchedulerProvider provider;

    List<Place> mapPlaces = new ArrayList<>();

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
        if (mapPlaces.isEmpty())
            return;
        showPlaces();
        map.zoomInBounds(bounds());
    }


    private LatLngBounds bounds() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Place place : mapPlaces) {
            builder.include(place.location());
        }

        return builder.build();
    }


    public void onPlaces(List<Place> places) {
        mapPlaces.addAll(places);
        if (map.isLoaded()) {
            showPlaces();
            map.zoomInBounds(bounds());
        }
    }

    private void showPlaces() {
        for (Place place : mapPlaces) {
            if (place.isMyLocation())
                map.addMyPoi(place.location());
            else map.addPoi(place.location(), place.name(), place.distanceFromAnchor());
        }
    }
}
