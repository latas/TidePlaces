package co.tide.tideplaces.observables;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.data.models.Venue;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Created by Antonis Latas
 */

public class TestShareObservable {

    Observer<Place> observer1, observer2;

    ArgumentCaptor<Place> placeObserver1Captor = ArgumentCaptor.forClass(Place.class);
    ArgumentCaptor<Place> placeObserver2Captor = ArgumentCaptor.forClass(Place.class);

    @Before
    public void setUp() {
        observer1 = spy(new Observer<Place>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Place place) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        observer2 = spy(new Observer<Place>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Place o) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Test
    public void testShareObservable() {
        ConnectableObservable observable = Observable.just(new Venue("id", "name", new LatLng(10, 10))).share().replay();
        observable.subscribeWith(observer1);
        observable.connect();
        observable.subscribeWith(observer2);

        verify(observer1).onNext(placeObserver1Captor.capture());
        verify(observer2).onNext(placeObserver2Captor.capture());
        Assert.assertEquals(placeObserver1Captor.getValue(), placeObserver2Captor.getValue());
    }


}
