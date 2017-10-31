package co.tide.tideplaces.data.models;

import com.google.android.gms.maps.model.LatLng;

public class Venue implements Place {
    final String id;
    final LatLng location;
    final String name;

    public Venue(String id, String name, LatLng location) {
        this.id = id;
        this.location = location;
        this.name = name;
    }


    @Override
    public String id() {
        return id;
    }

    @Override
    public LatLng location() {
        return location;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean isMyLocation() {
        return false;
    }
}
