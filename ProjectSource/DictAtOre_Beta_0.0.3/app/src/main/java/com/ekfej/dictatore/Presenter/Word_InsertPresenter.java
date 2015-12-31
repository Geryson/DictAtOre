package com.ekfej.dictatore.Presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lloyd on 2015. 12. 31..
 */
public class Word_InsertPresenter extends MainPresenter {

    public Word_InsertPresenter(Context context) {
        super(context);
    }



    public void updateWord() {
        //db.wordMethod.WordUpdateObject();
    }

    public void addMeaning() {

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
