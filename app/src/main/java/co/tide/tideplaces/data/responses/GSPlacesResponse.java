package co.tide.tideplaces.data.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.tide.tideplaces.data.models.Place;


public class GSPlacesResponse {

    @SerializedName("results")
    @Expose
    final List<GSPlaceResult> results;

    public GSPlacesResponse(List<GSPlaceResult> results) {
        this.results = results;
    }


    public List<GSPlaceResult> results() {
        return results != null ? results : Collections.<GSPlaceResult>emptyList();
    }

    public List<Place> map() {
        List<Place> places = new ArrayList<>();
        for (GSPlaceResult result : results()) {
            places.add(result.map());
        }
        return places;
    }
}




