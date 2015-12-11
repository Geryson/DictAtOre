package com.ekfej.dictatore.Presenter;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by Lloyd on 2015. 12. 11..
 */
public class KnowledgeTestPresenter extends MainPresenter {

    private Language language1;

    private Language language2;

    private Word[] words;
    private int next = 0;
    public Word GetNextWord() {
        if (next < wordsCount) return words[next++];
        else return null;
    }

    private int wordsCount = words.length;
    public int GetWordsCount() {
        return wordsCount;
    }


    public KnowledgeTestPresenter(Context context, String language1, String language2, int numberOfWords) {
        super(context);
        this.language1 = new Language(language1);
        this.language2 = new Language(language2);
        GetRandomWords(numberOfWords, this.language1, this.language2);
    }


    private void GetRandomWords(int number, Language language1, Language language2) {
        String[] wordsNames = db.Expression(language1.getName(), number);

        words = StringWordArray2WordWordArray(number, wordsNames, language1, language2);
    }


}