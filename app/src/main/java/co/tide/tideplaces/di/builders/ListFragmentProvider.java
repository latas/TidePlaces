package co.tide.tideplaces.di.builders;

import co.tide.tideplaces.di.modules.ListFragmentModule;
import co.tide.tideplaces.ui.fragments.PlacesListFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ListFragmentProvider {
    @ContributesAndroidInjector(modules = ListFragmentModule.class)
    abstract PlacesListFragment providePlacesListFragment();

}
