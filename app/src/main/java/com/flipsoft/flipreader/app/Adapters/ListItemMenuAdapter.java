package com.flipsoft.flipreader.app.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.flipsoft.flipreader.app.R;

/**
 * Created by Flipelunico on 12-10-16.
 */

public class ListItemMenuAdapter extends CursorAdapter{ /*
    Etiqueta de Depuración
     */
    private static final String TAG = "Prueba";

    public ListItemMenuAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_menu_item, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        // Extract properties from cursor
        String label = cursor.getString(cursor.getColumnIndexOrThrow("LABEL"));
        // Populate fields with extracted properties
        tvTitle.setText(label);

    }

}
