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



}
