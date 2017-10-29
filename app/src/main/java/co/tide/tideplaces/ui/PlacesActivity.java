package co.tide.tideplaces.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.tide.tideplaces.R;
import co.tide.tideplaces.TideApp;
import co.tide.tideplaces.di.components.ActivityComponent;
import co.tide.tideplaces.di.components.DaggerActivityComponent;
import co.tide.tideplaces.ui.adapters.ViewPagerAdapter;

public class PlacesActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    ActivityComponent activityComponent;


    private int[] tabIcons = {
            R.drawable.list_indicator,
            R.drawable.map_indicator,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = DaggerActivityComponent.builder().appComponent(((TideApp) getApplication()).component()).build();
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
    }

    private void addTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    public ActivityComponent activityComponent() {
        return activityComponent;
    }
}
