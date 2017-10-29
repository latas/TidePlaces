package co.tide.tideplaces.di.modules;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import co.tide.tideplaces.R;
import co.tide.tideplaces.data.rest.ApiService;
import co.tide.tideplaces.di.scopes.AppScope;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module(includes = ApplicationModule.class)
public class NetworkModule {


    @Provides
    @AppScope
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .build();
    }

    @Provides
    @AppScope
    public Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    @AppScope
    public Retrofit retrofit(OkHttpClient okHttpClient, Gson gson, Context context) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(context.getString(R.string.api_base_url))
                .build();
    }

    @Provides
    @AppScope
    public ApiService service(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

}
