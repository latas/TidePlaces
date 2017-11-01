package co.tide.tideplaces.ui.screens;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public interface UiMap extends DataScreen {
    void onMapLoaded(GoogleMap googleMap);

    void addMyPoi(LatLng latLng);

    boolean isLoaded();

    void zoomInBounds(LatLngBounds bounds);

    void addPoi(LatLng location, String title, float distance);
}
