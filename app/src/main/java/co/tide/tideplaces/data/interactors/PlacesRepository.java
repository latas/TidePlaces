package co.tide.tideplaces.data.interactors;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.data.rest.ApiService;
import co.tide.tideplaces.data.rest.params.ConstantParams;
import co.tide.tideplaces.data.rest.params.GooglePlacesSearch;
import co.tide.tideplaces.data.rest.params.SearchQuery;
import io.reactivex.Observable;

public class PlacesRepository implements Repository<List<Place>> {


    private final ApiService apiService;
    private Observable<List<Place>> observable;
    private SearchQuery params;
    private final ConstantParams constantParams;

    @Inject
    public PlacesRepository(ApiService apiService, ConstantParams constantParams) {
        this.apiService = apiService;
        this.constantParams = constantParams;
    }

    public PlacesRepository nearby(LatLng poi) {
        params = new GooglePlacesSearch(poi, constantParams).build();
        return this;
    }

    @Override
    public Observable<List<Place>> data() {
        return null;
    }
}
