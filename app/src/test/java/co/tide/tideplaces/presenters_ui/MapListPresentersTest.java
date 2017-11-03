package co.tide.tideplaces.presenters_ui;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.tide.tideplaces.data.models.MapItem;
import co.tide.tideplaces.data.models.MyPlace;
import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.data.models.Venue;
import co.tide.tideplaces.presenters.ListPresenter;
import co.tide.tideplaces.presenters.MapPresenter;
import co.tide.tideplaces.rxscheduler.BaseSchedulerProvider;
import co.tide.tideplaces.ui.screens.ListScreen;
import co.tide.tideplaces.ui.screens.UiMap;
import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Antonis Latas
 */

public class MapListPresentersTest {
    @Mock
    MapView mapView;
    @Mock
    UiMap map;
    @Mock
    ListScreen screen;
    @Mock
    BaseSchedulerProvider provider;

    MapPresenter mapPresenter;
    ListPresenter listPresenter;
    ArgumentCaptor<List> mapListCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<List> listListCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<List> listItemsListCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<LatLng> myPosCaptor = ArgumentCaptor.forClass(LatLng.class);

    ArgumentCaptor<MapItem> placesCaptor = ArgumentCaptor.forClass(MapItem.class);
    private int totalPlaces = 20;

    @Before
    public void setUp() {
        initMocks(this);
        mapPresenter = spy(new MapPresenter(mapView, map, provider));
        listPresenter = spy(new ListPresenter(screen));
    }

    @Test
    public void testRandomPlacesWithoutMine() {
        ConnectableObservable placesObservable = Observable.just(getRandomPlaces()).share().replay();
        placesObservable.subscribeWith(mapPresenter);
        placesObservable.subscribeWith(listPresenter);
        placesObservable.connect();
        verify(mapPresenter).onNext(mapListCaptor.capture());
        verify(listPresenter).onNext(listListCaptor.capture());
        verify(screen).show(listItemsListCaptor.capture());
        verify(map, never()).addMyPoi(myPosCaptor.capture());
        verify(map, times(totalPlaces)).addPoi(placesCaptor.capture());

        Assert.assertEquals(totalPlaces, mapListCaptor.getValue().size());
        Assert.assertEquals(totalPlaces, listListCaptor.getValue().size());
        Assert.assertEquals(totalPlaces, listItemsListCaptor.getValue().size());

        for (int i = 0; i < totalPlaces; i++) {
            Assert.assertEquals(getRandomPlaces().get(i).location().latitude, placesCaptor.getAllValues().get(i).location.latitude, 0);
            Assert.assertEquals(getRandomPlaces().get(i).location().longitude, placesCaptor.getAllValues().get(i).location.longitude, 0);
        }

    }

    @Test
    public void testRandomPlacesIncludingMine() {


        ConnectableObservable placesObservable =
                Observable.just(getRandomPlaces(), Collections.singletonList(myPlace())).share().replay();

        placesObservable.subscribeWith(mapPresenter);
        placesObservable.subscribeWith(listPresenter);
        placesObservable.connect();

        verify(mapPresenter, times(2)).onNext(mapListCaptor.capture());
        verify(listPresenter, times(2)).onNext(listListCaptor.capture());

        verify(screen, times(2)).show(listItemsListCaptor.capture());

        verify(map).addMyPoi(myPosCaptor.capture());
        verify(map, times(totalPlaces)).addPoi(placesCaptor.capture());


        Assert.assertEquals(totalPlaces, mapListCaptor.getAllValues().get(0).size());
        Assert.assertEquals(1, mapListCaptor.getAllValues().get(1).size());

        Assert.assertEquals(totalPlaces, listListCaptor.getAllValues().get(0).size());
        Assert.assertEquals(1, listListCaptor.getAllValues().get(1).size());

        Assert.assertEquals(totalPlaces, listItemsListCaptor.getAllValues().get(0).size());
        Assert.assertEquals(0, listItemsListCaptor.getAllValues().get(1).size());

        Assert.assertEquals(myPlace().location(), myPosCaptor.getValue());


        for (int i = 0; i < totalPlaces; i++) {
            Assert.assertEquals(getRandomPlaces().get(i).location(), placesCaptor.getAllValues().get(i).location);
        }

    }


