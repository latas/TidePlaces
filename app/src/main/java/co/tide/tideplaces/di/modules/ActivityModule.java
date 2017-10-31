package co.tide.tideplaces.di.modules;

import android.support.v4.app.FragmentManager;

import co.tide.tideplaces.R;
import co.tide.tideplaces.di.scopes.ActivityScope;
import co.tide.tideplaces.ui.PlacesActivity;
import co.tide.tideplaces.ui.screens.PlacesScreen;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {


    final PlacesActivity placesActivity;


    public ActivityModule(PlacesActivity placesActivity) {

        this.placesActivity = placesActivity;
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
                placesActivity.getResources().getString(R.string.tab0_title),
                placesActivity.getResources().getString(R.string.tab1_title)
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

}
