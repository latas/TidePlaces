package co.tide.tideplaces.di.components;

import android.content.Context;

import co.tide.tideplaces.di.modules.ApplicationModule;
import co.tide.tideplaces.di.scopes.AppScope;
import dagger.Component;


@AppScope
@Component(modules = {ApplicationModule.class, ActivityModule.class})
public interface AppComponent {
    Context appContext();
}
