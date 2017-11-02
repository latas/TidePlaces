package co.tide.tideplaces.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import co.tide.tideplaces.R;
import co.tide.tideplaces.data.models.ListItem;
import co.tide.tideplaces.presenters.ListPresenter;
import co.tide.tideplaces.presenters.PlacesPresenter;
import co.tide.tideplaces.presenters.UiPresenter;
import co.tide.tideplaces.ui.PlacesActivity;
import co.tide.tideplaces.ui.screens.ListScreen;


public class PlacesListFragment extends Fragment implements ListScreen {
    @Inject
    PlacesPresenter presenter;

    UiPresenter uiPresenter;

    @Override
    public void onAttach(Context context) {
        ((PlacesActivity) context).component().inject(this);
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        presenter.removeUiDelegate(uiPresenter);
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment_layout, container, false);
        uiPresenter = new ListPresenter(this);
        presenter.addUiDelegate(uiPresenter);
        return view;
    }




    @Override
    public void show(List<ListItem> listItems) {

    }

    @Override
    public void openGoogleMaps(LatLng location) {
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", location.latitude, location.longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        getActivity().startActivity(intent);
    }
}
