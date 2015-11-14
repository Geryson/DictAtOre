package com.ekfej.dictatore;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Buda Viktor
 */
    public class DictDatabaseHandler {
    Context context;
    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(null);

    public List<String> LanguageSelect() {

        databaseAccess.open();
        List<String> quotes = databaseAccess.LanguageSelect();
        databaseAccess.close();
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Name FROM Languages", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    //saját

    public boolean LanguageInsert(String Name, int Id) {

        ContentValues cv = new ContentValues();
        cv.put("Id", Id);
        cv.put("Name", Name);
        //String sql="insert into Languages (ID, Name) values (" + Id + Name + ")" ;
        //Cursor cursor = database.rawQuery(sql, null);
        database.insert("Languages", "Id", cv);
        return true;
    }
    public boolean LanguageUpdate(String NewName,String OldName) {
        String sql="update Languages SET Name= " + NewName + " where Name= " + OldName ;
        Cursor cursor = database.rawQuery(sql, null);
        return true;
    }
    public boolean LanguageDelete(String Name) {
        String sql="delete from Languages where Name= " + Name ;
        Cursor cursor = database.rawQuery(sql, null);
        return true;
    }
}


















/*
    public class DictDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DictDatabaseV2.db";
    private static final String TABLE_LANGUAGES = "Languages";
    private static final String TABLE_WORDS = "Words";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    public DictDatabaseHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
        public void onCreate(SQLiteDatabase db) {
        String CREATE_LANGUAGES_TABLE = "CREATE TABLE " +
                TABLE_LANGUAGES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME
                + " TEXT," + ")";
        db.execSQL(CREATE_LANGUAGES_TABLE);

    }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LANGUAGES);
            onCreate(db);
        }

    public void addLanguages(Languages languages) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, languages.getName());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_LANGUAGES, null, values);
        db.close();
    }
    public Languages findLanguages(String name) {
        String query = "Select * FROM " + TABLE_LANGUAGES + " WHERE " + COLUMN_NAME + " =  \"" + name + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Languages languages = new Languages();

        if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                languages.setID(Integer.parseInt(cursor.getString(0)));
                languages.setName(cursor.getString(1));
                cursor.close();
        } else {
            languages = null;
        }
        db.close();
        return languages;
    }
    public boolean deleteLanguages(String name) {

        boolean result = false;

        String query = "Select * FROM " + TABLE_LANGUAGES + " WHERE " + COLUMN_NAME + " =  \"" + name + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Languages languages = new Languages();

        if (cursor.moveToFirst()) {
            languages.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_LANGUAGES, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(languages.getID()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
}
*/






