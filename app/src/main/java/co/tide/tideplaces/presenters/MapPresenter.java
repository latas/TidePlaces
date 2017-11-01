package co.tide.tideplaces.presenters;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
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
        if (places.size() == 0)
            return;
        for (Place place : places) {
            map.addPlace(place);
        }
        map.zoomInBounds(cameraUpdate());
    }

    private CameraUpdate cameraUpdate() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Place place : places) {
            builder.include(place.location());
        }

        return CameraUpdateFactory.newLatLngBounds(builder.build(), 100);
    }


    public void onPlace(Place place) {
        places.add(place);
        if (map.isLoaded()) {
            map.addPlace(place);
            map.zoomInBounds(cameraUpdate());
        }
    }
}
