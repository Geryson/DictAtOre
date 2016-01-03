package com.ekfej.dictatore.Presenter;

import android.content.Context;

import com.ekfej.dictatore.Database.DatabaseAccess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lloyd on 2015. 12. 31..
 */
public class Word_InsertPresenter extends MainPresenter {

    public Word_InsertPresenter(Context context)
    {
        super(context);
    }

    public void addWord(Word word) {
        db.wordMethod.WordInsert(word);
    }

    public void updateWord(Word oldWord, Word newWord) {
        db.wordMethod.WordUpdateObject(oldWord, newWord);
    }

    public void addMeaning(Word oldWord, Word newWord) {
        db.wordMethod.WordUpdateObject(oldWord, newWord);
    }

    public void deleteWord(Word word) {
        db.wordMethod.WordDelete(word);
    }
}
