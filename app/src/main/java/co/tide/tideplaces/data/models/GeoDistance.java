package co.tide.tideplaces.data.models;

import com.google.android.gms.maps.model.LatLng;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by Antonis Latas
 */

public class GeoDistance {
    final LatLng startPoint;
    final LatLng endPoint;

    public GeoDistance(LatLng startPoint, LatLng endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }


    public Float meters() {
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(endPoint.latitude - startPoint.latitude);
        double lngDiff = Math.toRadians(endPoint.longitude - startPoint.longitude);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(Math.toRadians(startPoint.latitude)) * Math.cos(Math.toRadians(endPoint.latitude)) *
                        Math.sin(lngDiff / 2) * Math.sin(lngDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        int meterConversion = 1609;
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);


        return Float.valueOf(df.format((distance * meterConversion)));
    }
}
