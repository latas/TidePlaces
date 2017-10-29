package co.tide.tideplaces.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.tide.tideplaces.R;
import co.tide.tideplaces.TideApp;
import co.tide.tideplaces.di.components.ActivityComponent;
import co.tide.tideplaces.di.components.DaggerActivityComponent;
import co.tide.tideplaces.di.modules.ActivityModule;
import co.tide.tideplaces.presenters.LocationPresenter;
import co.tide.tideplaces.ui.adapters.ViewPagerAdapter;
import co.tide.tideplaces.ui.screens.LocationScreen;

public class PlacesActivity extends AppCompatActivity implements LocationScreen {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Inject
    LocationPresenter locationPresenter;


    private int[] tabIcons = {
            R.drawable.list_indicator,
            R.drawable.map_indicator,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityComponent activityComponent = DaggerActivityComponent.builder()
                .appComponent(((TideApp) getApplication()).component()).activityModule(new ActivityModule(this, this)).build();
        activityComponent.inject(this);

        setContentView(R.layout.activity_places);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getResources().getString(R.string.toolbar_title));
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), new String[]{getResources().getString(R.string.tab0_title),
                getResources().getString(R.string.tab1_title)

        }));

        tabLayout.setupWithViewPager(viewPager);
        addTabIcons();

        locationPresenter.locateMe();
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
    public void onLocationSetFailed(int message) {
        Toast.makeText(this, getResources().getString(message), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onLocationRetrieved(Location location) {

    }

    @Override
    public void startLocating() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            locationPresenter.locateMe();
        } else showErrorMessage(R.string.permission_denied);
    }

    private void showErrorMessage(int message) {
        Toast.makeText(this, getString(message), Toast.LENGTH_LONG).show();
    }
}
