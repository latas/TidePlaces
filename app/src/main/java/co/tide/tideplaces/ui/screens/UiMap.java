package co.tide.tideplaces.ui.screens;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;

import co.tide.tideplaces.data.models.Place;

public interface UiMap {
    void onMapLoaded(GoogleMap googleMap);

    void addPlace(Place place);

    boolean isLoaded();

    void zoomInBounds(CameraUpdate cameraUpdate);
}
