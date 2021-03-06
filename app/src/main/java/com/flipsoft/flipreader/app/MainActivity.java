package com.flipsoft.flipreader.app;



import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.flipsoft.flipreader.app.Adapter.FeedCursorAdapter;
import com.flipsoft.flipreader.app.DB.FeedlyDB;
import com.flipsoft.flipreader.app.Parser.FeedlyParser;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener, SlidingPaneLayout.PanelSlideListener{

    /*
     DECLARACIONES
     */
    private DrawerLayout drawer;
    private TabLayout tabLayout;
    //private String[] pageTitle = {"Fragment 1", "Fragment 2", "Fragment 3"};
    private ListView feedList;
    private SlidingPaneLayout mPanes;
    private static final int PARALLAX_SIZE = 5;
    /**
     * Activity title
     */
    private CharSequence mTitle;

    /**
     * Current title depending on the selected animal
     */
    private CharSequence mCurrentTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FeedlyDB.getInstance(this).deleteAllEntries();

        FeedlyParser.getInstance(this).get_categories();

        FeedlyParser.getInstance(this).setCustomParserListener(new FeedlyParser.ParserListener() {
            @Override
            public void onParserReady(String continuation) {
                Log.i("Flipelunico","Evento ya termino parseo: " + continuation);
            }
        });

        Cursor cCat = FeedlyDB.getInstance(this).getCATEGORIES();
        while (cCat.moveToNext()) {
            FeedlyParser.getInstance(this).get_entries(cCat.getString(1));
        }
        cCat.close();



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);

        setSupportActionBar(toolbar);

        //create default navigation drawer toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        //handling navigation view item event
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);
        final Menu menu = navigationView.getMenu();


        Cursor cur = FeedlyDB.getInstance(this).getCATEGORIES();
        for(cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()){
            String name = cur.getString(2);
            menu.add(name);
        }



        feedList = (ListView) findViewById(R.id.feedList);
        feedList.setOnItemClickListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        String iName = item.getTitle().toString();

        //if (iName.equals("Android")) {
            Cursor c = FeedlyDB.getInstance(this).getENTRIES(iName);
            int numero = c.getCount();
            Log.i("Flipelunico","Navigation Item Selected, numero de entries: "+ numero);
            FeedCursorAdapter feedCA = new FeedCursorAdapter(this,c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            feedList.setAdapter(feedCA);
          //TODO: launch activty
        //}

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        Intent intent = new Intent(this, DesActivity.class);
        intent.putExtra("string", "Go to other Activity by NavigationView item cliked!");
        startActivity(intent);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

    }



    private void openPane() {
        mPanes.openPane();
        getSupportActionBar().setTitle(mTitle);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    private void closePane() {
        mPanes.closePane();
        getSupportActionBar().setTitle(mCurrentTitle);

        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void onBackPressed() {
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        //Toast.makeText(this,"Panel Slide",Toast.LENGTH_SHORT);
    }

    @Override
    public void onPanelOpened(View panel) {
        //Toast.makeText(this,"Panel Abierto",Toast.LENGTH_SHORT);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void onPanelClosed(View panel) {
        //Toast.makeText(this,"Panel Cerrado",Toast.LENGTH_SHORT);

    }
}