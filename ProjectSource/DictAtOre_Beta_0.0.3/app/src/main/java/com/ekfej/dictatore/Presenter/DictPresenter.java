package com.ekfej.dictatore.Presenter;

import android.content.Context;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lloyd on 2015. 12. 13..
 */
public class DictPresenter extends MainPresenter {

    private Context context;

    private Language language1;

    private Language language2;

    private ArrayAdapter<Language> langAdapter1;

    private ArrayAdapter<Language> langAdapter2;

    private Spinner spinner1;

    private Spinner spinner2;

    private ArrayAdapter<Word> language1Words;

    private ArrayAdapter<Word> language2FirstMeanings;

    private List<ArrayAdapter<Word>> language2Meanings;



    public DictPresenter(Context context, int language1Index, int language2Index,
                         ArrayAdapter<Language> langAdapter1, ArrayAdapter<Language> langAdapter2,
                         Spinner spinner1, Spinner spinner2,
                         ArrayAdapter<Word> language1Words, ArrayAdapter<Word> language2FirstMeanings,
                         List<ArrayAdapter<Word>> language2Meanings) {
        super(context);
        this.context = context;
        this.langAdapter1 = langAdapter1;
        this.langAdapter2 = langAdapter2;
        this.spinner1 = spinner1;
        this.spinner2 = spinner2;
        this.language1Words = language1Words;
        this.language2FirstMeanings = language2FirstMeanings;
        this.language2Meanings = language2Meanings;

        //langAdapter1.addAll(stringLanguageList2LanguageLanguageList(db.lister.LanguageSelect()));
        List<Language> temp = stringLanguageList2LanguageLanguageList(db.lister.LanguageSelect());
        for (Language x : temp) {
            langAdapter1.add(x);
        }

        language1 = langAdapter1.getItem(language1Index);
        language2 = langAdapter1.getItem(language2Index);

        //spinner1.setSelection(0);
        //spinner2.setSelection(0);

        langAdapter1.remove(language1);
        langAdapter1.remove(language2);

        for (int i = 0; i < langAdapter1.getCount(); i++) {
            this.langAdapter2.add(langAdapter1.getItem(i));
        }

        langAdapter1.insert(language1, 0);
        langAdapter2.insert(language2, 0);

        fillWordAdapters(words2Display(language1, language2));
    }



    /**
     * Visszaadja a kiválasztott első nyelv szavait és második nyelvű jelentéseit.
     * @param spinnerId A spinner id-je ahol a változás történt.
     * @param selectedLanguageIndex A kiválasztott elem sor indexe.
     * @return Egy Word típusú tömböt ad vissza.
     */
    public void languageChanged(int spinnerId, int selectedLanguageIndex) {
        if (spinnerId == 0) {
            Language old = language1;
            language1 = langAdapter1.getItem(selectedLanguageIndex);

            //langAdapter1.remove(language1);
            langAdapter1.insert(language1, 0);
            insertLanguage2Adapter1(old);

            langAdapter2.remove(language1);
            insertLanguage2Adapter2(old);
        }
        else if (spinnerId == 1) {
            Language old = language2;
            language2 = langAdapter2.getItem(selectedLanguageIndex);

            //langAdapter2.remove(language2);
            langAdapter2.insert(language2, 0);
            insertLanguage2Adapter2(old);

            langAdapter1.remove(language2);
            insertLanguage2Adapter1(old);
        }

        fillWordAdapters(words2Display(language1, language2));
    }

    private void insertLanguage2Adapter1(Language lang) {
        int langId = lang.getId();

        int i = 0;
        while (langAdapter1.getItem(i).getId() < langId) {
            i++;
        }
        langAdapter1.insert(lang, i);
    }

    private void insertLanguage2Adapter2(Language lang) {
        int langId = lang.getId();

        int i = 0;
        while (langAdapter2.getItem(i).getId() < langId) {
            i++;
        }
        langAdapter2.insert(lang, i);
    }


    public void swapLanguages() {
        Spinner temp;
        temp = spinner1;
        spinner1 = spinner2;
        spinner2 = temp;

        int tempId;
        tempId = spinner1.getId();
        spinner1.setId(spinner2.getId());
        spinner2.setId(tempId);

        Language tempLang;
        tempLang = language1;
        language1 = language2;
        language2 = tempLang;

        fillWordAdapters(words2Display(language1, language2));
    }

    private Word[] words2Display(Language language1, Language language2) {
        //return (Word[])db.wordMethod.DictionarySelect(language1.getName(), language2.getName()).toArray();
        List<Word> words = db.wordMethod.DictionarySelect(language1.getName(), language2.getName());
        return words.toArray(new Word[words.size()]);
    }

    private void fillWordAdapters(Word[] words) {
        language1Words.clear();
        language2FirstMeanings.clear();
        language2Meanings.clear();
        for (Word w : words) {
            language1Words.add(w);
            if (w.getMeaning().size() == 0) language2FirstMeanings.add(new Word("nincs..", new Language("..nincs")));
            else {
                language2FirstMeanings.add(w.getMeaning().get(0));
            }
            /*language2FirstMeanings.add(w.getMeaning().get(0));
            w.getMeaning().remove(0);*/

            if (w.getMeaning().size() == 0) {
                language2Meanings.add(null);
            }
            else {
                ArrayAdapter<Word> tempAA = new ArrayAdapter<Word>(context, android.R.layout.simple_spinner_item, w.getMeaning());
                tempAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tempAA.remove(w.getMeaning().get(0));
                language2Meanings.add(tempAA);
            }
        }
    }

}
