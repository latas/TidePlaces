package co.tide.tideplaces.repositories;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import co.tide.tideplaces.BaseTest;
import co.tide.tideplaces.data.interactors.MyLocationRepository;
import co.tide.tideplaces.data.interactors.PlacesRepository;
import co.tide.tideplaces.data.models.MyPlace;
import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.data.models.RxException;
import co.tide.tideplaces.data.models.error.ErrorCodes;
import co.tide.tideplaces.data.models.error.LocationError;
import co.tide.tideplaces.data.models.error.UnAuthorizedLocationError;
import co.tide.tideplaces.data.responses.GSPlaceLocation;
import co.tide.tideplaces.data.responses.GSPlaceResult;
import co.tide.tideplaces.data.responses.GSPlacesResponse;
import co.tide.tideplaces.data.responses.PlaceResponseGeometry;
import co.tide.tideplaces.data.rest.ApiService;
import co.tide.tideplaces.data.rest.params.ConstantParams;
import io.reactivex.Observable;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.TestObserver;
import retrofit2.HttpException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlacesRepositoryTest extends BaseTest {

    @Mock
    MyLocationRepository myLocationRepository;
    @Mock
    ApiService apiService;
    @Mock
    HttpException exception;

    ConstantParams params = randomParams();
    PlacesRepository repository;
    ConnectableObservable observable;
    TestObserver<List<Place>> testObserver;

    private int totalPlaces = 20;
    private LatLng myLocation = new LatLng(10.0, 10.0);

    @Before
    public void setUp() {
        initMocks(this);
        repository = new PlacesRepository(apiService, params, schedulersProvider, myLocationRepository);
        testObserver = new TestObserver<>();

    }

    @Test
    public void randomVenues() {
        doReturn(Observable.just(myLocation)).when(myLocationRepository).data();
        doReturn(Observable.just(new GSPlacesResponse(getRandomPlaces()))).when(apiService).getPlaces(anyString(), anyString(), anyString(), anyString());

        final List<Place> expectedList1 = Collections.<Place>singletonList(new MyPlace(myLocation));
        final List<Place> expectedList2 = new ArrayList<>();

        for (int i = 0; i < getRandomPlaces().size(); i++) {
            expectedList2.add(getRandomPlaces().get(i).map());
        }


        ConnectableObservable obs = (ConnectableObservable) repository.init().data();
        obs.subscribeWith(testObserver);
        obs.connect();
        testScheduler.triggerActions();
        testObserver.assertNoErrors().assertValueAt(1, new Predicate<List<Place>>() {
            @Override
            public boolean test(List<Place> places) throws Exception {
                Assert.assertEquals(expectedList2, places);
                return true;
            }
        }).assertValueAt(0, new Predicate<List<Place>>() {
            @Override
            public boolean test(List<Place> places) throws Exception {
                Assert.assertEquals(expectedList1, places);
                return true;
            }
        }).assertValueCount(2).assertValueSet(Arrays.asList(expectedList1, expectedList2));
    }

    @Test
    public void noPlacesReturned() {
        doReturn(Observable.just(new GSPlacesResponse(Collections.<GSPlaceResult>emptyList()))).when(apiService).getPlaces(anyString(), anyString(), anyString(), anyString());
        doReturn(Observable.just(myLocation)).when(myLocationRepository).data();


        ConnectableObservable obs = (ConnectableObservable) repository.init().data();
        obs.subscribeWith(testObserver);
        obs.connect();
        testScheduler.triggerActions();
        testObserver.assertValue(new Predicate<List<Place>>() {
            @Override
            public boolean test(List<Place> places) throws Exception {
                return places.size() == 1 && places.get(0).equals(new MyPlace(myLocation));
            }
        }).assertError(new Predicate<Throwable>() {
            @Override
            public boolean test(Throwable throwable) throws Exception {
                return throwable instanceof RxException && ((RxException) throwable).code() == ErrorCodes.noClosePlacesError;
            }
        }).assertValueCount(1);
    }


    @Test
    public void noLocation() {

        doReturn(Observable.error(new RxException(new LocationError()))).when(myLocationRepository).data();

        ConnectableObservable obs = (ConnectableObservable) repository.init().data();
        obs.subscribeWith(testObserver);
        obs.connect();

        testScheduler.triggerActions();
        testObserver.assertError(new Predicate<Throwable>() {
            @Override
            public boolean test(Throwable throwable) throws Exception {
                return throwable instanceof RxException && ((RxException) throwable).code() == ErrorCodes.locationError;
            }
        }).assertNoValues();
    }

    @Test
    public void noLocationPermission() {

        doReturn(Observable.error(new RxException(new UnAuthorizedLocationError()))).when(myLocationRepository).data();

        ConnectableObservable obs = (ConnectableObservable) repository.init().data();
        obs.subscribeWith(testObserver);
        obs.connect();
        testScheduler.triggerActions();
        testObserver.assertError(new Predicate<Throwable>() {
            @Override
            public boolean test(Throwable throwable) throws Exception {
                return throwable instanceof RxException && ((RxException) throwable).code() == ErrorCodes.unAuthorizedLocationError;
            }
        }).assertNoValues();
    }

    @Test
    public void noLocationOtherException() {

        doReturn(Observable.error(new IllegalStateException())).when(myLocationRepository).data();


        ConnectableObservable obs = (ConnectableObservable) repository.init().data();
        obs.subscribeWith(testObserver);
        obs.connect();
        testScheduler.triggerActions();
        testObserver.assertError(new Predicate<Throwable>() {
            @Override
            public boolean test(Throwable throwable) throws Exception {
                return throwable instanceof IllegalStateException;
            }
        }).assertNoValues();
    }


    @Test
    public void serviceException() {

        doReturn(Observable.just(myLocation)).when(myLocationRepository).data();

        doReturn(Observable.error(exception))
                .when(apiService).getPlaces(anyString(), anyString(), anyString(), anyString());

        ConnectableObservable obs = (ConnectableObservable) repository.init().data();
        obs.subscribeWith(testObserver);
        obs.connect();
        testScheduler.triggerActions();

        testObserver.assertError(HttpException.class).assertValue(new Predicate<List<Place>>() {
            @Override
            public boolean test(List<Place> places) throws Exception {
                return places.size() == 1 && places.get(0).equals(new MyPlace(myLocation));
            }
        });
    }


    private List<GSPlaceResult> getRandomPlaces() {
        List<GSPlaceResult> placeResults = new ArrayList<>();
        for (int i = 0; i < totalPlaces; i++) {
            placeResults.add(new GSPlaceResult("" + i, "name" + i, new PlaceResponseGeometry(new GSPlaceLocation(15.0, 15.0))));
        }
        return placeResults;
    }

    private ConstantParams randomParams() {
        return new ConstantParams("ApikEy", "randomType", new Random().nextInt());

    }
}
