package co.tide.tideplaces.ui.screens;

import android.location.Location;

public interface LocationScreen {


    void requestLocationPermission();

    void onLocationSetFailed(int message);

    void onLocationRetrieved(Location latLng);

    void startLocating();
}
