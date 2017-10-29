package co.tide.tideplaces.data.responses;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

import co.tide.tideplaces.data.models.Place;


public class GSPlacesResponse {

    @SerializedName("results")
    @Expose
    List<GSPlaceResult> results;

    public List<GSPlaceResult> results() {
        return results != null ? results : Collections.<GSPlaceResult>emptyList();
    }

    public class GSPlaceResult {

        @SerializedName("geometry")
        @Expose
        public PlaceResponseGeometry geometry;


        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("name")
        @Expose
        public String name;


        class PlaceResponseGeometry {
            @SerializedName("lat")
            @Expose
            public double lat;
            @SerializedName("lng")
            @Expose
            public double lng;
        }

        public Place map() {
            return new Place(id, name, new LatLng(geometry.lat, geometry.lng));
        }

    }

}


