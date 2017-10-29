package co.tide.tideplaces.di.modules;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import javax.inject.Named;

import co.tide.tideplaces.di.scopes.ActivityScope;
import dagger.Module;
import dagger.Provides;

@Module(includes = {ActivityModule.class})
public class LocationModule {

    @ActivityScope
    @Provides
    public GoogleApiClient googleApiClient(@Named("activity_context") Context context) {
        return new GoogleApiClient.Builder(context).addApi(LocationServices.API).build();
    }



}
