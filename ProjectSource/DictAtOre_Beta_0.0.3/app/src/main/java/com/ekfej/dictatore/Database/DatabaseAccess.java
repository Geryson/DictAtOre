package com.ekfej.dictatore.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.AdapterView;
import android.widget.EditText;


import com.ekfej.dictatore.Presenter.Language;
import com.ekfej.dictatore.Presenter.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    //region importálás
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
    public static DatabaseAccess getInstance() {

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

    //endregion

    //region Listázás
    public List<String> LanguageSelect() {
        open();
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Name FROM Languages order by Name asc", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return list;
    }
    public List<Word> WordObjectSelect(String Name) {
        open();
        List<Word> list = new ArrayList<>();
        List<String> listword = new ArrayList<>();
        Cursor word = database.rawQuery("select word from words where Language_ID = (select ID from languages where Name =\"" + Name + "\") group by word", null);
        Cursor meaning = database.rawQuery("select meaning from words where Language_ID = (select ID from languages where Name =\"" + Name + "\") group by word", null);
        Cursor language = database.rawQuery("select ID from languages where Name =\"" + Name + "\" ", null);

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
            List<Word> meaningList = new ArrayList<Word>();
            meaningList.add(new Word(meaning.getInt(0)));
            list.add(new Word(listword.get(index), meaningList, new Language(l, Name)));
            index++;
            meaning.moveToNext();
        }
        meaning.close();

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
            cursor = database.rawQuery("select word from words where Language_ID = (select ID from languages where Name =\"" + Name + "\") group by word", null);
        }                                                                                                                                 //de lehet később group by nélkül jobb

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return list;
    }

    public int LanguageIdSelect (String Name) {
        int Idint = 0;
        String sql = "SELECT ID FROM Languages WHERE Name = \"" + Name + "\"";
        open();
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        Idint = cursor.getInt(0);
        cursor.close();
        close();
        return Idint;
    }
    //endregion

    //saját

    //region Elemi fgv-ek

    public List<String> WordsSelectMatch(String Language, String wordLanguage, String word) {
        open();
        List<String> list = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT word FROM `words` WHERE Meaning = (select ID from words where word= \"" +
                word + "\" and Language_ID = (select ID from  languages where Name = \"" + wordLanguage +
                "\")) and Language_ID = (select ID from languages where Name = \"" + Language + "\")", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return list;
    }

    public boolean WordInsertElemi(String word, int meaning, int Language_ID) {
        open();
        try {
            String sql = null;
            if (meaning == -1)
            { sql = "insert into Words (word, Language_ID) values (\"" + word + "\" ," + Language_ID + ")";}
           else {sql  = "insert into Words (word, meaning, Language_ID) values (\"" + word + "\" ," + meaning + ", " + Language_ID + ")";}
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


    //region Presenter rétegnek való fgv-ek


    public boolean LanguageInsert(EditText LanguageName) {
        String Name = LanguageName.getText().toString();
        List<String> nyelvek = LanguageSelect();
        if (ListEqualsElement(Name, nyelvek) && NullHossz(Name)) {
            LanguageInsertElemi(Name);
            return  true;
        }
        else { return false; }
    }
    public boolean NullHossz(String szoveg) { //akkor igaz a szöveg hossza nagyobb mint nulla
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
    //region Szórár
    public boolean WordDelete(Word RemoveWord) //A RemoveWord egy id nélküli konstruktort vár
    {                                           //nincs kipróbálva
        Boolean b = false;
        List<Word> Szavak = WordObjectSelect(RemoveWord.getLanguage().getName());
        for (int i=0; i < Szavak.size(); i++)
        {
            /*
            if (RemoveWord.equals(Szavak.get(i)))
            {   }
            */
            if (RemoveWord.getWord().equals(Szavak.get(i).getWord()) && RemoveWord.getLanguage().equals(Szavak.get(i).getLanguage())) {
                WordDeleteElemi(RemoveWord.getWord(),RemoveWord.getLanguage().getId()); b = true;
            }
        }
        return b;
    }
    public boolean WordInsert(Word word)
    {
        int end =0;
        if (NullHossz(word.getWord()) && NullHossz(word.getLanguage().getName())) {
            List<String> nyelvek = LanguageSelect();
            boolean nyelvB = false;
            for (int j =0; j < nyelvek.size(); j++)
            {
                if (word.getLanguage().getName().equals(nyelvek.get(j))) {
                    nyelvB = true; break;
                }
            }
            if (nyelvB) {
                List<Word> sumword = WordObjectSelect(word.getLanguage().getName());
                for (int i = 0; i < sumword.size(); i++) {
                    if (sumword.get(i).equals(word)) {
                        nyelvB = false; break;
                    }
                }
                if (nyelvB == true) {
                    end = 1;
                    if (word.getMeaning() == null)
                    {
                      boolean b =  WordInsertElemi(word.getWord(), -1, word.getLanguage().getId());
                    }
                    else {
                        boolean b = WordInsertElemi(word.getWord(), word.getMeaning().get(0).getId(), word.getLanguage().getId());
                    }
                }
            }
        }

        if (end == 1)
        { return true; }
        else
        {return false; }
    }

    public List<Word> DictionarySelect(String FirstLanguage, String SecondLanguage) {
        List<Word> Dictionary = new ArrayList<Word>();
        List<String> FirstWords = WordsSelect(FirstLanguage);
        List<String> SecondWords = new ArrayList<String>();
        List<Language> Nyelvek = new ArrayList<Language>();
        Nyelvek.add(new Language(LanguageIdSelect(FirstLanguage), FirstLanguage));
        Nyelvek.add(new Language(LanguageIdSelect(SecondLanguage), SecondLanguage));
        for ( int i= 0; i < FirstWords.size(); i++) {
            String actualWord = FirstWords.get(i);
            SecondWords = (Decipherment(SecondLanguage, FirstLanguage, actualWord));

            int LanguageIndex, LanguageIndex2;
            if (FirstLanguage.equals(Nyelvek.get(0)))
            { LanguageIndex = 0; LanguageIndex2 = 1;}
            else {LanguageIndex = 1; LanguageIndex2 = 0;}

            List<Word> SecondWordTypeList = new ArrayList<Word>();
            for (int indexer= 0; indexer < SecondWords.size(); indexer++) {
                SecondWordTypeList.add(new Word(SecondWords.get(indexer),
                        Nyelvek.get(LanguageIndex2))); //itt lehetnek még problémák
            }
            Dictionary.add(new Word(FirstWords.get(i), SecondWordTypeList, Nyelvek.get(LanguageIndex)));

        }
        return Dictionary;
    }

    //endregion
    //region Tudásteszt

    public String Expression(String LanguageName) throws Exception {
        List<String> szavak = WordsSelect(LanguageName);
        Random rnd = new Random();
        if (szavak.size() > 0) {
            int random = rnd.nextInt(szavak.size());
            return szavak.get(random);
        }
        else {
            return null;
            //throw new Exception("Nincs " + LanguageName + " nyelvű szó az adatbázisban");

        }
    }
    public List<String> Decipherment(String Language, String wordLanguage, String word)
    {
        List<String> decipherment = null;
        try {
            if (NullHossz(word)) {
                decipherment = WordsSelectMatch(Language, wordLanguage, word);
                return decipherment;
            }
        }
        catch (Exception e)
        {
           e.printStackTrace();
            //return decipherment;
        }
        return decipherment;
    }
    public boolean DeciphermentVsElement(EditText User_decipherment, List<String> decipherment) {
        String user_d = User_decipherment.getText().toString();
        if ( (!ListEqualsElement(user_d, decipherment)) && NullHossz(user_d)) {
            return  true;
        }
        else { return  false; }
    }

    //endregion

    //endregion
}

