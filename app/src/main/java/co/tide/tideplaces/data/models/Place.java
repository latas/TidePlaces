package co.tide.tideplaces.data.models;

import com.google.android.gms.maps.model.LatLng;

public class Place {
    final long id;
    final LatLng latLng;
    final String name;


    public Place(long id, LatLng latLng, String name) {
        this.id = id;
        this.latLng = latLng;
        this.name = name;
    }
}
