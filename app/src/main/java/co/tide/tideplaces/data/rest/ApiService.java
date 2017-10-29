package co.tide.tideplaces.data.rest;


import java.util.List;

import co.tide.tideplaces.data.responses.PlaceResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {
    @GET("maps/api/place/nearbysearch/json")
    Observable<List<PlaceResponse>> getPlaces(@Query("location") String latLang, @Query("radius") String radius,
                                              @Query("type") String type, @Query("key") String apiKey);

}
