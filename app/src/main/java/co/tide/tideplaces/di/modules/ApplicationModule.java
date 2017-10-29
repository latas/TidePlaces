package co.tide.tideplaces.di.modules;

import android.content.Context;

import co.tide.tideplaces.di.scopes.AppScope;
import co.tide.tideplaces.rxscheduler.BaseSchedulerProvider;
import co.tide.tideplaces.rxscheduler.SchedulerProvider;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    final Context appContext;

    public ApplicationModule(Context appContext) {
        this.appContext = appContext;
    }

    @AppScope
    @Provides
    public Context context() {
        return appContext;
    }
    @AppScope
    @Provides
    BaseSchedulerProvider scheduler(SchedulerProvider provider) {
        return provider;
    }

}
