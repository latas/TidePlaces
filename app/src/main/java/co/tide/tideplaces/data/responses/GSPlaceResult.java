package co.tide.tideplaces.data.responses;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import co.tide.tideplaces.data.models.Place;

/**
 * Created by Antonis Latas
 */

public class GSPlaceResult {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("geometry")
    @Expose
    public PlaceResponseGeometry geometry;

    public GSPlaceResult(String id, String name, PlaceResponseGeometry geometry) {
        this.geometry = geometry;
        this.id = id;
        this.name = name;
    }

    public Place map() {
        return new Place(id, name, new LatLng(geometry.lat, geometry.lng));
    }

}
