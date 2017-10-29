package co.tide.tideplaces.data.rest;

import com.google.android.gms.maps.model.LatLng;

public class GooglePlacesParams implements QueryParams {
    String[] params;

    final LatLng point;
    final private String apiKey;

    public GooglePlacesParams(LatLng point, String apiKey) {
        this.point = point;
        this.apiKey = apiKey;
    }

    String pointParam() {
        return point.latitude + "," + point.longitude;
    }


    String token() {
        return apiKey;
    }

    String type() {
        return "bar";
    }

    String radius() {
        return "5000";
    }

    @Override
    public String[] params() {
        return params;
    }

    @Override
    public QueryParams build() {
        params = new String[]{pointParam(), token(), type()};
        return this;
    }
}
