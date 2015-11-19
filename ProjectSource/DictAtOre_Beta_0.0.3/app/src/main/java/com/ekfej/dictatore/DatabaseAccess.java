package com.ekfej.dictatore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.jar.Attributes;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    private List<Words> WordsObject;
    public Dictionary<Words,Languages>  Dict;

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
        open();
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Name FROM Languages", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return list;
    }

    public List<String> WordsSelect(String Name) {  //nem tökéletes
        open();
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select word from words where Language_ID = (select ID from languages where Name =\"" + Name + "\")", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return list;
    }

    //saját

    public boolean WordInsert(String word, int meaning, int Language_ID) {
        open();
        try {
            String sql = "insert into Words (word, meaning, Language_ID) values (\"" + word + "\" ," + meaning + ", " + Language_ID + ")";
            database.execSQL(sql);
            close();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            close();
            return false;
        }
    }
    public boolean LanguageInsert(String Name) {
        open();
        try {

            /* Ez is működik (beépített insert fgv), de már megtaláltam hol írhatom be a saját sql parancsaim :D
            ContentValues cv = new ContentValues();
            cv.put("Id", Id);
            cv.put("Name", Name);
            database.insert("Languages", "Id", cv);
            */
            if(Name == null || Name == "") {return false;} //többi fgv-nél is kell vizsgálni (de itt se működik rendesen)
            else {
                String sql = "insert into Languages ( Name) values (\"" + Name + "\")";
                database.execSQL(sql);
                close();
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            close();
            return false;
        }
    }
    public boolean LanguageUpdate(String NewName,String OldName) {
        open();
        try {
            String sql = "update Languages SET Name= \"" + NewName + "\" where Name= \"" + OldName + "\"";
            database.execSQL(sql);
            close();
            return true;
        }
        catch(SQLException e) {
            e.printStackTrace();
            close();
            return  false;
        }
    }
    public boolean WordsWordUpdate(String NewWord, String OldWord, int Meaning, int Language_ID) {
        open();
        try {
            String sql = "update words Set word=\""+ NewWord + "\"  where word=\"" + OldWord + "\" and Language_ID=" + Language_ID + "and meaning =" + Meaning;
            database.execSQL(sql);
            close();
            return true;
        }
        catch(SQLException e) {
            e.printStackTrace();
            close();
            return  false;
        }
    }
    public boolean WordsMeaningUpdate(int NewMeaning, int OldMeaning, String Word, int Language_ID) {
        open();
        try {
            //if (NewMeaning == -1) { NewMeaning = null;}
            String sql = "update words Set meaning="+ NewMeaning + " where meaning=" + OldMeaning + " and Language_ID=" + Language_ID + "and word =\"" + Word + "\"";
            database.execSQL(sql);
            close();
            return true;
        }
        catch(SQLException e) {
            e.printStackTrace();
            close();
            return  false;
        }
    }
    public boolean WordsLanguage_IDUpdate(int NewLanguage_ID, int OldLanguage_ID, int Meaning, String Word) {
        open();
        try {
            String sql = "update words Set Language_ID="+ NewLanguage_ID + "  where Language_ID=" + OldLanguage_ID + " and meaning=" + Meaning + "and word=\"" + Word + "\"";
            database.execSQL(sql);
            close();
            return true;
        }
        catch(SQLException e) {
            e.printStackTrace();
            close();
            return  false;
        }
    }
    public boolean LanguageDelete(String Name) {
        open();
        try {
            String sql = "delete from Languages where Name= \"" + Name + "\"";
            database.execSQL(sql);
            close();
            return true;
        }
        catch(SQLException e) {
            e.printStackTrace();
            close();
            return false;
        }
    }

    public boolean WordDelete(String Name) {  //ne használd még nincs kész
        open();
        try {
            //kikeressük a keresett szót
            int modifyLanguage_ID = 0; //ide
            String modifyword =""; //ide
            int modifyoldmeaning = 0; //és ide belerakjuk a keresett szó elemeit
            int modifymeaning = -1;
            WordsMeaningUpdate(modifymeaning, modifyoldmeaning, modifyword, modifyLanguage_ID); //itt módosítjuk a kersett szónak a meaningjét
            String sql = "delete from Words where Name= \"" + Name + "\""; //itt pedig töröljük a törölni kívánt szót
            database.execSQL(sql);
            close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            close();
            return false;
        }
    }
}

