package co.tide.tideplaces.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import co.tide.tideplaces.ui.fragments.PlacesMapFragment;
import co.tide.tideplaces.ui.fragments.PlacesListFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    final String[] fragmentsTitle;

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
        return 2;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsTitle[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    public int getItemPosition(Object object) {

        return POSITION_NONE;
    }
}
