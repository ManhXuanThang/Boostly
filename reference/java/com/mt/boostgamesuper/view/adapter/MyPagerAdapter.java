package com.mt.boostgamesuper.view.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mt.boostgamesuper.view.fragment.BoostProTab;
import com.mt.boostgamesuper.view.fragment.BootgameFragment;
import com.mt.boostgamesuper.view.fragment.ScanphoneFragment;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;
    private boolean isScan;

    public MyPagerAdapter(FragmentManager fragmentManager, boolean isScan) {
        super(fragmentManager);
        this.isScan = isScan;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return ScanphoneFragment.newInstance(0, isScan);
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return BootgameFragment.newInstance(null);

            case 2: // Fragment # 1 - This will show SecondFragment
                return BoostProTab.newInstance(null);
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

    public void updateData(boolean isScan) {
        this.isScan = isScan;
        notifyDataSetChanged();
    }


}