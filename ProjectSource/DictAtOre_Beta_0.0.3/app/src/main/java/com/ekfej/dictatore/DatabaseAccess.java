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
        Cursor cursor = database.rawQuery("select word from words where Languages_ID = (select ID from languages where Name =\"" + Name + "\")", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    //saját

    public boolean WordInsert(String word, int meaning, int Language_ID) {

        try {
            String sql = "insert into Words (word, meaning, Language_ID) values (\"" + word + "\" ," + meaning + ", " + Language_ID + ")";
            database.execSQL(sql);
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean LanguageInsert(String Name) {

        try {

            /* Ez is működik (beépített insert fgv), de már megtaláltam hol írhatom be a saját sql parancsaim :D
            ContentValues cv = new ContentValues();
            cv.put("Id", Id);
            cv.put("Name", Name);
            database.insert("Languages", "Id", cv);
            */
            String sql="insert into Languages ( Name) values (\"" + Name + "\")" ;
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
    public boolean WordsWordUpdate(String NewWord, String OldWord, int Meaning, int Language_ID) {

        try {
            String sql = "update words Set word=\""+ NewWord + "\"  where word=\"" + OldWord + "\" and Languages_ID=" + Language_ID + "and meaning =" + Meaning;
            database.execSQL(sql);
            return true;
        }
        catch(SQLException e) {
            e.printStackTrace();
            return  false;
        }
    }
    public boolean WordsMeaningUpdate(int NewMeaning, int OldMeaning, String Word, int Language_ID) {

        try {
            String sql = "update words Set meaning="+ NewMeaning + " where meaning=" + OldMeaning + " and Languages_ID=" + Language_ID + "and word =\"" + Word + "\"";
            database.execSQL(sql);
            return true;
        }
        catch(SQLException e) {
            e.printStackTrace();
            return  false;
        }
    }
    public boolean WordsLanguages_IDUpdate(int NewLanguages_ID, int OldLanguages_ID, int Meaning, String Word) {

        try {
            String sql = "update words Set Languages_ID="+ NewLanguages_ID + "  where Languages_ID=" + OldLanguages_ID + " and meaning=" + Meaning + "and word=\"" + Word + "\"";
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

            return true;
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean WordDelete(String Name) {
        try {
            String sql = "delete from Words where Name= \"" + Name + "\"";
            database.execSQL(sql);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

