package com.ekfej.dictatore.Presenter;

import android.content.Context;

import java.util.List;

/**
 * Created by Lloyd on 2015. 12. 11..
 */
public class KnowledgeTestPresenter extends MainPresenter {

    private byte WordsCount = 15;
    public byte GetWordsCount() { return WordsCount; }



    public KnowledgeTestPresenter(Context context, String language1, String language2) {
        super(context);
    }


}
