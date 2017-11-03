package co.tide.tideplaces.presenters;

import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;

import javax.inject.Inject;

import co.tide.tideplaces.data.models.MapItem;
import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.rxscheduler.BaseSchedulerProvider;
import co.tide.tideplaces.ui.screens.UiMap;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class MapPresenter implements Consumer<List<Place>> {


    final UiMap map;
    final BaseSchedulerProvider provider;
    final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public MapPresenter(UiMap map, BaseSchedulerProvider provider) {
        this.map = map;
        this.provider = provider;
    }


    private LatLngBounds bounds(List<Place> places) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Place place : places) {
            builder.include(place.location());
        }

        return builder.build();
    }


    public void drain() {
        disposables.clear();
    }

    @Override
    public void accept(List<Place> places) throws Exception {
        for (Place place : places) {
            if (place.isMyLocation())
                map.addMyPoi(place.location());
            else
                map.addPoi(new MapItem(place.location(), place.name(), place.distanceFromAnchor() + "m"));
        }
        if (places.size() > 0)
            map.zoomInBounds(bounds(places));
    }
}
