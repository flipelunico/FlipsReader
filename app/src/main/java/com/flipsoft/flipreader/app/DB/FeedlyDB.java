package com.flipsoft.flipreader.app.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.flipsoft.flipreader.app.Parser.Category;
import com.flipsoft.flipreader.app.Parser.Subscription;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Flipelunico on 11-10-16.
 */

public class FeedlyDB extends SQLiteOpenHelper{

    // Mapeado rápido de indices
    private static final int CATEGORY_COLUMN_ID = 0;
    private static final int CATEGORY_COLUMN_LABEL = 1;
    /*
    Instancia singleton
    */
    private static FeedlyDB singleton;
    /*
    Etiqueta de depuración
     */
    private static final String TAG = FeedlyDB.class.getSimpleName();
    /*
    Nombre de la base de datos
     */
    public static final String DATABASE_NAME = "Feed.db";
    /*
    Versión actual de la base de datos
     */
    public static final int DATABASE_VERSION = 1;


    private FeedlyDB(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Retorna la instancia unica del singleton
     * @param context contexto donde se ejecutarán las peticiones
     * @return Instancia
     */
    public static synchronized FeedlyDB getInstance(Context context) {
        if (singleton == null) {
            singleton = new FeedlyDB(context.getApplicationContext());
        }
        return singleton;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla 'CATEGORY'
        db.execSQL(DBScripts.CREATE_CATEGORY);
        // Crear la tabla 'SUBSCRIPTIONS'
        db.execSQL(DBScripts.CREATE_SUBSCRIPTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Añade los cambios que se realizarán en el esquema
        db.execSQL("DROP TABLE IF EXISTS " + DBScripts.CATEGORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBScripts.SUBSCRIPTIONS_TABLE_NAME);
        onCreate(db);
    }

    /**
     * Obtiene todos los registros de la tabla entrada
     *
     * @return cursor con los registros
     */
    public Cursor getCATEGORIES() {
        // Seleccionamos todas las filas de la tabla 'categorias'
        return getWritableDatabase().rawQuery(
                "select rowid _id, * from " + DBScripts.CATEGORY_TABLE_NAME, null);

    }

    /**
     * Obtiene todos los registros de la tabla SUBSCRIPTIONS
     *
     * @return cursor con los registros
     */
    public Cursor getSUBSCRIPTIONS() {
        // Seleccionamos todas las filas de la tabla 'subscripciones'
        return getWritableDatabase().rawQuery(
                "select rowid _id, * from " + DBScripts.SUBSCRIPTIONS_TABLE_NAME, null);

    }

    /**
     * Inserta un registro en la tabla CATEGORY
     *
     * @param ID     ID de la categoria
     * @param LABEL  Label de la categoria
     */
    public void insertCATEGORY(
            String ID,
            String LABEL) {

        ContentValues values = new ContentValues();
        values.put(DBScripts.ColumnsCATEGORY.ID, ID);
        values.put(DBScripts.ColumnsCATEGORY.LABEL, LABEL);


        try{
            // Insertando el registro en la base de datos
            getWritableDatabase().insertOrThrow(
                    DBScripts.CATEGORY_TABLE_NAME,null, values
            );
        } catch (SQLiteConstraintException e){

        }
    }

    /**
     * Inserta un registro en la tabla SUBSCRIPTIONS
     *
     * @param ID     ID de la categoria
     * @param TITLE  Label de la categoria
     */
    public void insertSUBSCRIPTIONS(
            String ID,
            String TITLE,
            String WEBSITE,
            String CATEGORY_ID,
            String CATEGORY_LABEL,
            String UPDATED){

        ContentValues values = new ContentValues();
        values.put(DBScripts.ColumnsSUBSCRIPTION.ID, ID);
        values.put(DBScripts.ColumnsSUBSCRIPTION.TITLE, TITLE);
        values.put(DBScripts.ColumnsSUBSCRIPTION.WEBSITE, WEBSITE);
        values.put(DBScripts.ColumnsSUBSCRIPTION.CATEGORY_ID, CATEGORY_ID);
        values.put(DBScripts.ColumnsSUBSCRIPTION.CATEGORY_LABEL, CATEGORY_LABEL);
        values.put(DBScripts.ColumnsSUBSCRIPTION.UPDATED, UPDATED);


        try{
            // Insertando el registro en la base de datos
            getWritableDatabase().insertOrThrow(
                    DBScripts.SUBSCRIPTIONS_TABLE_NAME,null, values
            );
        } catch (SQLiteConstraintException e){

        }
    }


    /**
     * Modifica los valores de las columnas de una categoria
     *
     * @param ID          identificador de la categoria
     * @param LABEL       label de la categoria
     */
    public void updateCATEGORY(String ID,
                               String LABEL){

        ContentValues values = new ContentValues();
        values.put(DBScripts.ColumnsCATEGORY.ID, ID);
        values.put(DBScripts.ColumnsCATEGORY.LABEL, LABEL);


        // Modificar tabla SUBSCRIPTIONS
        getWritableDatabase().update(
                DBScripts.CATEGORY_TABLE_NAME,
                values,
                DBScripts.ColumnsCATEGORY.ID + "=?",
                new String[]{ID});

    }

    /**
     * Modifica los valores de las columnas de una subscripcion
     *
     * @param ID          identificador de la subscripcion
     * @param TITLE       label de la subscripcion
     */
    public void updateSUBSCRIPTIONS(String ID,
                               String TITLE,
                               String WEBSITE,
                               String CATEGORY_ID,
                               String CATEGORY_LABEL,
                               String UPDATED){

        ContentValues values = new ContentValues();
        values.put(DBScripts.ColumnsSUBSCRIPTION.ID, ID);
        values.put(DBScripts.ColumnsSUBSCRIPTION.TITLE, TITLE);
        values.put(DBScripts.ColumnsSUBSCRIPTION.WEBSITE, WEBSITE);
        values.put(DBScripts.ColumnsSUBSCRIPTION.CATEGORY_ID, CATEGORY_ID);
        values.put(DBScripts.ColumnsSUBSCRIPTION.CATEGORY_LABEL, CATEGORY_LABEL);
        values.put(DBScripts.ColumnsSUBSCRIPTION.UPDATED, UPDATED);

        // Modificar tabla SUBSCRIPTIONS
        getWritableDatabase().update(
                DBScripts.SUBSCRIPTIONS_TABLE_NAME,
                values,
                DBScripts.ColumnsSUBSCRIPTION.ID + "=?",
                new String[]{ID});

    }

    /**
     * Procesa una lista de items para su almacenamiento local
     * y sincronización.
     *
     * @param categories lista de categorias
     */
    public void syncCATEGORIES(List<Category> categories) {

        /* Se guardan las categorias en memoria*/

        HashMap<String, Category> entryMap = new HashMap<>();
        for (Category c : categories) {
            entryMap.put(c.get_id(), c);
        }

        /*Obtener las entradas locales*/

        Cursor c = getCATEGORIES();
        assert c != null;


        /*
        #3  Comenzar a comparar las categorias
        */
        String id;
        String label;

        while (c.moveToNext()) {

            id = c.getString(CATEGORY_COLUMN_ID);
            label = c.getString(CATEGORY_COLUMN_LABEL);

            Category match = entryMap.get(id);

            if (match != null) {
                // Si se encuentra la entrada la sacamos de memoria para luego no insertarla y que de duplicado
                entryMap.remove(id);

            /*
            #3.1 Comprobar si la entrada necesita ser actualizada
            */
                if ((match.get_label() != null && !match.get_label().equals(label))) {
                    // Actualizar entradas
                    updateCATEGORY(id, match.get_label());
                }
            }
        }
        c.close();


        /*
        #4 Añadir entradas nuevas
        */
        for (Category ca : categories) {

            insertCATEGORY(
                    ca.get_id(),
                    ca.get_label()

            );
        }
    }


    /**
     * Procesa una lista de items para su almacenamiento local
     * y sincronización.
     *
     * @param subscriptions lista de subscripciones
     */
    public void syncSUBSCRIPTIONS(List<Subscription> subscriptions) {

        /* Se guardan las categorias en memoria*/

        HashMap<String, Subscription> entryMap = new HashMap<>();
        for (Subscription s : subscriptions) {
            entryMap.put(s.get_id(), s);
        }

        /*Obtener las entradas locales*/

        Cursor c = getSUBSCRIPTIONS();
        assert c != null;


        /*
        #3  Comenzar a comparar las subscripciones
        */
        String id;

        while (c.moveToNext()) {

            id = c.getString(0);


            Subscription match = entryMap.get(id);

            if (match != null) {
                // Si se encuentra la entrada la sacamos de memoria para luego no insertarla y que de duplicado
                entryMap.remove(id);
            }
        }
        c.close();


        /*
        #4 Añadir entradas nuevas
        */
        for (Subscription su : subscriptions) {

            insertSUBSCRIPTIONS(
                    su.get_id(),
                    su.get_title(),
                    su.get_website(),
                    su.get_category_id(),
                    su.get_category_label(),
                    su.get_updated()

            );
        }
    }
}