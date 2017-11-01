package co.tide.tideplaces.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import co.tide.tideplaces.data.models.PlacesView;
import co.tide.tideplaces.ui.fragments.PlacesListFragment;
import co.tide.tideplaces.ui.fragments.PlacesMapFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    final String[] fragmentsTitle;
    final int fragmentsCount = 2;
    SparseArray<PlacesView> registeredPlacesViews = new SparseArray<>();

    @Inject
    public ViewPagerAdapter(FragmentManager fm, String[] fragmentsTitle) {
        super(fm);
        this.fragmentsTitle = fragmentsTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return position == 0 ? new PlacesListFragment() : new PlacesMapFragment();


    }

    @Override
    public int getCount() {
        return fragmentsCount;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        if (fragment instanceof PlacesView)
            registeredPlacesViews.put(position, ((PlacesView) fragment));
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsTitle[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredPlacesViews.remove(position);
        super.destroyItem(container, position, object);
    }

    public List<PlacesView> placesViews() {
        List<PlacesView> placesViews = new ArrayList<>();
        for (int i = 0; i < registeredPlacesViews.size(); i++) {
            int key = registeredPlacesViews.keyAt(i);
            placesViews.add(registeredPlacesViews.get(key));
        }
        return placesViews;
    }

    public int getItemPosition(Object object) {

        return POSITION_NONE;
    }
}
