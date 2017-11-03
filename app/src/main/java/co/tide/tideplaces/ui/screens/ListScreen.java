package co.tide.tideplaces.ui.screens;

import android.net.Uri;

import java.util.List;

import co.tide.tideplaces.data.models.ListItem;

public interface ListScreen {
    void show(List<ListItem> listItems);

    public void openGoogleMaps(Uri uri);
}
