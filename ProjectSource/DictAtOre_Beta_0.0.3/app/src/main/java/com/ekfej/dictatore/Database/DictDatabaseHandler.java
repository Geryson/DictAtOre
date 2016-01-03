package com.ekfej.dictatore.Database;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import android.util.Log;
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
            Log.i("Database connect", "Sikeres kapcsolat");
        }

        return instance;
    }


    /**
     * Adatbázishoz való csatlakozás
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Adatbázishoz való csatlakozás megszüntetése
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    //endregion



}





