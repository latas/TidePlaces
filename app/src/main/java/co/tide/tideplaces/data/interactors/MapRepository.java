package co.tide.tideplaces.data.interactors;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class MapRepository implements Repository<GoogleMap> {
    final MapView mapView;


    public MapRepository(MapView mapView) {
        this.mapView = mapView;
    }


    @Override
    public Observable<GoogleMap> data() {


        return Observable.create(new ObservableOnSubscribe<GoogleMap>() {
            @Override
            public void subscribe(final ObservableEmitter<GoogleMap> e) throws Exception {
                mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        e.onNext(googleMap);
                    }
                });
            }
        });


    }


}