    @Test
    public void testPlacesOnlyMine() {


        ConnectableObservable placesObservable =
                Observable.just(Collections.singletonList(myPlace())).share().replay();

        placesObservable.subscribeWith(mapPresenter);
        placesObservable.subscribeWith(listPresenter);
        placesObservable.connect();
        verify(mapPresenter).onNext(mapListCaptor.capture());
        verify(listPresenter).onNext(listListCaptor.capture());

        verify(screen).show(listItemsListCaptor.capture());

        verify(map).addMyPoi(myPosCaptor.capture());
        verify(map, never()).addPoi(any(MapItem.class));


        Assert.assertEquals(1, listListCaptor.getValue().size());

        Assert.assertEquals(1, mapListCaptor.getValue().size());
        assert (listItemsListCaptor.getValue().isEmpty());

        Assert.assertEquals(myPlace().location(), myPosCaptor.getValue());


    }

    @Test
    public void noPlacesAtAll_Error() {

        ConnectableObservable placesObservable =
                Observable.error(new Throwable()).share().replay();
        placesObservable.subscribeWith(mapPresenter);
        placesObservable.subscribeWith(listPresenter);
        placesObservable.connect();
        verify(mapPresenter, never()).onNext(ArgumentMatchers.<Place>anyList());
        verify(listPresenter, never()).onNext(ArgumentMatchers.<Place>anyList());
        verify(listPresenter).onError(any(Throwable.class));
        verify(mapPresenter).onError(any(Throwable.class));

        verifyZeroInteractions(screen);
        verifyZeroInteractions(map);
    }

    @Test
    public void noPlacesAtAll_Empty() {
        ConnectableObservable placesObservable =
                Observable.just(Collections.emptyList()).share().replay();
        placesObservable.subscribeWith(mapPresenter);
        placesObservable.subscribeWith(listPresenter);
        placesObservable.connect();
        verify(mapPresenter).onNext(mapListCaptor.capture());
        verify(listPresenter).onNext(listListCaptor.capture());

        verify(screen).show(listItemsListCaptor.capture());

        verify(map, never()).addMyPoi(any(LatLng.class));
        verify(map, never()).addPoi(any(MapItem.class));


        assert (mapListCaptor.getValue().isEmpty());
        assert (listListCaptor.getValue().isEmpty());
        assert (listItemsListCaptor.getValue().isEmpty());

    }


    @Test
    public void noPlacesOnlyMine_withError() {
        ConnectableObservable placesObservable =
                Observable.mergeDelayError(Observable.just(Collections.singletonList(myPlace())), Observable.error(new Throwable())).share().replay();

        placesObservable.subscribeWith(mapPresenter);
        placesObservable.subscribeWith(listPresenter);
        placesObservable.connect();
        verify(mapPresenter).onNext(mapListCaptor.capture());
        verify(listPresenter).onNext(listListCaptor.capture());

        verify(mapPresenter).onError(any(Throwable.class));
        verify(listPresenter).onError(any(Throwable.class));

        verify(screen).show(listItemsListCaptor.capture());

        verify(map, never()).addPoi(any(MapItem.class));
        verify(map).addMyPoi(myPosCaptor.capture());

        Assert.assertEquals(1, listListCaptor.getValue().size());
        Assert.assertEquals(1, mapListCaptor.getValue().size());
        assert (listItemsListCaptor.getValue().isEmpty());
        Assert.assertEquals(myPlace().location(), myPosCaptor.getValue());


    }

    private Place myPlace() {
        return new MyPlace(new LatLng(22, 23));
    }

    private List<Place> getRandomPlaces() {
        List<Place> places = new ArrayList<>();
        for (int i = 0; i < totalPlaces; i++) {
            places.add(new Venue("id" + i, "name" + i, new LatLng(10, 10), 10f));
        }
        return places;
    }
}
