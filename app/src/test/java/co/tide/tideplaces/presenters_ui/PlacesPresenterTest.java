package co.tide.tideplaces.presenters_ui;

import com.google.android.gms.maps.model.LatLng;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.tide.tideplaces.BaseTest;
import co.tide.tideplaces.R;
import co.tide.tideplaces.data.interactors.PlacesRepository;
import co.tide.tideplaces.data.models.MyPlace;
import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.data.models.RxException;
import co.tide.tideplaces.data.models.Venue;
import co.tide.tideplaces.data.models.error.ErrorCodes;
import co.tide.tideplaces.data.models.error.UnAuthorizedLocationError;
import co.tide.tideplaces.presenters.PlacesPresenter;
import co.tide.tideplaces.ui.screens.Screen;
import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.any;
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
    PlacesRepository placesRepository;

    PlacesPresenter placesPresenter;
    LatLng myLocation = new LatLng(10, 10);

    ArgumentCaptor<RxException> rxExceptionCaptor = ArgumentCaptor.forClass(RxException.class);
    ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);


    @Before
    public void setUp() {
        initMocks(this);
        placesPresenter = spy(new PlacesPresenter(screen, placesRepository, schedulersProvider));
    }


    @Test
    public void presenterTest_RandomPlacesReturned() {

        doReturn(Observable.just(randomPlaces(), Arrays.asList(new Place[]{new MyPlace(myLocation)}))).when(placesRepository).data();
        placesPresenter.retrievePlaces();
        testScheduler.triggerActions();
        verify(placesPresenter, times(2)).onNext(ArgumentMatchers.<Place>anyList());
        verify(screen).showProgress();
        verify(screen, times(1)).hideProgress();
        verifyNoMoreInteractions(screen);
        verify(placesPresenter, never()).onError(any(Throwable.class));

    }


    @Test
    public void presenterTest_NoLocationPermission() {

        doReturn(Observable.error(new RxException(new UnAuthorizedLocationError()))).when(placesRepository).data();
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
    public void presenterTest_OnlyMyLocationReturned() {
        doReturn(Observable.mergeDelayError(Observable.just(Arrays.asList(new Place[]{new MyPlace(myLocation)})), Observable.error(new Throwable()))).when(placesRepository).data();

        placesPresenter.retrievePlaces();
        testScheduler.triggerActions();
        verify(placesPresenter).onNext(listCaptor.capture());
        verify(placesPresenter).onError(any(Throwable.class));
        verify(screen).showProgress();
        verify(screen).hideProgress();
        verify(screen).onErrorRetrievingPlaces(R.string.general_error_retrieving_places);
        verifyNoMoreInteractions(screen);
        Assert.assertEquals(1, listCaptor.getValue().size());
    }


    private List<Place> randomPlaces() {
        List<Place> placeResults = new ArrayList<>();
        for (int i = 0; i < totalPlaces; i++) {
            placeResults.add(new Venue("id" + i, "name" + i, new LatLng(10.0, 10.0)));
        }
        return placeResults;
    }

}
