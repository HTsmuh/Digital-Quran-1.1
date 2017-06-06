package com.horizontech.biz.digitalquran.Adapter;

/**
 * Created by HP on 5/4/2017.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.horizontech.biz.digitalquran.Fragments.ParaFragment;
import com.horizontech.biz.digitalquran.Fragments.SurahFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new SurahFragment();
            case 1:
                return new ParaFragment();
            case 2:
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
