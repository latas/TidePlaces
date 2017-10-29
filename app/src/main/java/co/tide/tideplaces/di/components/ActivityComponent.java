package co.tide.tideplaces.di.components;


import co.tide.tideplaces.di.modules.ActivityModule;
import co.tide.tideplaces.di.modules.LocationModule;
import co.tide.tideplaces.di.scopes.ActivityScope;
import co.tide.tideplaces.ui.PlacesActivity;
import dagger.Component;

@ActivityScope
@Component(modules = {ActivityModule.class, LocationModule.class}, dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(PlacesActivity activity);
}
