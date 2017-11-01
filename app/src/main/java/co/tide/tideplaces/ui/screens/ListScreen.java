package co.tide.tideplaces.ui.screens;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import co.tide.tideplaces.data.models.ListItem;

public interface ListScreen extends DataScreen {
    void show(List<ListItem> listItems);

    public void openGoogleMaps(LatLng location);
}
