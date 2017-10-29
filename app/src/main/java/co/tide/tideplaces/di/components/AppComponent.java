package co.tide.tideplaces.di.components;

import android.content.Context;

import javax.inject.Named;

import co.tide.tideplaces.data.rest.ApiService;
import co.tide.tideplaces.di.modules.ApplicationModule;
import co.tide.tideplaces.di.modules.NetworkModule;
import co.tide.tideplaces.di.scopes.AppScope;
import dagger.Component;


@AppScope
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface AppComponent {
    Context appContext();

    ApiService exposeService();

    @Named("apiKey")
    String exposeApiKey();
}
