package co.tide.tideplaces.ui.screens;

import com.google.android.gms.maps.model.LatLng;

public interface UiMap {
    void onLocationSetFailed();
    void addPoi(LatLng marker);



}
