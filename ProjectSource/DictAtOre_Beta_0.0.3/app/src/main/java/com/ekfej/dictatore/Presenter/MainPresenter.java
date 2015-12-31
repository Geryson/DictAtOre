package com.ekfej.dictatore.Presenter;

import android.content.Context;

import com.ekfej.dictatore.Database.DatabaseAccess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lloyd on 2015. 12. 11..
 */
public class MainPresenter {

    protected DatabaseAccess db;



    protected MainPresenter(Context context) {
        db = new DatabaseAccess(context);
    }



    protected List<Word> stringMeaningList2WordMeaningList(List<String> wordsNames, Language language){
        List<Word> words = new ArrayList<Word>();
        int wordsNamesSize = wordsNames.size();

        for (int i = 0; i < wordsNamesSize; i++) {
            words.add(new Word(wordsNames.get(i), language));
        }

        return words;
    }

    protected Word[] stringWordArray2WordWordArray(int number, String[] wordsNames, Language language1, Language language2) {
        Word[] words = new Word[number];
        String language1Name = language1.getName();
        String language2Name = language2.getName();

        for (int i = 0; i < number; i++) {
            words[i] = new Word(
                    wordsNames[i],
                    stringMeaningList2WordMeaningList(
                            db.knowledgeTest.Decipherment(
                                    language2Name,
                                    language1Name,
                                    wordsNames[i]
                            ),
                            language2
                    ),
                    language1
            );
        }

        return words;
    }

    protected Language[] stringLanguageList2LanguageLanguageArray(List<String> languages) {
        int languagesSize = languages.size();
        Language[] languagesArray = new Language[languagesSize];

        for (int i = 0; i < languagesSize; i++) {
            languagesArray[i] = new Language(languages.remove(0));
        }

        return languagesArray;
    }

    protected List<Language> stringLanguageList2LanguageLanguageList(List<String> languages) {
        int languagesSize = languages.size();
        List<Language> languageList = new ArrayList<Language>();

        for (int i = 0; i < languagesSize; i++) {
            languageList.add(new Language(i, languages.get(i)));
        }

        return languageList;
    }

}
