package co.tide.tideplaces.presenters;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import co.tide.tideplaces.data.interactors.MapRepository;
import co.tide.tideplaces.rxscheduler.BaseSchedulerProvider;
import co.tide.tideplaces.ui.screens.UiMap;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class MapLoaderPresenter implements Consumer<GoogleMap> {

    final MapView mapView;
    final UiMap map;
    private final BaseSchedulerProvider provider;
    final CompositeDisposable disposables = new CompositeDisposable();

    public MapLoaderPresenter(MapView mapView, UiMap map, BaseSchedulerProvider provider) {
        this.mapView = mapView;
        this.map = map;
        this.provider = provider;
    }

    public void loadMap() {
        disposables.add(new MapRepository(mapView).data().subscribe(this));

    }

    @Override
    public void accept(GoogleMap googleMap) throws Exception {
        map.onMapLoaded(googleMap);
    }

    public void drain() {
        disposables.clear();
    }
}
