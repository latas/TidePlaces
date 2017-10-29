package co.tide.tideplaces.data.interactors;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import co.tide.tideplaces.data.models.ResultListener;

public class MapRepository implements Repository<GoogleMap>, OnMapReadyCallback {
    final MapView mapView;
    ResultListener<GoogleMap> listener;

    public MapRepository(MapView mapView) {
        this.mapView = mapView;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.listener.success(googleMap);
    }

    @Override
    public void data(ResultListener<GoogleMap> listener) {
        listener.start();
        this.listener = listener;
        mapView.getMapAsync(this);
    }


}
