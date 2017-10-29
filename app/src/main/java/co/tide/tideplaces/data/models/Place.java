package co.tide.tideplaces.data.models;

import com.google.android.gms.maps.model.LatLng;

public class Place {
    final String id;
    final LatLng latLng;
    final String name;


    public Place(String id,
                 String name, LatLng latLng) {
        this.id = id;
        this.latLng = latLng;
        this.name = name;
    }
}
