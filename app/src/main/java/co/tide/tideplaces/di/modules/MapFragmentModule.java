package co.tide.tideplaces.di.modules;

import com.google.android.gms.maps.MapView;

import co.tide.tideplaces.di.scopes.ActivityScope;
import co.tide.tideplaces.ui.screens.UiMap;
import dagger.Module;
import dagger.Provides;

@Module
public class MapFragmentModule {

    final UiMap map;
    final MapView mapView;


    public MapFragmentModule(UiMap map, MapView mapView) {
        this.map = map;
        this.mapView = mapView;
    }

    @ActivityScope
    @Provides
    UiMap provides() {
        return map;
    }


    @ActivityScope
    @Provides
    MapView mapView() {
        return mapView;
    }

}
