package co.tide.tideplaces.data.models;

import android.view.View;

public class ListItem {
    final String name;
    final String distance;
    final View.OnClickListener clickListener;

    public ListItem(String name, String distance, View.OnClickListener clickListener) {
        this.name = name;
        this.distance = distance;
        this.clickListener = clickListener;
    }

    public String name() {
        return name;
    }

    public String distance() {
        return distance;
    }

    public View.OnClickListener itemClickListener() {
        return clickListener;
    }
}
