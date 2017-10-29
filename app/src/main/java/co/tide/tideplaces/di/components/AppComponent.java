package co.tide.tideplaces.di.components;

import co.tide.tideplaces.data.interactors.PlacesRepository;
import co.tide.tideplaces.di.modules.ApplicationModule;
import co.tide.tideplaces.di.modules.NetworkModule;
import co.tide.tideplaces.di.modules.ParamsModule;
import co.tide.tideplaces.di.scopes.AppScope;
import dagger.Component;


@AppScope
@Component(modules = {ApplicationModule.class,ParamsModule.class, NetworkModule.class})
public interface AppComponent {

    PlacesRepository exposeRepository();
}
