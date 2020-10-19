package cn.appoa.afbase.adapter;


import android.view.ViewGroup;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * ViewPager中放Fragment的适配器
 */
public class AfPagerAdapter extends FragmentPagerAdapter {

    protected List<Fragment> listFragment;
    protected List<String> listTitle;

    public AfPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public AfPagerAdapter(FragmentManager fm, List<Fragment> listFragment) {
        super(fm);
        this.listFragment = listFragment;
    }

    public AfPagerAdapter(FragmentManager fm, List<Fragment> listFragment, List<String> listTitle) {
        super(fm);
        this.listFragment = listFragment;
        this.listTitle = listTitle;
    }

    @Override
    public Fragment getItem(int position) {
        if (listFragment == null) {
            return null;
        } else {
            return listFragment.get(position);
        }
    }

    @Override
    public int getCount() {
        if (listFragment == null) {
            return 0;
        } else {
            return listFragment.size();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (listTitle == null) {
            return "";
        } else {
            return listTitle.get(position);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
    }

    public void setPageTitle(int position, String title) {
        if (listTitle != null && position >= 0 && position < listTitle.size()) {
            listTitle.set(position, title);
            notifyDataSetChanged();
        }
    }
}
