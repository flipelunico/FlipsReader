package com.flipsoft.flipreader.app.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.flipsoft.flipreader.app.R;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Flipelunico on 14-10-16.
 */

public class FeedCursorAdapter extends CursorAdapter {

    /*
    Etiqueta de Depuración
     */
    private static final String TAG = FeedCursorAdapter.class.getSimpleName();
    //private ImageLoader imgLoader;
    private Context mContext;
    private LayoutInflater inflater;

    /**
     * View holder para evitar multiples llamadas de findViewById()
     */
    static class ViewHolder {
        TextView timestamp;
        TextView page;
        TextView titulo;
        TextView descripcion;
        //NetworkImageView imagen;
        ImageView favicon;

        int tituloI;
        int descripcionI;
        int imagenI;
        int timestampI;
        int canalI;
    }

    public FeedCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //imgLoader = new ImageLoader(mContext);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.feed_item, null, false);

        ViewHolder vh = new ViewHolder();

        // Almacenar referencias
        vh.titulo = (TextView) view.findViewById(R.id.item_titulo);
        vh.descripcion = (TextView) view.findViewById(R.id.item_contenido);
        vh.timestamp = (TextView) view.findViewById(R.id.item_timestamp);
        vh.page = (TextView) view.findViewById(R.id.item_page);
        vh.favicon = (ImageView)  view.findViewById(R.id.item_favicon);
        view.setTag(vh);

        return view;
    }



    public void bindView(View view, Context context, Cursor cursor) {

        final ViewHolder vh = (ViewHolder) view.getTag();

        String v0 = cursor.getString(0);
        String v1 = cursor.getString(1);
        String v2 = cursor.getString(2);
        String v3 = cursor.getString(3);
        String v4 = cursor.getString(4);
        String v5 = cursor.getString(5); //Author
        String v6 = cursor.getString(6);
        String v7 = cursor.getString(7);
        String v8 = cursor.getString(8);
        String v9 = cursor.getString(9);
        String v10 = cursor.getString(10);
        String v11 = cursor.getString(11);
        String v12 = cursor.getString(12);
        String v13 = cursor.getString(13);
        String v14 = cursor.getString(14);
        String v15 = cursor.getString(15);
        String v16 = cursor.getString(16);

        Log.i("Flipelunico","bindview autor es : "+ v5);

        //TODO: sacar esto el error es de la bd
        if (v8.length() == 0){
            v8 = "1111111111111";
        }
        Long fecha;
        String formattedDate = "";
        if (v8 != "") {
            fecha = Long.parseLong(v8);
            Date pub_date = new Date(fecha);

            SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm");
            formattedDate = dateFormat.format(pub_date);
        }

        vh.timestamp.setText(" · " + formattedDate );
        vh.page.setText(v11);

        // Setear el texto al titulo
        vh.titulo.setText(v2);
        //vh.titulo.setTextSize(4 *  context.getResources().getDisplayMetrics().density);

        String content = v3;
        String summary = v4;

        String descripcion;

        if (summary != ""){
            descripcion = summary;
        }else {
            descripcion = content;
        }

        String noHTML1 = descripcion.replaceAll("\\<.*?>","");
        String noHTML2 = noHTML1.replaceAll("&.*?;","");
        String noHTML3 = noHTML2.replace("{\"content\":\"","");
        String noHTML4 = noHTML3.replaceAll("\\\\n","");
        descripcion = noHTML4;

        int ln =0;
        // Obtener acceso a la descripción y su longitud
        if (descripcion != null) {
            ln = v3.length();
        }

        // Acortar descripción a 110 caracteres
        if (ln >= 110)
            vh.descripcion.setText(descripcion.substring(0, 110)+"...");
        else vh.descripcion.setText(descripcion);

        //vh.descripcion.setTextSize(4 *  context.getResources().getDisplayMetrics().density);

        String url_favicon = "http://www.google.com/s2/favicons?domain_url=" + v12;

        //imgLoader.DisplayImage(url_favicon, vh.favicon);

        Glide
                .with(context)
                .load(url_favicon)
                .into(vh.favicon);

    }
}