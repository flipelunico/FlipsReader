package com.flipsoft.flipreader.app;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final String ARG_ID = "AnimalFragment:id";
    private Cursor mCursor;

    public ViewPagerAdapter(Context context, FragmentManager fm, Cursor cursor) {
        super(fm);
        this.mCursor = cursor;
    }


    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();

        mCursor.moveToPosition(position);
        args.putString("title", mCursor.getString(2));
        args.putString("content",mCursor.getString(3));
        args.putString("author",mCursor.getString(5));
        args.putString("origin_title",mCursor.getString(11));

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
        return mCursor.getCount();
    }
}
