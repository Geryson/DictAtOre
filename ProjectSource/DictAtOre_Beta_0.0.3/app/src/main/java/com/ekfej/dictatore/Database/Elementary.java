package com.ekfej.dictatore.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by Buda Viktor on 2015.12.24..
 */
public class Elementary  {

    DictDatabaseHandler db;

    private SQLiteDatabase database;
    Context context;

    public Elementary(Context contextall) {
        context = contextall;

        db = DictDatabaseHandler.getInstance(context);

        database = db.database;

    }


    public boolean NullHossz(String szoveg) { //akkor igaz a szöveg hossza nagyobb mint nulla
        if ( 0 < (szoveg.length())) {
            return true;
        } else {
            return false;
        }
    }

    //region Elemi fgv-ek


    public boolean WordInsertElemi(String word, int meaning, int Language_ID) {
        db.open();
        try {
            String sql = null;
            if (meaning == -1)
            { sql = "insert into Words (word, Language_ID) values (\"" + word + "\" ," + Language_ID + ")";}
            else {sql  = "insert into Words (word, Meaning, Language_ID) values (\"" + word + "\" ," + meaning + " ," + Language_ID + ")";}
            db.database.execSQL(sql);
            db.close();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            db.close();
            return false;
        }
    }

    public boolean LanguageInsertElemi(String Name) {
        db.open();
        try {

            /* Ez is működik (beépített insert fgv), de már megtaláltam hol írhatom be a saját sql parancsaim :D
            ContentValues cv = new ContentValues();
            cv.put("Id", Id);
            cv.put("Name", Name);
            database.insert("Languages", "Id", cv);
            */


            String sql = "insert into Languages ( Name) values (\"" + Name + "\")";
            db.database.execSQL(sql);
            db.close();
            return true;


        }
        catch (SQLException e) {
            e.printStackTrace();
            db.close();
            return false;
        }
    }
    public boolean LanguageUpdateElemi(String NewName,String OldName) {
        db.open();
        try {
            String sql = "update Languages SET Name= \"" + NewName + "\" where Name= \"" + OldName + "\"";
            db.database.execSQL(sql);
            db.close();
            return true;
        }
        catch(SQLException e) {
            e.printStackTrace();
            db.close();
            return  false;
        }
    }
    public boolean WordsWordUpdateElemi(String NewWord, String OldWord, int Meaning, int Language_ID) {
        db.open();
        try {
            String sql = "update words Set word=\""+ NewWord + "\"  where word=\"" + OldWord + "\" and Language_ID=" + Language_ID + " and meaning =" + Meaning;
            db.database.execSQL(sql);
            db.close();
            return true;
        }
        catch(SQLException e) {
            e.printStackTrace();
            db.close();
            return  false;
        }
    }
    public boolean WordsMeaningUpdateElemi(int NewMeaning, int OldMeaning, String Word, int Language_ID) {
        db.open();
        try {
            //if (NewMeaning == -1) { NewMeaning = null;}
            String sql = "update words Set meaning="+ NewMeaning + " where meaning=" + OldMeaning + " and Language_ID=" + Language_ID + " and word =\"" + Word + "\"";
            db.database.execSQL(sql);
            db.close();
            return true;
        }
        catch(SQLException e) {
            e.printStackTrace();
            db.close();
            return  false;
        }
    }
    public boolean WordsLanguage_IDUpdateElemi(int NewLanguage_ID, int OldLanguage_ID, int Meaning, String Word) {
        db.open();
        try {
            String sql = "update words Set Language_ID="+ NewLanguage_ID + "  where Language_ID=" + OldLanguage_ID + " and meaning=" + Meaning + " and word=\"" + Word + "\"";
            db.database.execSQL(sql);
            db.close();
            return true;
        }
        catch(SQLException e) {
            e.printStackTrace();
            db.close();
            return  false;
        }
    }
    public boolean LanguageDeleteElemi(String Name) {
        db.open();
        try {
            String sql = "delete from Languages where Name= \"" + Name + "\"";
            db.database.execSQL(sql);
            db.close();
            return true;
        }
        catch(SQLException e) {
            e.printStackTrace();
            db.close();
            return false;
        }
    }

    public boolean WordDeleteElemi(String Name, int meaning, int Language_ID) {
        db.open();
        try {
            String sql = "delete from Words where word= \"" + Name + "\" and meaning = " + meaning + " and Language_ID = " + Language_ID;
            db.database.execSQL(sql);
            db.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            db.close();
            return false;
        }
    }

    public boolean ListEqualsElement(String Elem, List<String> List) //akkor igaz ha az elem nincs benne a listába
    {
        boolean re = true;
        for (int i=0; i < List.size(); i++)
        {
            if( Elem.equals(List.get(i)))
            {
                re = false;
                break;
            }
        }
        if (re == true) { return true; }
        else { return false; }
    }
    //endregion

}
