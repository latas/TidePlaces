package co.tide.tideplaces.di.modules;

import android.content.Context;

import javax.inject.Named;

import co.tide.tideplaces.di.scopes.ActivityScope;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    final Context activityContext;


    public ActivityModule(Context activityContext) {
        this.activityContext = activityContext;
    }

    @ActivityScope
    @Provides
    @Named("activity_context")
    public Context context() {
        return activityContext;
    }
}
