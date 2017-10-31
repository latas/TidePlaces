package co.tide.tideplaces.data.interactors;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.data.models.RxException;
import co.tide.tideplaces.data.models.error.NoClosePlacesError;
import co.tide.tideplaces.data.responses.GSPlaceResult;
import co.tide.tideplaces.data.responses.GSPlacesResponse;
import co.tide.tideplaces.data.rest.ApiService;
import co.tide.tideplaces.data.rest.params.ConstantParams;
import co.tide.tideplaces.rxscheduler.BaseSchedulerProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class PlacesRepository implements Repository<List<Place>> {


    private final ApiService apiService;
    private final ConstantParams constantParams;
    private final BaseSchedulerProvider provider;
    private final MyLocationRepository locationRepository;


    @Inject
    public PlacesRepository(ApiService apiService, ConstantParams constantParams, BaseSchedulerProvider provider, MyLocationRepository locationRepository) {
        this.apiService = apiService;
        this.constantParams = constantParams;
        this.provider = provider;
        this.locationRepository = locationRepository;
    }


    @Override
    public Observable<List<Place>> data() {

        return locationRepository.data().flatMap(new Function<LatLng, ObservableSource<List<Place>>>() {
            @Override
            public ObservableSource<List<Place>> apply(LatLng latLng) throws Exception {
                return apiService.getPlaces(latLng.latitude + "," + latLng.longitude, constantParams.radiusParam(), constantParams.typeParam(), constantParams.apiKey()).subscribeOn(provider.io())
                        .flatMap(new Function<GSPlacesResponse, ObservableSource<List<Place>>>() {
                            @Override
                            public ObservableSource<List<Place>> apply(GSPlacesResponse gsPlacesResponse) throws Exception {
                                if (gsPlacesResponse.results().isEmpty())
                                    return Observable.error(new RxException(new NoClosePlacesError()));
                                List<Place> places = new ArrayList<>();
                                for (GSPlaceResult result : gsPlacesResponse.results())
                                    places.add(result.map());
                                return Observable.just(places);
                            }
                        }).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends List<Place>>>() {
                            @Override
                            public ObservableSource<List<Place>> apply(Throwable throwable) throws Exception {
                                return Observable.error(throwable);
                            }
                        });
            }
        });
    }
}
