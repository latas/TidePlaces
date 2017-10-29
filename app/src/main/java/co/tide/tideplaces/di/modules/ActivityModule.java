package co.tide.tideplaces.di.modules;

import android.content.Context;

import javax.inject.Named;

import co.tide.tideplaces.di.scopes.ActivityScope;
import co.tide.tideplaces.ui.screens.LocationScreen;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    final Context activityContext;
    final LocationScreen locationScreen;


    public ActivityModule(Context activityContext, LocationScreen locationScreen) {
        this.activityContext = activityContext;
        this.locationScreen = locationScreen;
    }

    @ActivityScope
    @Provides
    @Named("activity_context")
    public Context context() {
        return activityContext;
    }

    @ActivityScope
    @Provides

    public LocationScreen locationScreen() {
        return locationScreen;
    }
}
