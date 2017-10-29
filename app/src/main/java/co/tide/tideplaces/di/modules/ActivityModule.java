package co.tide.tideplaces.di.modules;

import android.content.Context;
import android.support.v4.app.FragmentManager;

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
    public String[] tabsTitle() {
        return new String[]{
                context().getResources().getString(R.string.tab0_title),
                context().getResources().getString(R.string.tab1_title)
        };
    }

    @ActivityScope
    @Provides
    public FragmentManager fragmentManager() {
        return placesActivity.getSupportFragmentManager();
    }


    @ActivityScope
    @Provides
    public int[] tabsIcons() {
        return new int[]{R.drawable.list_indicator, R.drawable.map_indicator};
    }


    @ActivityScope
    @Provides
    public LocationScreen placesScreen() {
        return placesActivity;
    }
}
