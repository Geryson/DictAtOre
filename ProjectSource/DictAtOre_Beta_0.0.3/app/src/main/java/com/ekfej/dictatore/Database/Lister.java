package com.ekfej.dictatore.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ekfej.dictatore.Presenter.Language;
import com.ekfej.dictatore.Presenter.Word;

import java.util.ArrayList;
import java.util.List;

/**
 *adatbázis adatokat listázó fgv-ek
 */
public class Lister {

    DictDatabaseHandler db;

    SQLiteDatabase databases;

    public Lister(Context context) {
        db = DictDatabaseHandler.getInstance(context);
        databases = db.database;
    }


    //region Listázás

    /**
     * Kilistázza a nyelvek neveit abc sorrendben
     * @return
     */
    public java.util.List<String> LanguageSelect() {
        db.open();
        java.util.List<String> list = new ArrayList<>();
        Cursor cursor = db.database.rawQuery("SELECT Name FROM Languages order by Name asc", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * Szó objektumokat listáz ki adott nyelven
     * @param Name
     * @return
     */
    public java.util.List<Word> WordObjectSelect(String Name) {
        db.open();
        java.util.List<Word> list = new ArrayList<>();
        java.util.List<String> listword = new ArrayList<>();
        java.util.List<Integer> IdList = new ArrayList<Integer>();
        Cursor ID = db.database.rawQuery("select ID from words where Language_ID = (select ID from languages where Name =\"" + Name + "\") group by word", null);
        Cursor word = db.database.rawQuery("select word from words where Language_ID = (select ID from languages where Name =\"" + Name + "\") group by word", null);
        Cursor meaning = db.database.rawQuery("select meaning from words where Language_ID = (select ID from languages where Name =\"" + Name + "\") group by word", null);
        Cursor language = db.database.rawQuery("select ID from languages where Name =\"" + Name + "\" ", null);

        ID.moveToFirst();
        while (!ID.isAfterLast()) {
            IdList.add(ID.getInt(0));
            ID.moveToNext();
        }
        ID.close();

        word.moveToFirst();
        while (!word.isAfterLast()) {
            listword.add(word.getString(0));
            word.moveToNext();
        }
        word.close();
        language.moveToFirst();
        int l = language.getInt(0);
        language.close();
        meaning.moveToFirst();
        int index =0;
        while (!meaning.isAfterLast()) {
            java.util.List<Word> meaningList = new ArrayList<Word>();
            meaningList.add(new Word(meaning.getInt(0)));
            list.add(new Word(IdList.get(index),listword.get(index), meaningList, new Language(l, Name)));
            index++;
            meaning.moveToNext();
        }
        meaning.close();

        db.close();
        return list;
    }

    /**
     * Szavak 'word' mezőit listázza ki az adott nyelven
     * @param Name
     * @return
     */
    public java.util.List<String> WordsSelect(String Name) {
        db.open();
        java.util.List<String> list = new ArrayList<>();
        Cursor cursor;
        if (Name == "-")
        {
            cursor = db.database.rawQuery("select word from words", null);
        }
        else {
            cursor = db.database.rawQuery("select word from words where Language_ID = (select ID from languages where Name =\"" + Name + "\") group by word", null);
        }                                                                                                                                 //de lehet később group by nélkül jobb

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * Egy nyelv 'ID' mezőjét adja vissza
     * @param Name
     * @return
     */
    public int LanguageIdSelect (String Name) {
        int Idint = 0;
        String sql = "SELECT ID FROM Languages WHERE Name = \"" + Name + "\"";
        db.open();
        Cursor cursor = db.database.rawQuery(sql, null);
        cursor.moveToFirst();
        Idint = cursor.getInt(0);
        cursor.close();
        db.close();
        return Idint;
    }
    /**
     * Egy szó 'ID' mezőjét adja vissza
     * @param ID
     * @return
     */
    public String WordIdSelect (int ID) {
        String w;
        String sql = "SELECT word FROM words WHERE ID = " + ID;
        db.open();
        Cursor cursor = db.database.rawQuery(sql, null);
        cursor.moveToFirst();
        w = cursor.getString(0);
        cursor.close();
        db.close();
        return w;
    }

    /**
     * Olyan szavak 'word' mezőjét listázza melyben benne van az adott kifejezés
     * @param Name
     * @param wordpart
     * @return
     */
    public List<String> Search (String Name, String wordpart) { //nincs tesztelve
        String sql = "select * from words where  Language_ID = (select ID from  languages where Name = \"" + Name + "\") and word like  \"%" + wordpart + "%\"";
        java.util.List<String> word = new ArrayList<String>();
        db.open();
        Cursor cursor = db.database.rawQuery(sql, null);
        cursor.moveToFirst();
        word.add(cursor.getString(0));
        cursor.close();
        db.close();
        return word;

    }

    /**
     * Egy adott nyelvű adott szavának egy adott nyelven való jelentését keresi
     * @param Language
     * @param wordLanguage
     * @param word
     * @return egy adott szó párja
     */
    public List<String> WordsSelectMatch(String Language, String wordLanguage, String word) {
        db.open();
        List<String> list = new ArrayList<>();

        Cursor cursor = db.database.rawQuery("SELECT word FROM `words` WHERE Meaning = (select ID from words where word= \"" +
                word + "\" and Language_ID = (select ID from  languages where Name = \"" + wordLanguage +
                "\")) and Language_ID = (select ID from languages where Name = \"" + Language + "\")", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }
    //endregion
}
