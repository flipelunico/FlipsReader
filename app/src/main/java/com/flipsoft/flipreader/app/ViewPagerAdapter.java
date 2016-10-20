package com.flipsoft.flipreader.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final String ARG_ID = "AnimalFragment:id";

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putInt(ARG_ID, position);


        Fragment fragment = new Fragment1();
        fragment.setArguments(args);
        return fragment;

        /*
        if (position ==0) {
            Fragment fragment = new Fragment1();
            fragment.setArguments(args);
            return fragment;
        } else if (position == 1) {
            return new Fragment1();
        } else return new Fragment1();
        */
    }

    @Override
    public int getCount() {
        return 3;
    }
}
