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
        List<Integer> IdList = new ArrayList<Integer>();
        Cursor ID = database.rawQuery("select ID from words where Language_ID = (select ID from languages where Name =\"" + Name + "\") group by word", null);
        Cursor word = database.rawQuery("select word from words where Language_ID = (select ID from languages where Name =\"" + Name + "\") group by word", null);
        Cursor meaning = database.rawQuery("select meaning from words where Language_ID = (select ID from languages where Name =\"" + Name + "\") group by word", null);
        Cursor language = database.rawQuery("select ID from languages where Name =\"" + Name + "\" ", null);

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
            List<Word> meaningList = new ArrayList<Word>();
            meaningList.add(new Word(meaning.getInt(0)));
            list.add(new Word(IdList.get(index),listword.get(index), meaningList, new Language(l, Name)));
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
    public List<String> Search (String Name, String wordpart) { //nincs tesztelve
        String sql = "select * from words where  Language_ID = (select ID from  languages where Name = \"" + Name + "\") and word like  \"%" + wordpart + "%\"";
        List<String> word = new ArrayList<String>();
        open();
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        word.add(cursor.getString(0));
        cursor.close();
        close();
        return word;

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
            String sql = "update words Set word=\""+ NewWord + "\"  where word=\"" + OldWord + "\" and Language_ID=" + Language_ID + " and meaning =" + Meaning;
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
            String sql = "update words Set meaning="+ NewMeaning + " where meaning=" + OldMeaning + " and Language_ID=" + Language_ID + " and word =\"" + Word + "\"";
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
            String sql = "update words Set Language_ID="+ NewLanguage_ID + "  where Language_ID=" + OldLanguage_ID + " and meaning=" + Meaning + " and word=\"" + Word + "\"";
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

    public boolean WordDeleteElemi(String Name, int meaning, int Language_ID) { 
        open();
        try {
            String sql = "delete from Words where word= \"" + Name + "\" and meaning = " + meaning + " and Language_ID = " + Language_ID;
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

    //region Szótár
    public boolean WordDelete(List<Word> TörlendőSzavak) //kipróbálva, kevés teszt
    {
        Boolean b = false;
        List<Word> Szavak = WordObjectSelect(TörlendőSzavak.get(0).getLanguage().getName());
        for (int i=0; i < TörlendőSzavak.size(); i++)
        {
            for (int j =0; j < Szavak.size(); j++) {
                if (TörlendőSzavak.get(i).getWord().equals(Szavak.get(j).getWord())) {
                    for (int m =0; m <Szavak.get(j).getMeaning().size(); m++) {
                        if (TörlendőSzavak.get(i).getMeaning().get(0).getId() == Szavak.get(j).getMeaning().get(m).getId()) {
                            b =  WordDeleteElemi(TörlendőSzavak.get(i).getWord(), TörlendőSzavak.get(i).getMeaning().get(0).getId(), TörlendőSzavak.get(i).getLanguage().getId());
                        break;
                        }
                    }
                }
            }
        }
        return b;
    }
    public boolean WordUpdate(Word word, String NewValue, int mezoindex) { //egyszerre csak egyet lehet módosítani
        List<Word> Szavak = WordObjectSelect(word.getLanguage().getName());  //kipróbálva, de kevés teszt
        for (int j =0; j < Szavak.size(); j++) {
            if (word.getWord().equals(Szavak.get(j).getWord())) {
                for (int m =0; m <Szavak.get(j).getMeaning().size(); m++) {
                    if (word.getMeaning().get(0).getId() == Szavak.get(j).getMeaning().get(0).getId()) {
                        switch (mezoindex) {
                            case 2:
                                return WordsWordUpdateElemi(NewValue, word.getWord(), word.getMeaning().get(0).getId(), word.getLanguage().getId());
                            case 3:
                                return WordsMeaningUpdateElemi(Integer.decode(NewValue), word.getMeaning().get(0).getId(), word.getWord(), word.getLanguage().getId());
                            case 4:
                                return WordsLanguage_IDUpdateElemi(Integer.decode(NewValue), word.getLanguage().getId(), word.getMeaning().get(0).getId(), word.getWord());

                        }
                    }
                }
            }
        }
        return false;
    }
    public boolean WordUpdateObject (Word OldWord, Word NewWord) //akár mind a 3 értékéát lehet módosítani (kivéve az ID-t)
    {                                                               //nincs kipróbálva
        boolean wordb = true, meaningb = true, languageb = true;
        if (OldWord.getWord().equals(NewWord.getWord())) { wordb = false;}
        if (OldWord.getMeaning().equals(NewWord.getMeaning())) { meaningb = false;}
        if (OldWord.getLanguage().equals(NewWord.getLanguage())) {languageb = false; }
        if (wordb) {
            WordUpdate(OldWord,NewWord.getWord(), 2);
            wordb = false;
        }
        if (languageb) {
            WordUpdate(new Word(NewWord.getWord(), OldWord.getMeaning(), OldWord.getLanguage()), ""+NewWord.getLanguage().getId()+"", 4);
            languageb = false;
        }
        if (meaningb) {
            WordUpdate(new Word(NewWord.getWord(),OldWord.getMeaning(), NewWord.getLanguage()), ""+NewWord.getMeaning().get(0).getId()+"" ,3);
            meaningb = false;
        }
        return (!wordb && !languageb && !meaningb);
    }
    public boolean WordInsert(Word word) //kipróbálva, működött (kevés teszt)
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
                    for (int index =0; index < sumword.get(i).getMeaning().size(); index++) {
                        if (word.getWord().equals(sumword.get(i).getWord()) && word.getMeaning().get(0).getId() == sumword.get(i).getMeaning().get(index).getId()) {
                            nyelvB = false;
                            break;
                        }
                    }
                }
                if (nyelvB == true) {
                    end = 1;
                    if (word.getMeaning() == null)
                    {
                      boolean b =  WordInsertElemi(word.getWord(), -1, word.getLanguage().getId());
                    }
                    else {
                        boolean is = false;
                        for (int index =0; index < sumword.size(); index++) {
                            if (word.getMeaning().get(0).getWord().equals(sumword.get(index).getWord()) && sumword.get(index).getMeaning() == null &&
                                    sumword.get(index).getLanguage().getName().equals(word.getLanguage().getName())) {
                                int meaningID = sumword.get(index).getId();
                                boolean b = WordInsertElemi(word.getWord(), meaningID, word.getLanguage().getId());
                                WordUpdate(sumword.get(index), "" + meaningID + "", 3);
                                is = true;
                                break;
                            }
                        }
                        if (is == false) {

                            boolean b = WordInsertElemi(word.getWord(), -1, word.getLanguage().getId());

                            sumword = WordObjectSelect(word.getLanguage().getName());
                                for (int i =0; i < sumword.size(); i++) {
                                    if (word.getWord().equals(sumword.get(i).getWord()) && sumword.get(i).getMeaning().get(0).getId() == 0) {
                                        int meaningID = sumword.get(i).getId();
                                        if (NullHossz(word.getMeaning().get(0).getWord()) && NullHossz(word.getMeaning().get(0).getLanguage().getName())) {
                                            WordInsertElemi(word.getMeaning().get(0).getWord(), meaningID, word.getMeaning().get(0).getLanguage().getId());
                                            meaningID++;
                                            WordUpdate(word, "" + meaningID + "", 3);
                                        } //a második nyelv szava nem tud az elsö nyelv szavára mutatni
                                        break;
                                    }
                                }
                        }
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
            if (FirstLanguage.equals(Nyelvek.get(0).getName()))
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

    public String[] Expression(String LanguageName,String LanguageName2, int Size) {
        List<Word> szavak = WordObjectSelect(LanguageName);
        for (int i =0; i< szavak.size(); i++) {
                List<String> d = Decipherment(LanguageName2,LanguageName,szavak.get(i).getWord());
                    if (d == null) {
                    szavak.remove(i);
                        break;
            }
        }
        Random rnd = new Random();
        if (szavak.size() <= Size) {
            String[] s = new String[szavak.size()];
            for (int index=0; index < szavak.size();) {
                s[index] = WordsSelect(LanguageName).get(index);
                index++;
            }
            return  s;
        }
        else {
            String[] s = new String[Size];
            for (int i=0; i< Size; i++) {
                    int random = rnd.nextInt(szavak.size());
                    s[i] = szavak.get(random).getWord();
            }
            return  s;
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

