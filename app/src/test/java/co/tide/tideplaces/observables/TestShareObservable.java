package co.tide.tideplaces.observables;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

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

    Observer<TestObject> observer1, observer2;

    ArgumentCaptor<TestObject> placeObserver1Captor = ArgumentCaptor.forClass(TestObject.class);
    ArgumentCaptor<TestObject> placeObserver2Captor = ArgumentCaptor.forClass(TestObject.class);

    @Before
    public void setUp() {
        observer1 = spy(new Observer<TestObject>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(TestObject place) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        observer2 = spy(new Observer<TestObject>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(TestObject o) {

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
        ConnectableObservable observable = Observable.just(new TestObject()).share().replay();
        observable.subscribeWith(observer1);
        observable.connect();
        observable.subscribeWith(observer2);

        verify(observer1).onNext(placeObserver1Captor.capture());
        verify(observer2).onNext(placeObserver2Captor.capture());
        Assert.assertEquals(placeObserver1Captor.getValue(), placeObserver2Captor.getValue());
    }


    private class TestObject {

    }
}
