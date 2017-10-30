package co.tide.tideplaces.data.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;


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
}




