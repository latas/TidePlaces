package co.tide.tideplaces.di.modules;

import android.content.Context;

import javax.inject.Named;

import co.tide.tideplaces.R;
import co.tide.tideplaces.di.scopes.ActivityScope;
import co.tide.tideplaces.ui.PlacesActivity;
import co.tide.tideplaces.ui.screens.LocationScreen;
import co.tide.tideplaces.ui.screens.PlacesScreen;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    final Context activityContext;
    final PlacesActivity placesActivity;


    public ActivityModule(Context activityContext, PlacesActivity placesActivity) {
        this.activityContext = activityContext;
        this.placesActivity = placesActivity;

    }

    @ActivityScope
    @Provides
    @Named("activity_context")
    public Context context() {
        return activityContext;
    }

    @ActivityScope
    @Provides
    public PlacesScreen locationScreen() {
        return placesActivity;
    }



    @ActivityScope
    @Provides
    public LocationScreen placesScreen() {
        return placesActivity;
    }
}
