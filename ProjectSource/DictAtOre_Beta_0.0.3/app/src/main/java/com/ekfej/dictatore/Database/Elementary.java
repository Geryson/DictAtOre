package com.ekfej.dictatore.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ekfej.dictatore.DictatoreLogger.LoggerMain;

import java.util.List;


/**
 * Created by Buda Viktor on 2015.12.24..
 */

/**
 * Elemi fgv-ek tárolása
 */
public class Elementary {

    DictDatabaseHandler db;

    private SQLiteDatabase database;
    Context context;

    public Elementary(Context contextall) {
        context = contextall;

        db = DictDatabaseHandler.getInstance(context);

        database = db.database;

    }

    public Elementary() {

    }

    LoggerMain loggerMain = new LoggerMain();

    /**
     * akkor igaz ha a szöveg hossza nagyobb mint nulla
     * @param szoveg
     * @return
     */
    public boolean NullHossz(String szoveg) {
        if ( 0 < (szoveg.length()) ) {
            return true;
        } else {
            return false;
        }
    }

    //region Elemi fgv-ek

    /**
     * Egy szó felvitele az adatbázisba
     * @param word
     * @param meaning
     * @param Language_ID
     * @return
     */
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
            loggerMain.LogError("WordInsertElemi", e);
            e.printStackTrace();
            db.close();
            return false;
        }
    }

    /**
     * Egy nyelv felvitele az adatbázisba
     * @param Name
     * @return
     */
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

    /**
     * Egy nyelv módosítása
     * @param NewName
     * @param OldName
     * @return
     */
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

    /**
     * Egy szó 'word' mezőjének módosítása
     * @param NewWord
     * @param OldWord
     * @param Meaning
     * @param Language_ID
     * @return
     */
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

    /**
     * Egy szó 'meaning' mezőjének módosítása
     * @param NewMeaning
     * @param OldMeaning
     * @param Word
     * @param Language_ID
     * @return
     */
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

    /**
     * Egy szó 'Language_ID' mezőjének módosítása
     * @param NewLanguage_ID
     * @param OldLanguage_ID
     * @param Meaning
     * @param Word
     * @return
     */
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

    /**
     * Egy nyelv törlése
     * @param Name
     * @return
     */
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

    /**
     * Egy szó törlése (melynek minden mezője megegyezik a megadottéval (kivéve az ID)
     * @param Name
     * @param meaning
     * @param Language_ID
     * @return
     */
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

    /**
     * akkor igaz ha az elem nincs benne a listába
     * @param Elem
     * @param List
     * @return bool
     */
    public boolean ListEqualsElement(String Elem, List<String> List)
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
