package co.tide.tideplaces.data.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Antonis Latas
 */

public class PlaceResponseGeometry {
    @SerializedName("lat")
    @Expose
    public final double lat;
    @SerializedName("lng")
    @Expose
    public final double lng;

    public PlaceResponseGeometry(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}

