package com.ekfej.dictatore.Presenter;

import android.content.Context;

import java.util.List;

/**
 * Created by Lloyd on 2015. 12. 13..
 */
public class DictPresenter extends MainPresenter {

    private Language language1;

    private Language language2;

    private Language[] languages2Choose;




    protected DictPresenter(Context context, String language1, String language2) {
        super(context);
        this.language1 = new Language(language1);
        this.language2 = new Language(language2);
        languages2Choose = StringLanguageList2LanguageLanguageArray(db.LanguageSelect());
    }


    public Word[] Words2Display(Language language1, Language language2) {



        return (Word[])db.DictionarySelect(language1.getName(), language2.getName()).toArray();
    }

}
