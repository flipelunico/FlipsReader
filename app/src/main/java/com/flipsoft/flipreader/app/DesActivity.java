package com.flipsoft.flipreader.app;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flipsoft.flipreader.app.DB.FeedlyDB;

public class DesActivity extends Fragment{
    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_des, container, false);
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) view.findViewById(R.id.viewpagerContainer);
        Cursor c = FeedlyDB.getInstance(getContext()).getENTRIES();
        mPagerAdapter = new ViewPagerAdapter(getContext(),getFragmentManager(),c);
        mPager.setAdapter(mPagerAdapter);

        return view;
    }

}
