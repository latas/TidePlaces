package co.tide.tideplaces.data.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Antonis Latas
 */

public class MapItem {
    public final LatLng location;
    public final String title;
    public final String subTitle;

    public MapItem(LatLng location, String title, String subTitle) {
        this.location = location;
        this.title = title;
        this.subTitle = subTitle;
    }
}
