package co.tide.tideplaces.data.interactors;

import com.google.android.gms.maps.model.LatLng;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import co.tide.tideplaces.data.models.GeoDistance;
import co.tide.tideplaces.data.models.MyPlace;
import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.data.models.RxException;
import co.tide.tideplaces.data.models.Venue;
import co.tide.tideplaces.data.models.error.NoClosePlacesError;
import co.tide.tideplaces.data.responses.GSPlacesResponse;
import co.tide.tideplaces.data.rest.ApiService;
import co.tide.tideplaces.data.rest.params.ConstantParams;
import co.tide.tideplaces.di.scopes.ActivityScope;
import co.tide.tideplaces.rxscheduler.BaseSchedulerProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

@ActivityScope
public class PlacesRepository implements Repository<List<Place>> {


    private final ApiService apiService;
    private final ConstantParams constantParams;
    private final BaseSchedulerProvider provider;
    private final MyLocationRepository locationRepository;
    private Observable observable;


    @Inject
    public PlacesRepository(ApiService apiService, ConstantParams constantParams, BaseSchedulerProvider provider, MyLocationRepository locationRepository) {
        this.apiService = apiService;
        this.constantParams = constantParams;
        this.provider = provider;
        this.locationRepository = locationRepository;
    }

    public PlacesRepository init() {
        this.observable = locationRepository.data().flatMap(new Function<LatLng, ObservableSource<List<Place>>>() {
            @Override
            public ObservableSource<List<Place>> apply(final LatLng latLng) throws Exception {
                return Observable.mergeDelayError(apiService.getPlaces(latLng.latitude + "," + latLng.longitude, constantParams.radiusParam(), constantParams.typeParam(), constantParams.apiKey())
                                .subscribeOn(provider.io())
                                .flatMap(new Function<GSPlacesResponse, ObservableSource<Place>>() {
                                    @Override
                                    public ObservableSource<Place> apply(GSPlacesResponse gsPlacesResponse) throws Exception {
                                        if (gsPlacesResponse.results().isEmpty())
                                            return Observable.error(new RxException(new NoClosePlacesError()));
                                        else
                                            return Observable.fromIterable(gsPlacesResponse.map());
                                    }
                                })
                                .map(new Function<Place, Place>() {
                                    @Override
                                    public Place apply(Place place) throws Exception {
                                        return new Venue(place, new GeoDistance(latLng, place.location()).meters());
                                    }
                                })
                                .toSortedList(new Comparator<Place>() {
                                    @Override
                                    public int compare(Place place, Place t1) {
                                        return place.distanceFromAnchor().compareTo(t1.distanceFromAnchor());
                                    }
                                })
                                .toObservable()
                        , Observable.just(Collections.<Place>singletonList(new MyPlace(latLng))));
            }
        }).subscribeOn(provider.io()).observeOn(provider.ui(), true).share().replay();
        return this;
    }

    @Override
    public Observable<List<Place>> data() {
        return observable;
    }
}
