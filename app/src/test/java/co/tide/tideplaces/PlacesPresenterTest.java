package co.tide.tideplaces;

import com.google.android.gms.maps.model.LatLng;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import co.tide.tideplaces.data.interactors.MyLocationRepository;
import co.tide.tideplaces.data.interactors.PlacesRepository;
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
import co.tide.tideplaces.presenters.PlacesPresenter;
import co.tide.tideplaces.ui.screens.Screen;
import io.reactivex.Observable;
import retrofit2.HttpException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlacesPresenterTest extends BaseTest {

    final int totalPlaces = 20;

    @Mock
    Screen screen;
    @Mock
    MyLocationRepository myLocationRepository;
    @Mock
    ApiService apiService;

    @Mock
    HttpException exception;

    PlacesPresenter placesPresenter;
    PlacesRepository placesRepository;


    ConstantParams params = getRandomParams();


    ArgumentCaptor<RxException> rxExceptionCaptor = ArgumentCaptor.forClass(RxException.class);
    ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);


    @Before
    public void setUp() {
        initMocks(this);
        doReturn(Observable.just(new LatLng(10.0, 10.0))).when(myLocationRepository).data();
        placesRepository = spy(new PlacesRepository(apiService, params, schedulersProvider, myLocationRepository));
        placesPresenter = spy(new PlacesPresenter(screen, placesRepository, schedulersProvider));
    }


    @Test
    public void presenterTest_RandomPlacesReturned() {

        doReturn(Observable.just(new GSPlacesResponse(getRandomPlaces()))).when(apiService).getPlaces(anyString(), anyString(), anyString(), anyString());

        placesPresenter.retrievePlaces();
        testScheduler.triggerActions();
        verify(placesPresenter, times(2)).onNext(ArgumentMatchers.<Place>anyList());
        verify(screen).showProgress();
        verify(screen, times(2)).hideProgress();
        verifyNoMoreInteractions(screen);
        verify(placesPresenter, never()).onError(any(Throwable.class));

    }


    @Test
    public void presenterTest_HttpRequestFailed() {
        doReturn(Observable.error(exception))
                .when(apiService).getPlaces(anyString(), anyString(), anyString(), anyString());

        placesPresenter.retrievePlaces();
        testScheduler.triggerActions();
        verify(placesPresenter).onNext(listCaptor.capture());
        Assert.assertEquals(1, listCaptor.getValue().size());
        verify(screen).showProgress();
        verify(screen, atLeastOnce()).hideProgress();
        verify(screen).onErrorRetrievingPlaces(R.string.general_error_retrieving_places);
        verifyNoMoreInteractions(screen);
        verify(placesPresenter).onError(any(Throwable.class));
        verify(screen).onErrorRetrievingPlaces(R.string.general_error_retrieving_places);
    }


    @Test
    public void presenterTest_NoLocationFound_duo_to_permission() {
        doReturn(Observable.error(new RxException(new UnAuthorizedLocationError()))).when(myLocationRepository).data();
        placesPresenter.retrievePlaces();
        testScheduler.triggerActions();
        verify(placesPresenter, never()).onNext(ArgumentMatchers.<Place>anyList());
        verify(placesPresenter).onError(rxExceptionCaptor.capture());
        verify(screen).showProgress();
        verify(screen).hideProgress();
        verify(screen).requestLocationPermission();
        verifyNoMoreInteractions(screen);

        Assert.assertEquals(ErrorCodes.unAuthorizedLocationError, rxExceptionCaptor.getValue().code());
        Assert.assertEquals(R.string.empty, rxExceptionCaptor.getValue().message());

    }


    @Test
    public void presenterTest_NoLocationFound_due_to_system() {
        doReturn(Observable.error(new RxException(new LocationError()))).when(myLocationRepository).data();
        placesPresenter.retrievePlaces();
        testScheduler.triggerActions();
        verify(placesPresenter, never()).onNext(ArgumentMatchers.<Place>anyList());

        verify(placesPresenter).onError(rxExceptionCaptor.capture());
        verify(screen).onErrorRetrievingPlaces(R.string.location_cannot_retrieved_message);
        verify(screen).showProgress();
        verify(screen).hideProgress();
        Assert.assertEquals(ErrorCodes.locationError, rxExceptionCaptor.getValue().code());
        Assert.assertEquals(R.string.location_cannot_retrieved_message, rxExceptionCaptor.getValue().message());


    }


    @Test
    public void presenterTest_NoPlacesReturned() {

        doReturn(Observable.just(new GSPlacesResponse(Collections.<GSPlaceResult>emptyList()))).when(apiService).getPlaces(anyString(), anyString(), anyString(), anyString());
        placesPresenter.retrievePlaces();
        testScheduler.triggerActions();
        verify(placesPresenter).onNext(ArgumentMatchers.<Place>anyList());

        verify(placesPresenter).onError(rxExceptionCaptor.capture());
        verify(screen).onErrorRetrievingPlaces(R.string.no_close_places_error);
        verify(screen).showProgress();
        verify(screen, times(2)).hideProgress();
        Assert.assertEquals(ErrorCodes.noClosePlacesError, rxExceptionCaptor.getValue().code());
        Assert.assertEquals(R.string.no_close_places_error, rxExceptionCaptor.getValue().message());
    }


    private List<GSPlaceResult> getRandomPlaces() {
        List<GSPlaceResult> placeResults = new ArrayList<>();
        for (int i = 0; i < totalPlaces; i++) {
            placeResults.add(new GSPlaceResult("" + i, "name" + i, new PlaceResponseGeometry(new GSPlaceLocation(15.0, 15.0))));
        }
        return placeResults;
    }

    private ConstantParams getRandomParams() {
        return new ConstantParams("ApikEy", "randomType", new Random().nextInt());
    }

}
