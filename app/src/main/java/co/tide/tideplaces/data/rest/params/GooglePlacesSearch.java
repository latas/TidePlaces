package co.tide.tideplaces.data.rest.params;

import com.google.android.gms.maps.model.LatLng;

public class GooglePlacesSearch implements SearchQuery {
    final ConstantParams constantParams;
    final LatLng point;
    String params[];

    public GooglePlacesSearch(LatLng point, ConstantParams constanstParams) {
        this.point = point;
        this.constantParams = constanstParams;
    }

    String pointParam() {
        return point.latitude + "," + point.longitude;
    }


    @Override
    public String[] params() {
        return params;
    }

    @Override
    public SearchQuery build() {
        params = new String[]{pointParam(), String.valueOf(constantParams.radius), constantParams.type, constantParams.apiKey};
        return this;
    }
}
