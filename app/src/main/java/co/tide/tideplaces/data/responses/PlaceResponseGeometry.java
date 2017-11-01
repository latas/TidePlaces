package co.tide.tideplaces.data.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Antonis Latas
 */

public class PlaceResponseGeometry {

    @SerializedName("location")
    @Expose
   final GSPlaceLocation gsPlaceLocation;

    public PlaceResponseGeometry(GSPlaceLocation gsPlaceLocation) {
        this.gsPlaceLocation = gsPlaceLocation;
    }
}

