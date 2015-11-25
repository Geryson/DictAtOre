package com.ekfej.dictatore.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.AdapterView;
import android.widget.EditText;

import com.ekfej.dictatore.Language_InsertActivity;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

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

    public static DatabaseAccess getInstance(AdapterView.OnItemClickListener onItemClickListener) { //működik, kérdés hogy baj-e hogy nincs feltétel

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

    public List<String> WordsSelect(String Name) {
        open();
        List<String> list = new ArrayList<>();
        Cursor cursor;
        if (Name == "-")
        {
            cursor = database.rawQuery("select word from words", null);
        }
        else {
            cursor = database.rawQuery("select word from words where Language_ID = (select ID from languages where Name =\"" + Name + "\")", null);
        }

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

    //region Elemi fgv-ek

    public boolean WordInsertElemi(String word, int meaning, int Language_ID) {
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

    public boolean LanguageInsertElemi(String Name) {
        open();
        try {

            /* Ez is működik (beépített insert fgv), de már megtaláltam hol írhatom be a saját sql parancsaim :D
            ContentValues cv = new ContentValues();
            cv.put("Id", Id);
            cv.put("Name", Name);
            database.insert("Languages", "Id", cv);
            */


                String sql = "insert into Languages ( Name) values (\"" + Name + "\")";
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
    public boolean LanguageUpdateElemi(String NewName,String OldName) {
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
    public boolean WordsWordUpdateElemi(String NewWord, String OldWord, int Meaning, int Language_ID) {
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
    public boolean WordsMeaningUpdateElemi(int NewMeaning, int OldMeaning, String Word, int Language_ID) {
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
    public boolean WordsLanguage_IDUpdateElemi(int NewLanguage_ID, int OldLanguage_ID, int Meaning, String Word) {
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
    public boolean LanguageDeleteElemi(String Name) {
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

    public boolean WordDeleteElemi(String Name, int Language_ID) {  //ne használd még nincs kész
        open();
        try {
            /*
            //kikeressük a keresett szót
            int modifyLanguage_ID = 0; //ide
            String modifyword =""; //ide
            int modifyoldmeaning = 0; //és ide belerakjuk a keresett szó elemeit
            int modifymeaning = -1;
            WordsMeaningUpdate(modifymeaning, modifyoldmeaning, modifyword, modifyLanguage_ID); //itt módosítjuk a kersett szónak a meaningjét
            */
            String sql = "delete from Words where Name= \"" + Name + "\" and Language_ID =" + Language_ID; //itt pedig töröljük a törölni kívánt szót
            database.execSQL(sql);
            close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            close();
            return false;
        }
    }
    //endregion

    //region Presenter rétegnek való fgv-ek
    public boolean LanguageInsert(EditText LanguageName) {
        String Name = LanguageName.getText().toString();
        List<String> nyelvek = LanguageSelect();
        boolean re = true;
        for (int i=0; i < nyelvek.size(); i++)
        {
            String n = nyelvek.get(i);
           if( Name.equals(nyelvek.get(i)))

            {
                re = false;
                break;
            }
        }
        if (re == true && NullHossz(Name)) {
            LanguageInsertElemi(Name);
            return true;
        } else {
            //if (re == true ) { hiba = "Üres mező"; } else { hiba = "Már létezik";}
            return false;
        }
    }
    public boolean NullHossz(String szoveg) {
        if ( 0 < (szoveg.length())) {
            return true;
        } else {
            return false;
        }
    }
    public boolean LanguageUpdate(String OldName, String NewName) {
        if (NullHossz(NewName)) {
            LanguageUpdateElemi(OldName, NewName);
            return true;
        } else {
            return false;
        }
    }
    public boolean WordDelete() //csak bele van kezdve
    {
        List<String> Szavak = WordsSelect("-");
        return true;
    }


    //endregion
}

