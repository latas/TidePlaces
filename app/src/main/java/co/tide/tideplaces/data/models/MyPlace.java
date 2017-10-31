package co.tide.tideplaces.data.models;

import com.google.android.gms.maps.model.LatLng;

public class MyPlace implements Place {
    final LatLng myLocation;

    public MyPlace(LatLng myLocation) {
        this.myLocation = myLocation;
    }

    @Override
    public String id() {
        return "";
    }

    @Override
    public LatLng location() {
        return myLocation;
    }

    @Override
    public String name() {
        return "";
    }

    @Override
    public boolean isMyLocation() {
        return true;
    }
}
