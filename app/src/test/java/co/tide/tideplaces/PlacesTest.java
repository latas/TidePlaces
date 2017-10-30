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

import co.tide.tideplaces.data.interactors.MyLocationRepository;
import co.tide.tideplaces.data.interactors.PlacesRepository;
import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.data.responses.GSPlaceResult;
import co.tide.tideplaces.data.responses.GSPlacesResponse;
import co.tide.tideplaces.data.responses.PlaceResponseGeometry;
import co.tide.tideplaces.data.rest.ApiService;
import co.tide.tideplaces.data.rest.params.ConstantParams;
import co.tide.tideplaces.presenters.PlacesPresenter;
import co.tide.tideplaces.ui.screens.PlacesScreen;
import io.reactivex.Observable;
import retrofit2.HttpException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlacesTest extends BaseTest {

    final int totalPlaces = 20;

    @Mock
    PlacesScreen placesScreen;
    @Mock
    MyLocationRepository myLocationRepository;
    @Mock
    ApiService apiService;

    @Mock
    HttpException exception;

    PlacesPresenter placesPresenter;
    PlacesRepository placesRepository;


    ConstantParams params = new ConstantParams("apiKey", "type", 5000);
    ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);

    @Before
    public void setUp() {
        initMocks(this);
        doReturn(Observable.just(new LatLng(10.0, 10.0))).when(myLocationRepository).data();
        placesRepository = spy(new PlacesRepository(apiService, params, schedulersProvider));
        placesPresenter = spy(new PlacesPresenter(placesScreen, placesRepository, myLocationRepository, schedulersProvider));
    }

    @Test
    public void presenterTest_NoPlacesReturned() {

        doReturn(Observable.just(new GSPlacesResponse(Collections.<GSPlaceResult>emptyList()))).when(apiService).getPlaces(anyString(), anyString(), anyString(), anyString());
        placesPresenter.showPlaces();
        testScheduler.triggerActions();
        verify(placesPresenter).onNext(listCaptor.capture());
        verify(placesScreen).onErrorRetrievingPlaces();
        Assert.assertEquals(0, listCaptor.getValue().size());

        verify(placesPresenter, never()).onError(any(Throwable.class));
        verify(placesScreen, never()).showPlaces(ArgumentMatchers.<Place>anyList());
    }


    @Test
    public void presenterTest_RandomPlacesReturned() {

        doReturn(Observable.just(new GSPlacesResponse(getRandomPlaces()))).when(apiService).getPlaces(anyString(), anyString(), anyString(), anyString());

        placesPresenter.showPlaces();
        testScheduler.triggerActions();

        verify(placesPresenter).onNext(listCaptor.capture());
        Assert.assertEquals(totalPlaces, listCaptor.getValue().size());
        verify(placesScreen).showPlaces(listCaptor.capture());
        Assert.assertEquals(totalPlaces, listCaptor.getValue().size());
        verify(placesScreen, never()).onErrorRetrievingPlaces();
        verify(placesPresenter, never()).onError(any(Throwable.class));
    }


    @Test
    public void presenterTest_HttpRequestFailed() {
        doReturn(Observable.error(exception))
                .when(apiService).getPlaces(anyString(), anyString(), anyString(), anyString());

        placesPresenter.showPlaces();
        testScheduler.triggerActions();
        verify(placesPresenter, never()).onNext(ArgumentMatchers.<Place>anyList());
        verify(placesScreen, never()).showPlaces(ArgumentMatchers.<Place>anyList());
        verify(placesPresenter).onError(any(Throwable.class));
        verify(placesScreen).onErrorRetrievingPlaces();
    }


    private List<GSPlaceResult> getRandomPlaces() {
        List<GSPlaceResult> placeResults = new ArrayList<>();
        for (int i = 0; i < totalPlaces; i++) {
            placeResults.add(new GSPlaceResult("" + i, "name" + i, new PlaceResponseGeometry(15.0, 15.0)));
        }
        return placeResults;
    }

}
