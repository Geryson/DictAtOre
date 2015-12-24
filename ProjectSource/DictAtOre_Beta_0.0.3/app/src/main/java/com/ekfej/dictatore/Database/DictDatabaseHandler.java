package com.ekfej.dictatore.Database;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import android.widget.AdapterView;

    public class DictDatabaseHandler {
    private SQLiteOpenHelper openHelper;
    public SQLiteDatabase database;
    private static DictDatabaseHandler instance;

    //region importálás
    DictDatabaseHandler(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DictDatabaseHandler getInstance(Context context) {
        if (instance == null) {
            instance = new DictDatabaseHandler(context);
        }

        return instance;
    }


    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a Lister of quotes
     */

    //endregion



}





