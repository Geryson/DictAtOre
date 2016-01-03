package com.ekfej.dictatore.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.EditText;

import com.ekfej.dictatore.Presenter.Word;

import java.util.List;
import java.util.Random;

/**
 * Tudásteszt activiy műveleteihez szükséges adatbázis metódusok
 */
public class KnowledgeTest {

    //public DictDatabaseHandler db;
  Context context;



    public KnowledgeTest(Context contextall) {
        Context context = contextall;
    }


    Lister lister = new Lister(context);
    Elementary elementary = new Elementary(context);

    //region Tudásteszt

    /**
     * Előállít egy feladatokból álló listát a megadott paraméterek alapján
     * @param LanguageName
     * @param LanguageName2
     * @param Size
     * @return
     */
    public String[] Expression(String LanguageName,String LanguageName2, int Size) {
        List<Word> szavak = lister.WordObjectSelect(LanguageName);
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
                s[index] = lister.WordsSelect(LanguageName).get(index);
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

    /**
     * Megkeresi a feladathoz tartozó megoldásokat
     * @param Language
     * @param wordLanguage
     * @param word
     * @return
     */
    public List<String> Decipherment(String Language, String wordLanguage, String word)
    {
        List<String> decipherment = null;
        try {
            if (elementary.NullHossz(word)) {
                decipherment = lister.WordsSelectMatch(Language, wordLanguage, word);
                return decipherment;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.e("KnowledgeTest", e.toString());
            //return decipherment;
        }
        return decipherment;
    }

    /**
     * Összehasonlítja a lehetséges megoldást a valódi megoldásokkal és eldönte jó-e amegoláds
     * @param User_decipherment
     * @param decipherment
     * @return
     */
    public boolean DeciphermentVsElement(EditText User_decipherment, List<String> decipherment) {
        String user_d = User_decipherment.getText().toString();
        if ( (!elementary.ListEqualsElement(user_d, decipherment)) && elementary.NullHossz(user_d)) {
            return  true;
        }
        else { return  false; }
    }

    //endregion

}
