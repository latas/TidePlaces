package co.tide.tideplaces.data.models;

import android.view.View;

public class ListItem {
    final String name;
    final View.OnClickListener clickListener;

    public ListItem(String name, View.OnClickListener clickListener) {
        this.name = name;
        this.clickListener = clickListener;
    }
}
