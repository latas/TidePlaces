package co.tide.tideplaces.presenters;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import co.tide.tideplaces.data.interactors.MapRepository;
import co.tide.tideplaces.ui.screens.UiMap;
import io.reactivex.functions.Consumer;

public class MapPresenter implements Consumer<GoogleMap> {


    MapView mapView;
    UiMap map;


    public MapPresenter(MapView mapView, UiMap map) {
        this.mapView = mapView;
        this.map = map;
    }

    public void loadMap() {
        new MapRepository(mapView).data().subscribe(this);
    }

    @Override
    public void accept(GoogleMap googleMap) throws Exception {
        map.onMapLoaded(googleMap);
    }
}
