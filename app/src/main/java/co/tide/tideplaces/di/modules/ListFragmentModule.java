package co.tide.tideplaces.di.modules;

import co.tide.tideplaces.ui.fragments.PlacesListFragment;
import co.tide.tideplaces.ui.screens.ListScreen;
import dagger.Module;
import dagger.Provides;

@Module
public class ListFragmentModule {
    @Provides
    ListScreen providePlacesListFragment(PlacesListFragment fragment) {
        return fragment;
    }


}
