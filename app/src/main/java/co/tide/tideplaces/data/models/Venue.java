package co.tide.tideplaces.data.models;

import com.google.android.gms.maps.model.LatLng;

public class Venue implements Place {
    final String id;
    final LatLng location;
    final String name;
    final Float distance;

    public Venue(String id, String name, LatLng location) {
        this(id, name, location, 0f);
    }


    public Venue(Place place, Float distance) {
        this(place.id(), place.name(), place.location(), distance);
    }

    public Venue(String id, String name, LatLng location, Float distance) {
        this.id = id;
        this.location = location;
        this.name = name;
        this.distance = distance;
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

    @Override
    public Float distanceFromAnchor() {
        return distance;
    }
}
