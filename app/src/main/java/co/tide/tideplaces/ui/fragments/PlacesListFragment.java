package co.tide.tideplaces.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.tide.tideplaces.R;
import co.tide.tideplaces.data.models.PlacesView;
import co.tide.tideplaces.presenters.ListPlacesPresenter;
import co.tide.tideplaces.presenters.PlacesViewPresenter;


public class PlacesListFragment extends Fragment implements PlacesView {
    ListPlacesPresenter presenter = new ListPlacesPresenter();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment_layout, container, false);
        return view;
    }


    @Override
    public PlacesViewPresenter presenter() {
        return presenter;
    }
}
