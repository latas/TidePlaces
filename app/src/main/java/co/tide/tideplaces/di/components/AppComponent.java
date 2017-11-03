package co.tide.tideplaces.di.components;

import android.app.Application;

import co.tide.tideplaces.TideApp;
import co.tide.tideplaces.di.builders.ActivityBuilder;
import co.tide.tideplaces.di.modules.ApplicationModule;
import co.tide.tideplaces.di.modules.NetworkModule;
import co.tide.tideplaces.di.modules.ParamsModule;
import co.tide.tideplaces.di.scopes.AppScope;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;


@AppScope
@Component(modules = {AndroidInjectionModule.class, ActivityBuilder.class,
        ApplicationModule.class, ParamsModule.class, NetworkModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(TideApp app);

}
