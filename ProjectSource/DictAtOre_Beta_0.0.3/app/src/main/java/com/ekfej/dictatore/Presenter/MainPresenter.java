package com.ekfej.dictatore.Presenter;

import android.content.Context;

import com.ekfej.dictatore.Modell.DatabaseAccess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lloyd on 2015. 12. 11..
 */
public class MainPresenter {

    protected DatabaseAccess db;



    protected MainPresenter(Context context) {
        db = DatabaseAccess.getInstance(context);
    }



    protected List<Word> StringMeaningList2WordMeaningList(List<String> wordsNames, Language language){
        List<Word> words = new ArrayList<Word>();
        int wordsNamesSize = wordsNames.size();

        for (int i = 0; i < wordsNamesSize; i++) {
            words.add(new Word(wordsNames.get(i), language));
        }

        return words;
    }

    protected Word[] StringWordArray2WordWordArray(int number, String[] wordsNames, Language language1, Language language2) {
        Word[] words = new Word[number];
        String language1Name = language1.getName();
        String language2Name = language2.getName();

        for (int i = 0; i < number; i++) {
            words[i] = new Word(
                    wordsNames[i],
                    StringMeaningList2WordMeaningList(
                            db.Decipherment(
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

}
