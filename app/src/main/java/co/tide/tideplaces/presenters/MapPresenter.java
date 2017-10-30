package co.tide.tideplaces.presenters;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import co.tide.tideplaces.data.interactors.MapRepository;
import co.tide.tideplaces.rxscheduler.BaseSchedulerProvider;
import co.tide.tideplaces.ui.screens.UiMap;
import io.reactivex.functions.Consumer;

public class MapPresenter implements Consumer<GoogleMap> {


    final MapView mapView;
    final UiMap map;
    final BaseSchedulerProvider provider;


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
    }
}
