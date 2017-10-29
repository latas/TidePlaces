package co.tide.tideplaces.ui.screens;

import com.google.android.gms.maps.model.LatLng;

public interface LocationScreen {


    void requestLocationPermission();

    void onLocationSetFailed(int message);

    void onLocationRetrieved(LatLng latLng);

    void startLocating();
}
