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
        updateList();
        return rootView;

    }

    public void updateList() {
        feedListView = (ListView) _rootView.findViewById(R.id.lista);
        feedListView.setVisibility(View.VISIBLE);
        //progressbar.setVisibility(View.GONE);



        // Crear el adaptador
        adaptador = new FeedCursorAdapter(
                getActivity().getApplicationContext(),
                FeedlyDB.getInstance(getActivity().getApplicationContext()).getENTRIES(),
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        // Relacionar la lista con el adaptador

        feedListView.setAdapter(adaptador);

        feedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,	long id) {
                Object o = feedListView.getItemAtPosition(position);
                SQLiteCursor cr = (SQLiteCursor) o;

                /*
                int tituloI = cr.getColumnIndex(ScriptDatabase.ColumnEntradas.TITULO);
                int timestampI = cr.getColumnIndex(ScriptDatabase.ColumnEntradas.FECHA_PUB);
                int descripcionI = cr.getColumnIndex(ScriptDatabase.ColumnEntradas.DESCRIPCION);
                int imagenI = cr.getColumnIndex(ScriptDatabase.ColumnEntradas.URL);
                int canalI = cr.getColumnIndex(ScriptDatabase.ColumnEntradas.CANAL);

                FeedItem newsData = new FeedItem();
                newsData.setTitle(cr.getString(tituloI));

                newsData.setContent(cr.getString(descripcionI));

                newsData.setAttachmentUrl(cr.getString(imagenI));

                Intent intent = new Intent(MainActivity.this, FeedDetailsActivity.class);
                intent.putExtra("feed", newsData);
                startActivity(intent);*/
            }
        });
    }

}
