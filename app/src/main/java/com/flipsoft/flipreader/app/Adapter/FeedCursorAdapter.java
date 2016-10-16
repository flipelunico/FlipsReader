package com.flipsoft.flipreader.app.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.flipsoft.flipreader.app.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Flipelunico on 14-10-16.
 */

public class FeedCursorAdapter extends CursorAdapter {

    /*
    Etiqueta de Depuración
     */
    private static final String TAG = FeedCursorAdapter.class.getSimpleName();

    /**
     * View holder para evitar multiples llamadas de findViewById()
     */
    static class ViewHolder {
        TextView timestamp;
        TextView titulo;
        TextView descripcion;
        NetworkImageView imagen;

        int tituloI;
        int descripcionI;
        int imagenI;
        int timestampI;
        int canalI;
    }

    public FeedCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.feed_item, null, false);

        ViewHolder vh = new ViewHolder();

        // Almacenar referencias
        vh.titulo = (TextView) view.findViewById(R.id.item_titulo);
        vh.descripcion = (TextView) view.findViewById(R.id.item_contenido);
        vh.imagen = (NetworkImageView) view.findViewById(R.id.item_icono);
        vh.timestamp = (TextView) view.findViewById(R.id.item_timestamp);

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


        if (v8 != "") {
            Long fecha = Long.parseLong(v8);
        }


        Date pub_date = new Date(fecha);

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String formattedDate = dateFormat.format(pub_date);

        vh.timestamp.setText(formattedDate + " - " + v11);

        // Setear el texto al titulo
        vh.titulo.setText(cursor.getString(2));


        String content = cursor.getString(3);
        String summary = cursor.getString(4);

        String descripcion;

        if (summary != ""){
            descripcion = summary;
        }else {
            descripcion = content;
        }


        String noHTML1 = descripcion.replaceAll("\\<.*?>","");
        String noHTML2 = noHTML1.replaceAll("&.*?;","");
        String noHTML3 = noHTML2.replace("{\"content\":","");
        descripcion = noHTML3;

        int ln =0;
        // Obtener acceso a la descripción y su longitud
        if (descripcion != null) {
            ln = cursor.getString(3).length();
        }




        // Acortar descripción a 77 caracteres
        if (ln >= 150)
            vh.descripcion.setText(descripcion.substring(0, 150)+"...");
        else vh.descripcion.setText(descripcion);

        // Obtener URL de la imagen
        //String thumbnailUrl = cursor.getString(vh.imagenI);


        //Log.i("Flipelunico","thumburl: " + thumbnailUrl);
        // Obtener instancia del ImageLoader
        //ImageLoader imageLoader = RedSingleton.getInstance(context).getImageLoader();

        // Volcar datos en el image view
        //vh.imagen.setImageUrl(thumbnailUrl, imageLoader);

    }
}