package co.tide.tideplaces.di.components;


import co.tide.tideplaces.di.modules.ActivityModule;
import co.tide.tideplaces.di.modules.MapFragmentModule;
import co.tide.tideplaces.di.scopes.ActivityScope;
import co.tide.tideplaces.ui.PlacesActivity;
import co.tide.tideplaces.ui.fragments.PlacesListFragment;
import co.tide.tideplaces.ui.fragments.PlacesMapFragment;
import dagger.Component;

@ActivityScope
@Component(modules = {ActivityModule.class, MapFragmentModule.class}, dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(PlacesActivity activity);

    void inject(PlacesMapFragment fragment);

    void inject(PlacesListFragment fragment);
}
