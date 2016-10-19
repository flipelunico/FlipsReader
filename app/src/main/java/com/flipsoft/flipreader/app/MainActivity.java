package com.flipsoft.flipreader.app;

import android.app.Activity;
import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;

import android.content.res.Configuration;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.flipsoft.flipreader.app.Adapter.DrawerListAdapter;
import com.flipsoft.flipreader.app.DB.DBScripts;
import com.flipsoft.flipreader.app.DB.FeedlyDB;
import com.flipsoft.flipreader.app.Parser.FeedlyParser;

public class MainActivity extends AppCompatActivity {

    /*
     DECLARACIONES
     */
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView ndList;

    private CharSequence activityTitle;
    private CharSequence itemTitle;
    //private String[] tagTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FeedlyParser.getInstance(this).get_categories();
        FeedlyParser.getInstance(this).get_entries();

        //Toolbar

        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        //Menu del Navigation Drawer

        ndList = (ListView)findViewById(R.id.navdrawerlist);

        final Cursor cCatergories = FeedlyDB.getInstance(this).getCATEGORIES();

        DrawerListAdapter dladap = new DrawerListAdapter(this, cCatergories);

        //ndList.setAdapter(ndMenuAdapter);
        ndList.setAdapter(dladap);

        ndList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Fragment fragment = null;

                switch (pos) {
                    case 0:
                        fragment = new FragmentFeeds();
                        break;
                    case 1:
                        fragment = new Fragment1();
                        break;
                    default:
                        fragment = new Fragment1();
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();

                ndList.setItemChecked(pos, true);

                cCatergories.moveToPosition(pos);
                getSupportActionBar().setTitle(cCatergories.getString(2));

                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

        //Drawer Layout

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.color_primary_dark));

        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}