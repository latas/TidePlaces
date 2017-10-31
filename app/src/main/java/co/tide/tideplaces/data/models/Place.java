package co.tide.tideplaces.data.models;

import com.google.android.gms.maps.model.LatLng;

public interface Place {
    String id();

    LatLng location();

    String name();

    boolean isMyLocation();
}
