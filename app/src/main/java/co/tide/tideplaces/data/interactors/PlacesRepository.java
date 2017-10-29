package co.tide.tideplaces.data.interactors;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

import co.tide.tideplaces.data.models.ResultListener;
import co.tide.tideplaces.data.responses.PlaceResponse;
import co.tide.tideplaces.data.rest.ApiService;
import co.tide.tideplaces.di.scopes.ActivityScope;
import io.reactivex.functions.Consumer;

@ActivityScope
public class PlacesRepository implements Repository<List<PlaceResponse>>, Consumer<LatLng> {


    public ApiService apiService;

    @Inject
    public PlacesRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void data(ResultListener<List<PlaceResponse>> listener) {

    }

    @Override
    public void accept(LatLng latLng) throws Exception {

    }


}
