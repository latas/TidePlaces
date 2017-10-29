package co.tide.tideplaces;

import android.app.Application;

import co.tide.tideplaces.di.components.AppComponent;
import co.tide.tideplaces.di.components.DaggerAppComponent;
import co.tide.tideplaces.di.modules.ApplicationModule;

public class TideApp extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

    }

    public AppComponent component() {
        return appComponent;
    }


}
