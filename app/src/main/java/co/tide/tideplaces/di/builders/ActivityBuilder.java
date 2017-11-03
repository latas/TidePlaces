package co.tide.tideplaces.di.builders;

import co.tide.tideplaces.di.modules.ActivityModule;
import co.tide.tideplaces.di.scopes.ActivityScope;
import co.tide.tideplaces.ui.PlacesActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {
    @ActivityScope
    @ContributesAndroidInjector(modules = {ActivityModule.class,
            MapFragmentProvider.class, ListFragmentProvider.class})
    abstract PlacesActivity bindPlacesActivity();
}
