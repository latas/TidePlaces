package co.tide.tideplaces.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.tide.tideplaces.R;
import co.tide.tideplaces.presenters.MapPresenter;
import co.tide.tideplaces.presenters.PlacesViewPresenter;
import co.tide.tideplaces.rxscheduler.SchedulerProvider;
import co.tide.tideplaces.ui.screens.UiMap;


public class PlacesMapFragment extends Fragment implements UiMap {

    @BindView(R.id.mapView)
    MapView mapView;
    MapPresenter mapPresenter;
    GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        mapView.onCreate(savedInstanceState);
        mapPresenter = new MapPresenter(mapView, this, new SchedulerProvider());
        mapPresenter.loadMap();
        return view;
    }


    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }


    @Override
    public void onMapLoaded(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    @Override
    public void addMyPoi(LatLng latLng) {

        googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }


    @Override
    public boolean isLoaded() {
        return googleMap != null;
    }

    @Override
    public void zoomInBounds(LatLngBounds bounds) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) getResources().getDimension(R.dimen.map_bounds_padding)));
    }

    @Override
    public void addPoi(LatLng location, String title, float distance) {
        googleMap.addMarker(new MarkerOptions()
                .position(location)
                .snippet(title + " " + distance)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    }


    @Override
    public PlacesViewPresenter presenter() {
        return mapPresenter;
    }
}
