package co.tide.tideplaces.di.modules;

import android.app.Application;
import android.content.Context;

import co.tide.tideplaces.di.scopes.AppScope;
import co.tide.tideplaces.rxscheduler.BaseSchedulerProvider;
import co.tide.tideplaces.rxscheduler.SchedulerProvider;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    @Provides
    @AppScope
    Context provideContext(Application application) {
        return application;
    }

    @AppScope
    @Provides
    BaseSchedulerProvider scheduler(SchedulerProvider provider) {
        return provider;
    }

}
