package com.ekfej.dictatore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
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
     * @return a List of quotes
     */
    public List<String> LanguageSelect() {
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

    public List<String> WordsSelect(String Name) {  //nem tökéletes
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select word from words where nyelv = (select ID from languages where Name =\"" + Name + "\")", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    //saját

    public boolean WordInsert(String word, int Id, int meaning, int Language_ID) {

        try {
            String sql = "insert into Words (Id, word, meaning, nyelv) values (" + Id + ", \"" + word + "\" ," + meaning + ", " + Language_ID + ")";
            database.execSQL(sql);
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean LanguageInsert(String Name, int Id) {

        try {

            /* Ez is működik (beépített insert fgv), de már megtaláltam hol írhatom be a saját sql parancsaim :D
            ContentValues cv = new ContentValues();
            cv.put("Id", Id);
            cv.put("Name", Name);
            database.insert("Languages", "Id", cv);
            */
            String sql="insert into Languages (ID, Name) values (" + Id + ", \"" + Name + "\")" ;
            database.execSQL(sql);

            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean LanguageUpdate(String NewName,String OldName) {

        try {
            String sql = "update Languages SET Name= \"" + NewName + "\" where Name= \"" + OldName + "\"";
            database.execSQL(sql);
            return true;
        }
        catch(SQLException e) {
            e.printStackTrace();
            return  false;
        }
    }
    public boolean LanguageDelete(String Name) {
        try {
            String sql = "delete from Languages where Name= \"" + Name + "\"";
            database.execSQL(sql);
        /*
        ContentValues cv = new ContentValues();
        cv.put("Id", Id);
        cv.put("Name", Name);
        database.delete("Language", Name, cv);
        */
            return true;
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

