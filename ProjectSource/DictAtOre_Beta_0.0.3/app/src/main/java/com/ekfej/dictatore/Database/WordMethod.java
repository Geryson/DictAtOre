package com.ekfej.dictatore.Database;

import android.content.Context;

import com.ekfej.dictatore.Presenter.Language;
import com.ekfej.dictatore.Presenter.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Buda Viktor on 2015.12.24..
 */
public class WordMethod  {

    Context contextall;

    public WordMethod(Context context) {
        contextall = context;
    }

    Lister lister = new Lister(contextall);
    Elementary elementary = new Elementary(contextall);
    KnowledgeTest knowledgeTest = new KnowledgeTest(contextall);

    //region Szótár
    public boolean WordDelete(List<Word> TörlendőSzavak) //kipróbálva, kevés teszt
    {
        Boolean b = false;
        List<Word> Szavak = lister.WordObjectSelect(TörlendőSzavak.get(0).getLanguage().getName());
        for (int i=0; i < TörlendőSzavak.size(); i++)
        {
            for (int j =0; j < Szavak.size(); j++) {
                if (TörlendőSzavak.get(i).getWord().equals(Szavak.get(j).getWord())) {
                    for (int m =0; m <Szavak.get(j).getMeaning().size(); m++) {
                        if (TörlendőSzavak.get(i).getMeaning().get(0).getId() == Szavak.get(j).getMeaning().get(m).getId()) {
                            b =  elementary.WordDeleteElemi(TörlendőSzavak.get(i).getWord(), TörlendőSzavak.get(i).getMeaning().get(0).getId(), TörlendőSzavak.get(i).getLanguage().getId());
                            break;
                        }
                    }
                }
            }
        }
        return b;
    }
    public boolean WordUpdate(Word word, String NewValue, int mezoindex) { //egyszerre csak egyet lehet módosítani
        List<Word> Szavak = lister.WordObjectSelect(word.getLanguage().getName());  //kipróbálva, de kevés teszt
        for (int j =0; j < Szavak.size(); j++) {
            if (word.getWord().equals(Szavak.get(j).getWord())) {
                for (int m =0; m <Szavak.get(j).getMeaning().size(); m++) {
                    if (word.getMeaning().get(0).getId() == Szavak.get(j).getMeaning().get(0).getId()) {
                        switch (mezoindex) {
                            case 2:
                                return elementary.WordsWordUpdateElemi(NewValue, word.getWord(), word.getMeaning().get(0).getId(), word.getLanguage().getId());
                            case 3:
                                return elementary.WordsMeaningUpdateElemi(Integer.decode(NewValue), word.getMeaning().get(0).getId(), word.getWord(), word.getLanguage().getId());
                            case 4:
                                return elementary.WordsLanguage_IDUpdateElemi(Integer.decode(NewValue), word.getLanguage().getId(), word.getMeaning().get(0).getId(), word.getWord());

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
        if (elementary.NullHossz(word.getWord()) && elementary.NullHossz(word.getLanguage().getName())) {
            List<String> nyelvek = lister.LanguageSelect();
            boolean nyelvB = false;
            for (int j =0; j < nyelvek.size(); j++)
            {
                if (word.getLanguage().getName().equals(nyelvek.get(j))) {
                    nyelvB = true; break;
                }
            }
            if (nyelvB) {
                List<Word> sumword = lister.WordObjectSelect(word.getLanguage().getName());
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
                    if (word.getMeaning() == null) //ha nincs párja
                    {
                        boolean b =  elementary.WordInsertElemi(word.getWord(), -1, word.getLanguage().getId());
                    }
                    else {
                        boolean is = false; // ha van párja és benne van az adatbázisban a párja
                        for (int index =0; index < sumword.size(); index++) {
                            if (word.getMeaning().get(0).getWord().equals(sumword.get(index).getWord()) && sumword.get(index).getMeaning() == null &&
                                    sumword.get(index).getLanguage().getName().equals(word.getLanguage().getName())) {
                                int meaningID = sumword.get(index).getId();
                                boolean b = elementary.WordInsertElemi(word.getWord(), meaningID, word.getLanguage().getId());
                                WordUpdate(sumword.get(index), "" + meaningID + "", 3);
                                is = true;
                                break;
                            }
                        }
                        if (is == false) {  //van párja de nincs benne az adatbázisban

                            boolean b = elementary.WordInsertElemi(word.getWord(), -1, word.getLanguage().getId());

                            sumword = lister.WordObjectSelect(word.getLanguage().getName());
                            for (int i =0; i < sumword.size(); i++) {
                                if (word.getWord().equals(sumword.get(i).getWord()) && sumword.get(i).getMeaning().get(0).getId() == 0) {
                                    int meaningID = sumword.get(i).getId();
                                    if (elementary.NullHossz(word.getMeaning().get(0).getWord()) && elementary.NullHossz(word.getMeaning().get(0).getLanguage().getName())) {
                                        elementary.WordInsertElemi(word.getMeaning().get(0).getWord(), meaningID, word.getMeaning().get(0).getLanguage().getId());
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
        List<String> FirstWords = lister.WordsSelect(FirstLanguage);
        List<String> SecondWords = new ArrayList<String>();
        List<Language> Nyelvek = new ArrayList<Language>();
        Nyelvek.add(new Language(lister.LanguageIdSelect(FirstLanguage), FirstLanguage));
        Nyelvek.add(new Language(lister.LanguageIdSelect(SecondLanguage), SecondLanguage));
        for ( int i= 0; i < FirstWords.size(); i++) {
            String actualWord = FirstWords.get(i);
            SecondWords = (knowledgeTest.Decipherment(SecondLanguage, FirstLanguage, actualWord));

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

}
