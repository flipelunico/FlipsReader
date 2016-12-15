package com.flipsoft.flipreader.app;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

import com.flipsoft.flipreader.app.Adapter.FeedCursorAdapter;
import com.flipsoft.flipreader.app.DB.FeedlyDB;

/**
 * Created by Flipelunico on 19-10-16.
 */

public class FragmentFeeds extends ListFragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_article, container,
                false);

        //Cursor c = FeedlyDB.getInstance(getContext()).getENTRIES();
        //FeedCursorAdapter Fc = new FeedCursorAdapter(getContext(),c, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        //setListAdapter(Fc);



        return rootView;
    }



}
