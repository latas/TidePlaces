package co.tide.tideplaces;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import co.tide.tideplaces.di.components.AppComponent;
import co.tide.tideplaces.di.components.DaggerAppComponent;
import co.tide.tideplaces.di.modules.ApplicationModule;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class TideApp extends Application implements HasActivityInjector{

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);

    }



    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
