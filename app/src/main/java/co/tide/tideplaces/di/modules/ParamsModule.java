package co.tide.tideplaces.di.modules;


import android.content.Context;

import javax.inject.Named;

import co.tide.tideplaces.R;
import co.tide.tideplaces.di.scopes.AppScope;
import dagger.Module;
import dagger.Provides;

@Module
public class ParamsModule {

    @AppScope
    @Provides
    @Named("apiKey")
    public String apiKey(Context context) {
        return context.getResources().getString(R.string.api_key);
    }



    @AppScope
    @Provides
    @Named("places_type")
    public String type(Context context) {
        return context.getResources().getString(R.string.places_type);
    }


    @AppScope
    @Provides
    @Named("radius")
    public long radius(Context context) {
        return context.getResources().getInteger(R.integer.search_radius);
    }

}
