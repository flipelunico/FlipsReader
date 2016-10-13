package com.flipsoft.flipreader.app.DB;

/**
 * Created by Flipelunico on 11-10-16.
 */
public class DBScripts {
/*
Etiqueta para Depuración
 */
private static final String TAG = DBScripts.class.getSimpleName();

// Metainformación de la base de datos
public static final String CATEGORY_TABLE_NAME = "CATEGORY";
    public static final String SUBSCRIPTIONS_TABLE_NAME = "SUBSCRIPTIONS";
public static final String STRING_TYPE = "TEXT";
public static final String DATE_TYPE = "DATE";
public static final String INT_TYPE = "INTEGER";

// Campos de la tabla CATEGORY
    public static class ColumnsCATEGORY {
        public static final String ID = "ID";
        public static final String LABEL = "LABEL";
    }

    // Comando CREATE para la tabla CATEGORY
    public static final String CREATE_CATEGORY =
            "CREATE TABLE " + CATEGORY_TABLE_NAME + "(" +
                    ColumnsCATEGORY.ID + " " + STRING_TYPE + " primary key," +
                    ColumnsCATEGORY.LABEL + " " + STRING_TYPE  + ")";

    // Campos de la tabla SUBSCRIPTION
    public static class ColumnsSUBSCRIPTION {
        public static final String ID = "ID";
        public static final String TITLE = "TITLE";
        public static final String WEBSITE = "WEBSITE";
        public static final String CATEGORY_ID = "CATEGORY_ID";
        public static final String CATEGORY_LABEL = "CATEGORY_LABEL";
        public static final String UPDATED = "UPDATED";
    }

    // Comando CREATE para la tabla SUBSCRIPTIONS
    public static final String CREATE_SUBSCRIPTIONS =
            "CREATE TABLE " + SUBSCRIPTIONS_TABLE_NAME + "(" +
                    ColumnsSUBSCRIPTION.ID + " " + STRING_TYPE + " primary key," +
                    ColumnsSUBSCRIPTION.TITLE + " " + STRING_TYPE  + "," +
                    ColumnsSUBSCRIPTION.WEBSITE + " " + STRING_TYPE  + "," +
                    ColumnsSUBSCRIPTION.CATEGORY_ID + " " + STRING_TYPE  + "," +
                    ColumnsSUBSCRIPTION.CATEGORY_LABEL + " " + STRING_TYPE  + "," +
                    ColumnsSUBSCRIPTION.UPDATED + " " + STRING_TYPE  + ")";



}
