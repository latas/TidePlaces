package co.tide.tideplaces.di.builders;

import co.tide.tideplaces.di.modules.MapFragmentModule;
import co.tide.tideplaces.ui.fragments.PlacesMapFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MapFragmentProvider {
    @ContributesAndroidInjector(modules = MapFragmentModule.class)
    abstract PlacesMapFragment providePlacesMapFragment();
}
