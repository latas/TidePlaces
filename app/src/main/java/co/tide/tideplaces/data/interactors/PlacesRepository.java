package co.tide.tideplaces.data.interactors;

import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import co.tide.tideplaces.data.models.MyPlace;
import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.data.models.RxException;
import co.tide.tideplaces.data.models.error.CommonPlacesNotFoundError;
import co.tide.tideplaces.data.models.error.NoClosePlacesError;
import co.tide.tideplaces.data.responses.GSPlacesResponse;
import co.tide.tideplaces.data.rest.ApiService;
import co.tide.tideplaces.data.rest.params.ConstantParams;
import co.tide.tideplaces.rxscheduler.BaseSchedulerProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class PlacesRepository implements Repository<Place> {


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
    public Observable<Place> data() {

        return locationRepository.data().flatMap(new Function<LatLng, ObservableSource<Place>>() {
            @Override
            public ObservableSource<Place> apply(final LatLng latLng) throws Exception {

                return Observable.mergeDelayError(apiService.getPlaces(latLng.latitude + "," + latLng.longitude, constantParams.radiusParam(), constantParams.typeParam(), constantParams.apiKey())
                                .subscribeOn(provider.io())
                                .flatMap(new Function<GSPlacesResponse, Observable<Place>>() {
                                    @Override
                                    public Observable<Place> apply(GSPlacesResponse gsPlacesResponse) throws Exception {
                                        if (gsPlacesResponse.results().isEmpty())
                                            return Observable.error(new RxException(new NoClosePlacesError()));
                                        else return Observable.fromIterable(gsPlacesResponse.map());
                                    }
                                }).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Place>>() {
                                    @Override
                                    public ObservableSource<? extends Place> apply(Throwable throwable) throws Exception {
                                        if (throwable instanceof RxException)
                                            return Observable.error(throwable);
                                        return Observable.error(new RxException(new CommonPlacesNotFoundError()));
                                    }
                                }),
                        Observable.just(new MyPlace(latLng)));
            }
        });
    }
}
