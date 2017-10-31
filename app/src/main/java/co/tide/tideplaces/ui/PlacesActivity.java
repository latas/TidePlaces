package co.tide.tideplaces.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.tide.tideplaces.R;
import co.tide.tideplaces.TideApp;
import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.di.components.ActivityComponent;
import co.tide.tideplaces.di.components.DaggerActivityComponent;
import co.tide.tideplaces.di.modules.ActivityModule;
import co.tide.tideplaces.presenters.PlacesPresenter;
import co.tide.tideplaces.ui.adapters.ViewPagerAdapter;
import co.tide.tideplaces.ui.screens.PlacesScreen;

public class PlacesActivity extends AppCompatActivity implements PlacesScreen {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    @Inject
    PlacesPresenter placesPresenter;
    @Inject
    ViewPagerAdapter adapter;
    @Inject
    int[] tabIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityComponent activityComponent = DaggerActivityComponent.builder()
                .appComponent(((TideApp) getApplication()).component()).activityModule(new ActivityModule(this)).build();
        activityComponent.inject(this);

        setContentView(R.layout.activity_places);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getResources().getString(R.string.toolbar_title));
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        addTabIcons();


        placesPresenter.showPlaces();

    }

    private void addTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }


    @Override
    public void requestLocationPermission() {

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        }, 1);

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            placesPresenter.showPlaces();
        } else showErrorMessage(R.string.permission_denied);
    }

    private void showErrorMessage(int message) {
        Toast.makeText(this, getString(message), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showPlaces(List<Place> places) {

    }

    @Override
    public void onErrorRetrievingPlaces(int message) {
        Toast.makeText(this, getString(message), Toast.LENGTH_LONG).show();
    }
}



