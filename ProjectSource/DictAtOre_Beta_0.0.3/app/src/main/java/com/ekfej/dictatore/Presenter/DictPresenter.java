package com.ekfej.dictatore.Presenter;

import android.content.Context;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Lloyd on 2015. 12. 13..
 */
public class DictPresenter extends MainPresenter {

    private Word_InsertPresenter insertPresenter;

    private Context context;

    private Language language1;
    public String getLanguage1() { return language1.getName(); }

    private Language language2;
    public String getLanguage2() { return language2.getName(); }

    private ArrayAdapter<Language> langAdapter;

    private Spinner spinner1;

    private Spinner spinner2;

    private ArrayAdapter<Word> language1Words;

    private ArrayAdapter<Word> language2FirstMeanings;

    private List<ArrayAdapter<Word>> language2Meanings;



    public DictPresenter(Context context, int language1Index, int language2Index, ArrayAdapter<Language> langAdapter,
                         Spinner spinner1, Spinner spinner2, ArrayAdapter<Word> language1Words, ArrayAdapter<Word> language2FirstMeanings, List<ArrayAdapter<Word>> language2Meanings) {
        super(context);
        this.context = context;
        this.langAdapter = langAdapter;
        this.spinner1 = spinner1;
        this.spinner2 = spinner2;
        this.language1Words = language1Words;
        this.language2FirstMeanings = language2FirstMeanings;
        this.language2Meanings = language2Meanings;

        //langAdapter.addAll(stringLanguageList2LanguageLanguageList(db.lister.LanguageSelect()));
        List<Language> temp = stringLanguageList2LanguageLanguageList(db.lister.LanguageSelect());
        for (Language x : temp) {
            langAdapter.add(x);
        }

        language1 = langAdapter.getItem(language1Index);
        language2 = langAdapter.getItem(language2Index);

        spinner1.setSelection(language1Index);
        spinner2.setSelection(language2Index);

        langAdapter.remove(language1);
        langAdapter.remove(language2);
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
            language1 = langAdapter.getItem(selectedLanguageIndex);

            langAdapter.remove(language1);
            insertLanguage2Adapter(old);
        }
        else {
            Language old = language2;
            language2 = langAdapter.getItem(selectedLanguageIndex);

            langAdapter.remove(language2);
            insertLanguage2Adapter(old);
        }

        fillWordAdapters(words2Display(language1, language2));
    }

    private void insertLanguage2Adapter(Language lang) {
        int langId = lang.getId();

        int i = 0;
        while (langAdapter.getItem(i).getId() < langId) {
            i++;
        }
        langAdapter.insert(lang, i);
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
        return (Word[])db.wordMethod.DictionarySelect(language1.getName(), language2.getName()).toArray();
    }

    private List<ArrayAdapter<Word>> fillWordAdapters(Word[] words) {
        for (Word w : words) {
            language1Words.add(w);
            language2FirstMeanings.add(w.getMeaning().get(0));

            ArrayAdapter<Word> tempAA = new ArrayAdapter<Word>(context, android.R.layout.simple_spinner_item, w.getMeaning());
            tempAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            language2Meanings.add(tempAA);
        }

        return language2Meanings;
    }

    public void addWord(Word word) {
        if (insertPresenter == null) insertPresenter = new Word_InsertPresenter(db);

        insertPresenter.addWord(word);
    }

    public void updateWord(Word oldWord, Word newWord) {
        if (insertPresenter == null) insertPresenter = new Word_InsertPresenter(db);

        insertPresenter.updateWord(oldWord, newWord);
    }

    public void addMeaning(Word oldWord, Word newWord) {
        if (insertPresenter == null) insertPresenter = new Word_InsertPresenter(db);

        insertPresenter.addMeaning(oldWord, newWord);
    }

    public void deleteWord(Word word) {
        if (insertPresenter == null) insertPresenter = new Word_InsertPresenter(db);

        insertPresenter.deleteWord(word);
    }
}
