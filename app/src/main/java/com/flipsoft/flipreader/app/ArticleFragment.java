package com.flipsoft.flipreader.app;

/**
 * Created by Flipelunico on 12-10-16.
 */


import android.app.Fragment;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.flipsoft.flipreader.app.Adapter.FeedCursorAdapter;
import com.flipsoft.flipreader.app.DB.FeedlyDB;


public class ArticleFragment extends Fragment {
    public static final String ARG_ARTICLES_NUMBER = "articles_number";
    private ProgressBar progressbar = null;
    private ListView feedListView = null;
    private View _rootView;
    private FeedCursorAdapter adaptador;

    public ArticleFragment() {
        // Constructor vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article, container, false);

        this._rootView = rootView;

        int i = getArguments().getInt(ARG_ARTICLES_NUMBER);
        String article = getResources().getStringArray(R.array.Tags)[i];

        getActivity().setTitle(article);
        //updateList();
        return rootView;

    }



}
