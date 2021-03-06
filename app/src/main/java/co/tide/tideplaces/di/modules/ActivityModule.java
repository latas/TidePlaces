package co.tide.tideplaces.di.modules;


import android.support.v4.app.FragmentManager;

import co.tide.tideplaces.R;
import co.tide.tideplaces.data.events.PermissionsAcceptedEvent;
import co.tide.tideplaces.di.scopes.ActivityScope;
import co.tide.tideplaces.ui.PlacesActivity;
import co.tide.tideplaces.ui.screens.Screen;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;

@Module
public class ActivityModule {

    @ActivityScope
    @Provides
    public Screen placesScreen(PlacesActivity placesActivity) {
        return placesActivity;
    }


    @ActivityScope
    @Provides
    public Observable<PermissionsAcceptedEvent> observable(PlacesActivity placesActivity) {
        return placesActivity.observable;
    }


    @ActivityScope
    @Provides
    public String[] tabsTitle(PlacesActivity placesActivity) {
        return new String[]{
                placesActivity.getResources().getString(R.string.tab0_title),
                placesActivity.getResources().getString(R.string.tab1_title)
        };
    }

    @ActivityScope
    @Provides
    public FragmentManager fragmentManager(PlacesActivity placesActivity) {
        return placesActivity.getSupportFragmentManager();
    }


    @ActivityScope
    @Provides
    public int[] tabsIcons() {
        return new int[]{R.drawable.list_indicator, R.drawable.map_indicator};
    }


}