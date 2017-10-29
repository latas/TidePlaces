package co.tide.tideplaces.data.interactors;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.data.responses.GSPlacesResponse;
import co.tide.tideplaces.data.rest.ApiService;
import co.tide.tideplaces.data.rest.params.ConstantParams;
import co.tide.tideplaces.data.rest.params.GooglePlacesSearch;
import co.tide.tideplaces.data.rest.params.SearchQuery;
import co.tide.tideplaces.rxscheduler.BaseSchedulerProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class PlacesRepository implements Repository<List<Place>> {


    private final ApiService apiService;
    private Observable<List<Place>> observable;
    private SearchQuery params;
    private final ConstantParams constantParams;
    private final BaseSchedulerProvider provider;

    @Inject
    public PlacesRepository(ApiService apiService, ConstantParams constantParams, BaseSchedulerProvider provider) {
        this.apiService = apiService;
        this.constantParams = constantParams;
        this.provider = provider;
    }

    public PlacesRepository nearby(LatLng poi) {
        params = new GooglePlacesSearch(poi, constantParams).build();
        return this;
    }

    @Override
    public Observable<List<Place>> data() {
       return apiService.getPlaces(params.params()[0], params.params()[1], params.params()[2], params.params()[3]).subscribeOn(provider.io()).flatMap(new Function<GSPlacesResponse, ObservableSource<List<Place>>>() {
            @Override
            public ObservableSource<List<Place>> apply(GSPlacesResponse gsPlaceRepons) throws Exception {
                List<Place> places = new ArrayList<>();
                for (GSPlacesResponse.GSPlaceResult result : gsPlaceRepons.results())
                    places.add(result.map());
                return Observable.just(places);
            }
        }).observeOn(provider.ui());


        //  return Observable.just(Collections.<Place>emptyList());
    }
}
