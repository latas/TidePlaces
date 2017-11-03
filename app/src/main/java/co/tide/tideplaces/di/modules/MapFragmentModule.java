package co.tide.tideplaces.di.modules;

import co.tide.tideplaces.ui.fragments.PlacesMapFragment;
import co.tide.tideplaces.ui.screens.UiMap;
import dagger.Module;
import dagger.Provides;

@Module
public class MapFragmentModule {
    @Provides
    UiMap provideUiMap(PlacesMapFragment map) {
        return map;
    }

}
