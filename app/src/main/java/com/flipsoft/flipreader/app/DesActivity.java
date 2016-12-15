package com.flipsoft.flipreader.app;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

public class DesActivity extends FragmentActivity{
    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_des);


        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.viewpagerContainer);
        //TODO; arreglar esto
        //Cursor c = FeedlyDB.getInstance(getApplicationContext()).getENTRIES();
        //mPagerAdapter = new ViewPagerAdapter(getApplicationContext(),getSupportFragmentManager(),c);
        //mPager.setAdapter(mPagerAdapter);

    }

}
