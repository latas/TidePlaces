package co.tide.tideplaces.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import co.tide.tideplaces.R;
import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.presenters.PlacesPresenter;
import co.tide.tideplaces.ui.PlacesActivity;
import co.tide.tideplaces.ui.screens.DataScreen;


public class PlacesListFragment extends Fragment implements DataScreen {
    @Inject
    PlacesPresenter presenter;

    @Override
    public void onAttach(Context context) {
        ((PlacesActivity) context).component().inject(this);
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment_layout, container, false);
        presenter.addUiDelegate(this);
        return view;
    }


    @Override
    public void onDetach() {
        presenter.removeUiDelegate(this);
        super.onDetach();
    }


    @Override
    public void showData(List<Place> places) {

    }
}
