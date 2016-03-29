package com.ibraheem.ibraheem.widget;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ibraheem.ibraheem.KsaToKwiFragment;
import com.ibraheem.ibraheem.KwiToKsaFragment;

/**
 * Created by anirban on 22/03/2016.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter {
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new KsaToKwiFragment();
        else if (position == 1)
            return new KwiToKsaFragment();
        else
            return null;
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
            return "";
    }
}
