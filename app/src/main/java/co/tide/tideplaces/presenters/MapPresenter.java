package co.tide.tideplaces.presenters;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import javax.inject.Inject;

import co.tide.tideplaces.data.interactors.MapRepository;
import co.tide.tideplaces.data.models.ResultListener;
import co.tide.tideplaces.ui.screens.UiMap;

public class MapPresenter {


    MapView mapView;
    UiMap map;

    @Inject
    public MapPresenter(MapView mapView, UiMap map) {
        this.mapView = mapView;
        this.map = map;
    }

    public void loadMap() {
        new MapRepository(mapView).data(new ResultListener<GoogleMap>() {
            @Override
            public void start() {

            }

            @Override
            public void success(GoogleMap googleMap) {
                map.onMapLoaded(googleMap);
            }

            @Override
            public void failure() {

            }
        });
    }
}
