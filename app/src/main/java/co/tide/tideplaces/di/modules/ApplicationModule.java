package co.tide.tideplaces.di.modules;

import android.content.Context;

import javax.inject.Named;

import co.tide.tideplaces.R;
import co.tide.tideplaces.di.scopes.AppScope;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    final Context appContext;

    public ApplicationModule(Context appContext) {
        this.appContext = appContext;
    }

    @AppScope
    @Provides
    public Context context() {
        return appContext;
    }

    @Named("apiKey")
    @Provides
    @AppScope
    public String apiKey(Context context) {
        return context.getResources().getString(R.string.api_key);
    }
}
