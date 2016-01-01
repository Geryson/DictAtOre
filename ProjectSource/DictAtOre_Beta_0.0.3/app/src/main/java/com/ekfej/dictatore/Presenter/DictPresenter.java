package com.ekfej.dictatore.Presenter;

import android.content.Context;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lloyd on 2015. 12. 13..
 */
public class DictPresenter extends MainPresenter {

    private Language language1;

    private Language language2;

    private List<Language> adapter;

    private Spinner spinner1;

    private Spinner spinner2;



    public DictPresenter(Context context, int language1Index, int language2Index, List<Language> adapter, Spinner spinner1, Spinner spinner2) {
        super(context);
        this.adapter = adapter;
        this.spinner1 = spinner1;
        this.spinner2 = spinner2;

        adapter = stringLanguageList2LanguageLanguageList(db.lister.LanguageSelect());

        language1 = adapter.get(language1Index);
        language2 = adapter.get(language2Index);

        spinner1.setSelection(language1Index);
        spinner2.setSelection(language2Index);

        adapter.remove(language1Index);
        adapter.remove(language2Index);
    }



    /**
     * Visszaadja a kiválasztott első nyelv szavait és második nyelvű jelentéseit.
     * @param spinnerId A spinner id-je ahol a változás történt.
     * @param selectedLanguageIndex A kiválasztott elem sor indexe.
     * @return Egy Word típusú tömböt ad vissza.
     */
    public Word[] languageChanged(int spinnerId, int selectedLanguageIndex) {
        if (spinnerId == 0) {
            Language old = language1;
            language1 = adapter.get(selectedLanguageIndex);

            adapter.remove(selectedLanguageIndex);
            insertLanguage2Adapter(old);
        }
        else {
            Language old = language2;
            language2 = adapter.get(selectedLanguageIndex);

            adapter.remove(selectedLanguageIndex);
            insertLanguage2Adapter(old);
        }

        return words2Display(language1, language2);
    }

    private void insertLanguage2Adapter(Language lang) {
        int langId = lang.getId();

        int i = 0;
        while (adapter.get(i).getId() < langId) {
            i++;
        }
        adapter.add(i, lang);
    }


    public Word[] swapLanguages() {
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

        return words2Display(language1, language2);
    }

    private Word[] words2Display(Language language1, Language language2) {
        return (Word[])db.wordMethod.DictionarySelect(language1.getName(), language2.getName()).toArray();
    }


    public void addWord(Word word) {
        db.wordMethod.WordInsert(word);
    }

}
