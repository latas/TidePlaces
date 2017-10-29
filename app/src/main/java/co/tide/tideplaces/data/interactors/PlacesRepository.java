package co.tide.tideplaces.data.interactors;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.data.rest.ApiService;
import co.tide.tideplaces.data.rest.GooglePlacesParams;
import co.tide.tideplaces.data.rest.QueryParams;
import io.reactivex.Observable;

public class PlacesRepository implements Repository<List<Place>> {


    private final ApiService apiService;
    private Observable<List<Place>> observable;
    private QueryParams params;
    private final String apiKey;

    @Inject
    public PlacesRepository(ApiService apiService, @Named("apiKey") String apiKey) {
        this.apiService = apiService;
        this.apiKey = apiKey;
    }

    public PlacesRepository nearby(LatLng poi) {
        params = new GooglePlacesParams(poi, apiKey).build();
        return this;
    }

    @Override
    public Observable<List<Place>> data() {
        return null;
    }
}
