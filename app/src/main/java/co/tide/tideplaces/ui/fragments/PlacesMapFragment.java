package co.tide.tideplaces.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.tide.tideplaces.R;
import co.tide.tideplaces.data.models.PlacesView;
import co.tide.tideplaces.presenters.MapPresenter;
import co.tide.tideplaces.ui.screens.UiMap;


public class PlacesMapFragment extends Fragment implements UiMap, PlacesView, OnMapReadyCallback {

    @BindView(R.id.mapView)
    MapView mapView;

    @Inject
    MapPresenter mapPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment_layout, container, false);
        ButterKnife.bind(this, view);

        new MapPresenter(mapView, this).loadMap();

        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }





    @Override
    public void onMapLoaded(GoogleMap googleMap) {

    }


    @Override
    public void showPlaces() {

    }
}
