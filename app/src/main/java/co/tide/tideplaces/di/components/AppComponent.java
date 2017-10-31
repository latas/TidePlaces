package co.tide.tideplaces.di.components;

import co.tide.tideplaces.data.rest.ApiService;
import co.tide.tideplaces.data.rest.params.ConstantParams;
import co.tide.tideplaces.di.modules.ApplicationModule;
import co.tide.tideplaces.di.modules.NetworkModule;
import co.tide.tideplaces.di.modules.ParamsModule;
import co.tide.tideplaces.di.scopes.AppScope;
import co.tide.tideplaces.rxscheduler.BaseSchedulerProvider;
import dagger.Component;


@AppScope
@Component(modules = {ApplicationModule.class, ParamsModule.class, NetworkModule.class})
public interface AppComponent {

    BaseSchedulerProvider scheduler();

    ApiService service();

    ConstantParams exposeParams();

}
