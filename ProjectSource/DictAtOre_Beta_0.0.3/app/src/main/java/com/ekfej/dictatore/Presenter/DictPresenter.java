package com.ekfej.dictatore.Presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lloyd on 2015. 12. 13..
 */
public class DictPresenter extends MainPresenter {

    private Language language1;

    private Language language2;

    private List<Language> allLanguages;

    private List<Language> selectableLanguages;



    public DictPresenter(Context context, int language1Index, int language2Index) {
        super(context);
        allLanguages = stringLanguageList2LanguageLanguageList(db.lister.LanguageSelect());
        language1 = allLanguages.get(language1Index);
        language2 = allLanguages.get(language2Index);
    }


    /**
     * Visszaadja a kiválasztott első nyelv szavait és második nyelvű jelentéseit.
     * @param selectedLanguageIndex A kiválasztott elem sor indexe.
     * @param adapter A választható nyelveket tartalmazó adapter.
     * @return Egy Word típusú tömböt ad vissza.
     */
    public Word[] language1Changed(int selectedLanguageIndex, List<Language> adapter){
        selectableLanguages.remove(selectedLanguageIndex);
        insertLanguage2SelectableLanguages(language1);
        language1 = adapter.get(selectedLanguageIndex);
        adapter = selectableLanguages;

        return words2Display(language1, language2);
    }

    public Word[] language2Changed(int selectedLanguageIndex, List<Language> adapter){
        selectableLanguages.remove(selectedLanguageIndex);
        insertLanguage2SelectableLanguages(language2);
        language2 = adapter.get(selectedLanguageIndex);
        adapter = selectableLanguages;

        return words2Display(language1, language2);
    }

    private void insertLanguage2SelectableLanguages(Language lang) {
        int langId = lang.getId();

        int i = 0;
        while (selectableLanguages.get(i).getId() < langId) {
            i++;
        }
        selectableLanguages.add(i, lang);
    }

    public Word[] words2Display(Language language1, Language language2) {
        return (Word[])db.wordMethod.DictionarySelect(language1.getName(), language2.getName()).toArray();
    }

    public void addWord(Word word) {
        db.wordMethod.WordInsert(word);
    }

    public void deleteWord(Word word) {
        db.wordMethod.WordDelete(ideiglenesAtalakitoDeleteWordhoz(word));
    }

    private List<Word> ideiglenesAtalakitoDeleteWordhoz(Word word) {
        List<Word> w = new ArrayList<Word>();
        w.add(word);
        w.add(word.getMeaning().get(0));

        return w;
    }

}
