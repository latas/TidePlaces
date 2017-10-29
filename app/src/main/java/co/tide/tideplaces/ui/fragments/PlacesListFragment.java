package co.tide.tideplaces.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.tide.tideplaces.R;
import co.tide.tideplaces.data.models.PlacesView;


public class PlacesListFragment extends Fragment implements PlacesView {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment_layout, container, false);
        return view;
    }

    @Override
    public void showPlaces() {

    }
}
